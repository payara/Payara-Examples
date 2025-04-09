/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2025 Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.examples.dao;

import fish.payara.examples.domain.Person;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ejb.EJB;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jboss.arquillian.container.test.api.Testable;

/**
 * author: simonladen
 */
@ExtendWith(ArquillianExtension.class)
public class PersonDaoTest {

    @EJB
    private PersonDao personDao;
    
    @Inject TestData testData;

    private static final String APPLICATION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        + "<application xmlns=\"http://java.sun.com/xml/ns/javaee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/application_6.xsd\" version=\"6\">"            + "<display-name>org.acme.project</display-name>"
        // the WAR must be added to the application.xml !
        + "<module><web><web-uri>arquillian-junit5-example.war</web-uri><context-root>/test</context-root></web></module>"
        + "</application>";
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "arquillian-junit5-example.war")
                .addClass(Person.class)
                .addClass(PersonDao.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @BeforeEach
    public void prepareTestData() {
        testData.prepareForShouldReturnAllPerson();
    }

    @Test
    public void shouldReturnAllPerson() throws Exception {
        List<Person> personList = personDao.getAll();

        assertNotNull(personList);
        assertEquals(personList.size(), 1);
        assertEquals(personList.get(0).getName(), "John");
        assertEquals(personList.get(0).getLastName(), "Malkovich");
    }

    @Dependent
    public static class TestData {

        @PersistenceContext
        private EntityManager entityManager;

        @Transactional
        public void prepareForShouldReturnAllPerson() {
            entityManager.persist(new Person("John", "Malkovich"));
        }
    }
}
