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
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Tests the "Loan" JSF page (loan.xhtml). Unlike the other entities, a
 * loan is created by picking an existing librarian, patron and book from
 * dropdowns, so this test first creates one of each through their own pages
 * (reusing the same browser session) before creating, editing and deleting
 * the loan itself.
 */
class LoanUiIT extends AbstractUiIT {

    @Test
    void createsALoanFromAnExistingLibrarianPatronAndBook() {
        String librarianName = unique("Loan Librarian");
        String patronName = unique("Loan Patron");
        String bookTitle = unique("Loan Book");

        createLibrarian(librarianName);
        createPatron(patronName);
        createBook(bookTitle);
        fillAndSaveLoan(librarianName, patronName, bookTitle, "2026-07-14T09:00", "2026-07-28T09:00");

        Locator row = rowContaining(librarianName);
        assertThat(row).isVisible();
        assertThat(row).containsText(patronName);
        assertThat(row).containsText(bookTitle);
        assertThat(row).containsText("2026-07-14T09:00");
    }

    @Test
    void editsALoan() {
        String librarianName = unique("Loan Librarian Edit");
        String patronName = unique("Loan Patron Edit");
        String bookTitle = unique("Loan Book Edit");

        createLibrarian(librarianName);
        createPatron(patronName);
        createBook(bookTitle);
        fillAndSaveLoan(librarianName, patronName, bookTitle, "2026-07-14T09:00", "2026-07-28T09:00");

        rowContaining(librarianName).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Edit")).click();
        page.getByLabel("Return Date:").fill("2026-08-04T09:00");
        clickSave();

        assertThat(rowContaining(librarianName)).containsText("2026-08-04T09:00");
    }

    @Test
    void deletesALoan() {
        String librarianName = unique("Loan Librarian Delete");
        String patronName = unique("Loan Patron Delete");
        String bookTitle = unique("Loan Book Delete");

        createLibrarian(librarianName);
        createPatron(patronName);
        createBook(bookTitle);
        fillAndSaveLoan(librarianName, patronName, bookTitle, "2026-07-14T09:00", "2026-07-28T09:00");

        assertThat(rowContaining(librarianName)).isVisible();

        rowContaining(librarianName).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Delete")).click();

        assertThat(rowContaining(librarianName)).hasCount(0);
    }

    private void createLibrarian(String name) {
        navigateTo("librarian.xhtml");
        page.getByLabel("Name:").fill(name);
        clickSave();
    }

    private void createPatron(String name) {
        navigateTo("patron.xhtml");
        page.getByLabel("Name:").fill(name);
        clickSave();
    }

    private void createBook(String title) {
        navigateTo("book.xhtml");
        page.getByLabel("Title:").fill(title);
        page.getByLabel("Author:").fill("Loan Test Author");
        page.getByLabel("Pages:").fill("42");
        clickSave();
    }

    private void fillAndSaveLoan(String librarianName, String patronName, String bookTitle,
                                 String loanDate, String returnDate) {
        navigateTo("loan.xhtml");
        page.getByLabel("Loan Date:").fill(loanDate);
        page.getByLabel("Return Date:").fill(returnDate);
        page.getByLabel("Librarian:").selectOption(new SelectOption().setLabel(librarianName));
        page.getByLabel("Patron:").selectOption(new SelectOption().setLabel(patronName));
        page.getByLabel("Book:").selectOption(new SelectOption().setLabel(bookTitle));
        clickSave();
    }
}
