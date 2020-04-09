/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016 C2B2 Consulting Limited and/or its affiliates.
 * All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 */
package fish.payara.examples.dao;

import fish.payara.examples.domain.Person;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.hamcrest.core.Is.is;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * author: mertcaliskan
 */
@RunWith(Arquillian.class)
public class PersonDaoTest {

    @EJB
    private PersonDao personDao;
    
    @Inject TestData testData;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "arquillian-example.war")
                .addClass(Person.class)
                .addClass(PersonDao.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Before
    public void prepareTestData() {
        testData.prepareForShouldReturnAllPerson();
    }

    @Test
    public void shouldReturnAllPerson() throws Exception {
        List<Person> personList = personDao.getAll();

        assertNotNull(personList);
        assertThat(personList.size(), is(1));
        assertThat(personList.get(0).getName(), is("John"));
        assertThat(personList.get(0).getLastName(), is("Malkovich"));
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
