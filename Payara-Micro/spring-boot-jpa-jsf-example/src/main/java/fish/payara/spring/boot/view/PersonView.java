/*
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 Copyright (c) 2015 C2B2 Consulting Limited. All rights reserved.
 The contents of this file are subject to the terms of the Common Development
 and Distribution License("CDDL") (collectively, the "License").  You
 may not use this file except in compliance with the License.  You can
 obtain a copy of the License at
 https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 or packager/legal/LICENSE.txt.  See the License for the specific
 language governing permissions and limitations under the License.
 When distributing the software, include this License Header Notice in each
 file and include the License file at packager/legal/LICENSE.txt.
 */
package fish.payara.spring.boot.view;

import fish.payara.spring.boot.domain.Person;
import fish.payara.spring.boot.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mertcaliskan
 */
@Controller
public class PersonView implements Serializable {

    @Autowired
    private PersonRepository personRepository;

    private Person person = new Person();

    public List<Person> getPersonList() {
        return personRepository.findAll();
    }

    public void savePerson() {
        personRepository.save(person);
        person = new Person();
    }

    public void deletePerson(Person person) {
        personRepository.delete(person);
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}