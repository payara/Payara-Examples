/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2026 Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package fish.payara.examples.ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import fish.payara.examples.testcontainers.AbstractContainerIT;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

/**
 * Base class for browser-driven UI tests exercising the JSF pages of the
 * application through Playwright, running against the same Payara Micro
 * Testcontainer shared with the REST IT tests (see {@link AbstractContainerIT}).
 *
 * <p>One headless Chromium instance is launched per test class (JUnit 5 runs
 * {@code @BeforeAll}/{@code @AfterAll} around each concrete subclass); each
 * individual test then gets its own {@link BrowserContext}/{@link Page} so
 * tests don't leak cookies or state into one another.
 * Note: the Playwright {@code chromium} browser binary needs to be installed once, which the
 * build's {@code generate-test-resources} phase does automatically (see the
 * exec-maven-plugin execution in pom.xml).</p>
 *
 * <p>On failure, a screenshot, the page's HTML, its URL and the Payara
 * container logs are dumped under {@code target/playwright-failures/} (see
 * {@link FailureDiagnostics}) so a failing test is debuggable from the build
 * output alone, without having to reproduce it interactively.</p>
 */
@ExtendWith(AbstractUiIT.FailureDiagnostics.class)
public abstract class AbstractUiIT extends AbstractContainerIT {

    private static Playwright playwright;
    private static Browser browser;

    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @BeforeEach
    void newPage() {
        context = browser.newContext();
        page = context.newPage();
        // The "Delete" links use a JS confirm() dialog; always accept it so
        // delete flows don't need to register their own handler.
        page.onDialog(Dialog::accept);
    }

    protected Page navigateTo(String relativePath) {
        page.navigate(applicationContextUrl() + relativePath);
        return page;
    }

    /** Clicks the form's "Save" button (an {@code h:commandButton}, so it has no stable id). */
    protected void clickSave() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();
    }

    /**
     * Locates the {@code h:dataTable} row containing the given text (e.g. a
     * title or name). Scoped to {@code table.table-striped} - every page's
     * {@code h:dataTable} is rendered with that class (see e.g. book.xhtml),
     * while the entry form above it is a separate {@code h:panelGrid}
     * ("table-form" class) that also renders as an HTML {@code <table>}. That
     * distinction matters on loan.xhtml in particular: a {@code <select>}'s
     * full option list counts as "text" for Playwright's hasText matching, so
     * an unscoped locator would match the librarian/patron/book dropdown's
     * row in the form instead of the actual data row. Matches any
     * {@code <tr>} rather than assuming a {@code <tbody>} wrapper, since
     * {@code h:dataTable} doesn't always render one.
     */
    protected Locator rowContaining(String text) {
        return page.locator("table.table-striped tr", new Page.LocatorOptions().setHasText(text));
    }

    /**
     * The container's Payara instance and its database live for the whole test
     * run, so test data isn't reset between tests. Suffix human-readable values
     * with this to keep rows unique and independent of what other tests wrote.
     */
    protected static String unique(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * On test failure, saves a screenshot, the current page's HTML/URL, and
     * the Payara container logs to {@code target/playwright-failures/}, named
     * after the failing test. Best-effort: any problem while writing the
     * diagnostics is swallowed so it never masks the original failure.
     *
     * <p>This also owns closing the {@link BrowserContext} for every outcome
     * (pass, fail, abort, disabled) instead of an {@code @AfterEach} method,
     * because JUnit 5 runs {@code @AfterEach} - and therefore would have
     * closed the page - <b>before</b> invoking {@link TestWatcher} callbacks.
     * With cleanup in {@code @AfterEach}, {@link #testFailed} would always
     * see an already-closed page and silently skip the screenshot/HTML
     * capture (which is exactly what happened before this class took over
     * closing: only the container log, which doesn't need the page, was ever
     * written).</p>
     */
    static final class FailureDiagnostics implements TestWatcher {

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            withTestInstance(context, test -> {
                captureDiagnostics(context, test);
                closeContext(test);
            });
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            withTestInstance(context, this::closeContext);
        }

        @Override
        public void testAborted(ExtensionContext context, Throwable cause) {
            withTestInstance(context, this::closeContext);
        }

        @Override
        public void testDisabled(ExtensionContext context, Optional<String> reason) {
            withTestInstance(context, this::closeContext);
        }

        private void withTestInstance(ExtensionContext context, java.util.function.Consumer<AbstractUiIT> action) {
            Object testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof AbstractUiIT) {
                action.accept((AbstractUiIT) testInstance);
            }
        }

        private void captureDiagnostics(ExtensionContext context, AbstractUiIT test) {
            String name = context.getRequiredTestClass().getSimpleName() + "-" + context.getRequiredTestMethod().getName();
            try {
                Path dir = Path.of("target", "playwright-failures");
                Files.createDirectories(dir);
                if (test.page != null && !test.page.isClosed()) {
                    test.page.screenshot(new Page.ScreenshotOptions().setPath(dir.resolve(name + ".png")).setFullPage(true));
                    writeString(dir.resolve(name + ".html"), test.page.content());
                    writeString(dir.resolve(name + ".url.txt"), test.page.url());
                }
                writeString(dir.resolve(name + ".container.log"), AbstractUiIT.containerLogs());
            } catch (Exception diagnosticsFailure) {
                // Best-effort: never let diagnostics collection hide the real test failure.
            }
        }

        private void closeContext(AbstractUiIT test) {
            if (test.context != null) {
                try {
                    test.context.close();
                } catch (Exception ignored) {
                    // Best-effort cleanup.
                }
            }
        }

        private static void writeString(Path path, String content) throws IOException {
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        }
    }
}
