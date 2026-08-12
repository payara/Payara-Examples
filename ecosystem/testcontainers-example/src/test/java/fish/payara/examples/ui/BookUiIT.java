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

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Tests the "Book" JSF page (book.xhtml): create, edit and delete a book
 * through the browser, driven by Playwright against the deployed application.
 */
class BookUiIT extends AbstractUiIT {

    @Test
    void createsABookAndShowsItInTheList() {
        String title = unique("Playwright Book");

        navigateTo("book.xhtml");
        // Leave the Isbn field blank: the id is auto-generated on create, same
        // as the REST API path (see BookBean#save / AbstractService#create).
        page.getByLabel("Title:").fill(title);
        page.getByLabel("Author:").fill("Jane Author");
        page.getByLabel("Pages:").fill("123");
        clickSave();

        Locator row = rowContaining(title);
        assertThat(row).isVisible();
        assertThat(row).containsText("Jane Author");
        assertThat(row).containsText("123");
    }

    @Test
    void editsABook() {
        String title = unique("Book To Edit");
        String updatedTitle = unique("Updated Book");

        navigateTo("book.xhtml");
        page.getByLabel("Title:").fill(title);
        page.getByLabel("Author:").fill("Original Author");
        page.getByLabel("Pages:").fill("50");
        clickSave();

        rowContaining(title).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Edit")).click();
        page.getByLabel("Title:").fill(updatedTitle);
        clickSave();

        assertThat(rowContaining(updatedTitle)).containsText("Original Author");
        assertThat(rowContaining(title)).hasCount(0);
    }

    @Test
    void deletesABook() {
        String title = unique("Book To Delete");

        navigateTo("book.xhtml");
        page.getByLabel("Title:").fill(title);
        page.getByLabel("Author:").fill("Delete Author");
        page.getByLabel("Pages:").fill("10");
        clickSave();

        assertThat(rowContaining(title)).isVisible();

        rowContaining(title).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Delete")).click();

        assertThat(rowContaining(title)).hasCount(0);
    }
}
