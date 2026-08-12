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
package fish.payara.examples.resource;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

import fish.payara.examples.domain.Librarian;
import fish.payara.examples.service.LibrarianService;

@Named("librarianBean")
@ViewScoped
public class LibrarianBean implements Serializable {

    @Inject
    private transient LibrarianService librarianService;

    private Librarian librarian = new Librarian();

    public Librarian getLibrarian() {
        return librarian;
    }

    public List<Librarian> getAllLibrarians() {
        return librarianService.findAll();
    }

    public String create() {
      
        return null;
    }
    public String save() {
        // isBlank(), not just == null: librarian.xhtml's id field is a plain
        // h:inputText, so saving it with that field left blank submits ""
        // rather than null (see Librarian.assignId() for the full story).
        // Without this check, "" reads as "already has an id" and this would
        // call edit()/merge() on a brand new librarian instead of create().
        if (librarian.getLibrarianID() == null || librarian.getLibrarianID().isBlank()) {
             librarianService.create(librarian);
        } else {
             librarianService.edit(librarian);
        }
        librarian = new Librarian(); // reset
        return null;
    }

    public String remove(String librarianID) {
        librarianService.remove(librarianService.find(librarianID));
        return null;
    }

    public String edit(Librarian p) {
        this.librarian = p;
        return null;
    }

}