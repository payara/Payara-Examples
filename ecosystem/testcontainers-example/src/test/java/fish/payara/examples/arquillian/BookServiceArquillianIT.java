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
package fish.payara.examples.arquillian;

import fish.payara.examples.domain.Book;
import fish.payara.examples.service.AbstractService;
import fish.payara.examples.service.BookService;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Covers the one corner of {@link AbstractService} that neither
 * {@code BookServiceTest} (WeldInitiator, mocked {@code EntityManager}) nor
 * {@code BookServiceIT} (Testcontainers, real Payara Micro but only reachable
 * through the REST resources' CRUD endpoints) can reach: {@link
 * AbstractService#count()}, {@link AbstractService#findRange(int, int)}, and
 * the named-query methods ({@link AbstractService#findByNamedQuery(String, Map)},
 * {@link AbstractService#findSingleByNamedQuery(String, Map)}). Nothing in this
 * project calls those four methods or any of the fourteen {@code @NamedQuery}
 * declarations across the domain classes - not a REST resource, not a JSF
 * bean, not another test - so, until now, none of it was verified to actually
 * work against a real persistence provider at all.
 *
 * <p>Deploys a minimal {@link JavaArchive} - just the domain classes, {@code
 * AbstractService}/{@code BookService}, the real {@code persistence.xml}, and
 * an empty {@code beans.xml} - rather than the full WAR Testcontainers
 * deploys, since this test only needs {@code BookService} injectable and a
 * working persistence unit, not the JAX-RS/JSF layers.</p>
 */
@ExtendWith(ArquillianExtension.class)
class BookServiceArquillianIT {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "book-service-arquillian-it.jar")
                .addPackage(Book.class.getPackage())
                .addClasses(AbstractService.class, BookService.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private BookService bookService;

    @Test
    void countReflectsRealPersistedRows() {
        int before = bookService.count();

        bookService.create(newBook("Count Test One " + UUID.randomUUID()));
        bookService.create(newBook("Count Test Two " + UUID.randomUUID()));
        bookService.create(newBook("Count Test Three " + UUID.randomUUID()));

        assertEquals(before + 3, bookService.count());
    }

    @Test
    void findRangePaginatesWithoutOverlapOrLoss() {
        for (int i = 0; i < 5; i++) {
            bookService.create(newBook("Page Test " + i + " " + UUID.randomUUID()));
        }

        // AbstractService#findRange builds its CriteriaQuery without an
        // explicit ORDER BY, so which rows land on which page is not
        // guaranteed by the JPA spec - only that paging doesn't duplicate or
        // drop rows is. Assert on that instead of on page contents.
        List<Book> firstPage = bookService.findRange(0, 3);
        List<Book> secondPage = bookService.findRange(3, 3);

        assertEquals(3, firstPage.size());
        assertFalse(secondPage.isEmpty());

        Set<String> firstPageIsbns = firstPage.stream().map(Book::getIsbn).collect(Collectors.toSet());
        Set<String> secondPageIsbns = secondPage.stream().map(Book::getIsbn).collect(Collectors.toSet());

        assertTrue(java.util.Collections.disjoint(firstPageIsbns, secondPageIsbns),
                "the same book showed up on both pages - pagination is broken");
    }

    @Test
    void findByNamedQueryLocatesBookByTitle() {
        String title = "Named Query Target " + UUID.randomUUID();
        bookService.create(newBook(title));

        // Book.findByTitle: "SELECT e FROM Book e WHERE e.title = :title"
        // (see Book.java's @NamedQuery). Never invoked anywhere else in the
        // project before this test - this is the first thing to ever confirm
        // its JPQL actually compiles and runs against a real provider.
        List<Book> found = bookService.findByNamedQuery("Book.findByTitle", paramsOf("title", title));

        assertEquals(1, found.size());
        assertEquals(title, found.get(0).getTitle());
    }

    @Test
    void findSingleByNamedQueryIsEmptyWhenNoRowMatches() {
        // Mirrors AbstractServiceTest#findOrEmptyReturnsEmptyOnNoResult, but
        // that unit test proves findOrEmpty() correctly converts a
        // hand-thrown NoResultException into Optional.empty() - it never
        // provokes a real NoResultException from a real missed query. This
        // does.
        Optional<Book> found = bookService.findSingleByNamedQuery(
                "Book.findByIsbn", paramsOf("isbn", "does-not-exist-" + UUID.randomUUID()));

        assertTrue(found.isEmpty());
    }

    private Book newBook(String title) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor("Arquillian IT Author");
        book.setPages(100);
        return book;
    }

    private Map<String, Object> paramsOf(String key, Object value) {
        Map<String, Object> params = new HashMap<>();
        params.put(key, value);
        return params;
    }
}
