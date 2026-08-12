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
    @NamedQuery(name = "Librarian.findByLibrarianID", query = "SELECT e FROM Librarian e WHERE e.librarianID = :librarianID"),
    @NamedQuery(name = "Librarian.findByName", query = "SELECT e FROM Librarian e WHERE e.name = :name"),
    @NamedQuery(name = "Librarian.findByDepartment", query = "SELECT e FROM Librarian e WHERE e.department = :department")
})
@Entity
public class Librarian {

    // Plain @Id, no @GeneratedValue: JPA's generation strategies (IDENTITY/
    // SEQUENCE/TABLE/AUTO) target numeric surrogate keys; for a String @Id
    // we assign it ourselves in @PrePersist instead. Checking isBlank() (not
    // just null) matters: librarian.xhtml's "Librarian ID:" field is a plain
    // h:inputText bound directly to this property, and unlike a numeric
    // property (where JSF's implicit converter turns an empty submission into
    // null), a String property gets the submitted value verbatim - so saving
    // the form with that field left blank sets librarianID to "" rather than
    // null. That empty string is not null, so it silently passed the create/
    // edit check as "already has an id" and, before this fix, also passed
    // this method's null check unchanged - persisting the row with a literal
    // empty-string id. It looked fine in this page's own list (name/actions
    // still worked), but broke anything that keyed off "does this entity have
    // an id yet": the loan.xhtml dropdown's converter treats "" the same as
    // "no id" and renders it as an empty <option value="">, indistinguishable
    // from the "no selection" placeholder - so selecting that librarian for a
    // loan silently saved as no librarian at all.
    @Id
    private String librarianID;

    @PrePersist
    private void assignId() {
        if (librarianID == null || librarianID.isBlank()) {
            librarianID = UUID.randomUUID().toString();
        }
    }

    private String name;

    private String department;

    @JsonbTransient
    @OneToMany(mappedBy = "librarian")
    private List<Loan> loansManageds;


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.librarianID);
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
        final Librarian other = (Librarian) obj;
        return Objects.equals(this.librarianID, other.librarianID);
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    // Getters and setters

    public String getLibrarianID() {
        return librarianID;
    }

    public void setLibrarianID(String librarianID) {
        this.librarianID = librarianID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Loan> getLoansManageds() {
        return loansManageds;
    }

    public void setLoansManageds(List<Loan> loansManageds) {
        this.loansManageds = loansManageds;
    }

}
