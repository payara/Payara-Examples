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
package fish.payara.spring.boot.service;

import fish.payara.spring.boot.domain.Person;
import fish.payara.spring.boot.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by mertcaliskan
 */
@Service
public class DbInitService {

    @Autowired
    private PersonRepository repository;

    @PostConstruct
    public void init() {
        Person person1 = new Person(1, "Mert", "Caliskan", "mcaliskan@gmail.com");
        repository.save(person1);
        Person person2 = new Person(2, "Steve", "Millidge", "smillidge@c2b2.co.uk");
        repository.save(person2);
        Person person3 = new Person(3, "Andrew", "Pielage", "apielage@c2b2.co.uk");
        repository.save(person3);
    }
}
