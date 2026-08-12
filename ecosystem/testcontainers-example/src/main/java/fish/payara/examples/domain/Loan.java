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
package fish.payara.examples.domain;

import jakarta.persistence.*;
import java.util.Objects;

import java.time.LocalDateTime;

@NamedQueries({
    @NamedQuery(name = "Loan.findByLoanID", query = "SELECT e FROM Loan e WHERE e.loanID = :loanID"),
    @NamedQuery(name = "Loan.findByLoanDate", query = "SELECT e FROM Loan e WHERE e.loanDate = :loanDate"),
    @NamedQuery(name = "Loan.findByReturnDate", query = "SELECT e FROM Loan e WHERE e.returnDate = :returnDate")
})
@Entity
public class Loan {

    // GenerationType.TABLE (not AUTO or IDENTITY): EclipseLink's AUTO resolution for a
    // numeric @Id on H2 still picks the platform's native IDENTITY column generation,
    // which produces "INTEGER IDENTITY NOT NULL" DDL that this H2 version's parser
    // rejects (a real schema-creation failure, not a test issue - see the container
    // logs: CREATE TABLE LOAN fails, so the table never exists). TABLE forces the same
    // EclipseLink default table-based generator (the SEQUENCE table) that Book/Patron/
    // Librarian's AUTO-strategy String ids already use successfully.
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer loanID;

    private LocalDateTime loanDate;

    private LocalDateTime returnDate;

    @ManyToOne
    @JoinColumn(name = "librarian_id")
    private Librarian librarian;

    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.loanID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Loan other = (Loan) obj;
        return Objects.equals(this.loanID, other.loanID);
    }

    @Override
    public String toString() {
        return String.valueOf(loanID);
    }

    // Getters and setters

    public Integer getLoanID() {
        return loanID;
    }

    public void setLoanID(Integer loanID) {
        this.loanID = loanID;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
