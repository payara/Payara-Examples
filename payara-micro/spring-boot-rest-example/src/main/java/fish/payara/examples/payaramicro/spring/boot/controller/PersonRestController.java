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
package fish.payara.examples.payaramicro.spring.boot.controller;

import fish.payara.examples.payaramicro.spring.boot.domain.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mertcaliskan
 */
@RestController
@RequestMapping("/person")
public class PersonRestController {

    Map<Integer, Person> personMap = new HashMap<>();

    @PostConstruct
    public void init() {
        personMap.put(1, new Person(1, "Mert", "Caliskan", "mcaliskan@gmail.com"));
        personMap.put(2, new Person(2, "Steve", "Millidge", "smillidge@c2b2.co.uk"));
        personMap.put(3, new Person(3, "Andrew", "Pielage", "apielage@c2b2.co.uk"));
    }

    @RequestMapping("/all")
    public Collection<Person> getAll() {
        return personMap.values();
    }
}
