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
 * Exercises the "Patron" JSF page (patron.xhtml): create, edit and delete a
 * patron through the browser, driven by Playwright against the deployed
 * application.
 */
class PatronUiIT extends AbstractUiIT {

    @Test
    void createsAPatronAndShowsItInTheList() {
        String name = unique("Playwright Patron");

        navigateTo("patron.xhtml");
        page.getByLabel("Name:").fill(name);
        page.getByLabel("Address:").fill("1 Library Way");
        page.getByLabel("Email:").fill("patron@example.com");
        clickSave();

        Locator row = rowContaining(name);
        assertThat(row).isVisible();
        assertThat(row).containsText("1 Library Way");
        assertThat(row).containsText("patron@example.com");
    }

    @Test
    void editsAPatron() {
        String name = unique("Patron To Edit");

        navigateTo("patron.xhtml");
        page.getByLabel("Name:").fill(name);
        page.getByLabel("Address:").fill("Old Address");
        page.getByLabel("Email:").fill("old@example.com");
        clickSave();

        rowContaining(name).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Edit")).click();
        page.getByLabel("Email:").fill("new@example.com");
        clickSave();

        assertThat(rowContaining(name)).containsText("new@example.com");
    }

    @Test
    void deletesAPatron() {
        String name = unique("Patron To Delete");

        navigateTo("patron.xhtml");
        page.getByLabel("Name:").fill(name);
        page.getByLabel("Address:").fill("Somewhere");
        page.getByLabel("Email:").fill("delete@example.com");
        clickSave();

        assertThat(rowContaining(name)).isVisible();

        rowContaining(name).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Delete")).click();

        assertThat(rowContaining(name)).hasCount(0);
    }
}
