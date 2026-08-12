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
package fish.payara.examples.service;

import fish.payara.examples.domain.Patron;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@EnableWeld
class PatronServiceTest {

    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(PatronService.class)
            .build();

    @Mock
    private EntityManager entityManager;

    private PatronService patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patronService = weld.select(PatronService.class).get();
        try {
            var field = AbstractService.class.getDeclaredField("em");
            field.setAccessible(true);
            field.set(patronService, entityManager);
        } catch (Exception e) {
            fail("Failed to set mock EntityManager: " + e.getMessage());
        }
    }

    @Test
    void testCreateAndFind() {
        Patron p = new Patron();
        p.setName("John Doe");
        doNothing().when(entityManager).persist(any(Patron.class));

        patronService.create(p);
        verify(entityManager).persist(p);

        when(entityManager.find(eq(Patron.class), eq("id-1"))).thenReturn(p);
        Patron found = patronService.find("id-1");
        assertNotNull(found);
        assertEquals("John Doe", found.getName());
    }

    @Test
    void testFindAll() {
        Patron p1 = new Patron(); p1.setName("A");
        Patron p2 = new Patron(); p2.setName("B");
        List<Patron> list = Arrays.asList(p1, p2);

        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery cq = mock(CriteriaQuery.class);
        Root<Patron> root = mock(Root.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery()).thenReturn(cq);
        when(cq.from(Patron.class)).thenReturn(root);
        when(cq.select(root)).thenReturn(cq);

        TypedQuery<Patron> typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(cq)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Patron> result = patronService.findAll();

        verify(cq).from(Patron.class);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

}
