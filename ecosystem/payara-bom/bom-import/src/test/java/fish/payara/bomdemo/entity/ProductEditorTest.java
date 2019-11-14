/*
 *    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 *    Copyright (c) [2019] Payara Foundation and/or its affiliates. All rights reserved.
 *
 *    The contents of this file are subject to the terms of either the GNU
 *    General Public License Version 2 only ("GPL") or the Common Development
 *    and Distribution License("CDDL") (collectively, the "License").  You
 *    may not use this file except in compliance with the License.  You can
 *    obtain a copy of the License at
 *    https://github.com/payara/Payara/blob/master/LICENSE.txt
 *    See the License for the specific
 *    language governing permissions and limitations under the License.
 *
 *    When distributing the software, include this License Header Notice in each
 *    file and include the License file at glassfish/legal/LICENSE.txt.
 *
 *    GPL Classpath Exception:
 *    The Payara Foundation designates this particular file as subject to the "Classpath"
 *    exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 *    file that accompanied this code.
 *
 *    Modifications:
 *    If applicable, add the following below the License Header, with the fields
 *    enclosed by brackets [] replaced by your own identifying information:
 *    "Portions Copyright [year] [name of copyright owner]"
 *
 *    Contributor(s):
 *    If you wish your version of this file to be governed by only the CDDL or
 *    only the GPL Version 2, indicate your decision by adding "[Contributor]
 *    elects to include this software in this distribution under the [CDDL or GPL
 *    Version 2] license."  If you don't indicate a single choice of license, a
 *    recipient has the option to distribute your version of this file under
 *    either the CDDL, the GPL Version 2 or to extend the choice of license to
 *    its licensees as provided above.  However, if you add GPL Version 2 code
 *    and therefore, elected the GPL Version 2 license, then the option applies
 *    only if the new code is made subject to such option by the copyright
 *    holder.
 */

package fish.payara.bomdemo.entity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Not really recommended way to test EJBs, but a way to quickly test JPA behavior or mapping.
 */
public class ProductEditorTest {

    static class ManualClock extends Clock {
        private final ZoneId zone;
        private Instant instant;

        ManualClock(Instant start, ZoneId zone) {
            this.instant = start;
            this.zone = zone == null ? ZoneId.systemDefault() : zone;
        }
        @Override
        public ZoneId getZone() {
            return zone;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return new ManualClock(instant, zone);
        }

        @Override
        public Instant instant() {
            return instant;
        }

        public void tick() {
            instant = instant.plusSeconds(1);
        }
    }

    private static EntityManagerFactory emf;
    private static ManualClock clock = new ManualClock(Instant.now(), null);

    private EntityManager em;
    private EntityTransaction tx;
    private ProductEditor editor;


    @BeforeClass
    public static void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("jpatest");
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }
    }

    @Before
    public void createEntityManager() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        editor = new ProductEditor();
        editor.clock = clock;
        editor.mgr = em;
    }

    @After
    public void closeEntityManager() {
        if (tx != null) {
            if (!tx.getRollbackOnly()) {
                tx.commit();
            } else {
                tx.rollback();
            }
        }
        if (em != null) {
            em.close();
        }
    }

    @Test
    public void historyExistsAfterCreate() {
        Product product = editor.newProduct("historyExistsAfterCreate", "A product shall have history with initial price", BigDecimal.TEN);
        assertNotNull(product);
        clock.tick();
        assertEquals(product.getCurrentPrice(), product.getPriceAt(clock.instant()));
    }
}
