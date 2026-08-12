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

import java.util.List;
import java.util.UUID;
import jakarta.json.bind.annotation.JsonbTransient;

@NamedQueries({
    @NamedQuery(name = "Book.findByIsbn", query = "SELECT e FROM Book e WHERE e.isbn = :isbn"),
    @NamedQuery(name = "Book.findByTitle", query = "SELECT e FROM Book e WHERE e.title = :title"),
    @NamedQuery(name = "Book.findByAuthor", query = "SELECT e FROM Book e WHERE e.author = :author"),
    @NamedQuery(name = "Book.findByPages", query = "SELECT e FROM Book e WHERE e.pages = :pages")
})
@Entity
public class Book {

    // Plain @Id, no @GeneratedValue, with an isBlank() check in @PrePersist:
    // see Librarian.assignId() for the full explanation - book.xhtml's
    // "Isbn:" field is a plain h:inputText bound directly to isbn, so saving
    // it blank submits "" (not null) for this String property, which needs
    // the same isBlank() guard to avoid persisting a literal empty-string id.
    // Note: this is a generated surrogate key, not a real ISBN - the field
    // name predates this fix.
    @Id
    private String isbn;

    @PrePersist
    private void assignId() {
        if (isbn == null || isbn.isBlank()) {
            isbn = UUID.randomUUID().toString();
        }
    }

    private String title;

    private String author;

    private Integer pages;

    @JsonbTransient
    @OneToMany(mappedBy = "book")
    private List<Loan> loans;


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.isbn);
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
        final Book other = (Book) obj;
        return Objects.equals(this.isbn, other.isbn);
    }

    @Override
    public String toString() {
        return String.valueOf(title);
    }

    // Getters and setters

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

}
