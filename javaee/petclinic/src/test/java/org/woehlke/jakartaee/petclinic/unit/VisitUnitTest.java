package org.woehlke.jakartaee.petclinic.unit;

import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.woehlke.jakartaee.petclinic.deployments.UnitTestData;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import java.util.Collections;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VisitUnitTest extends UnitTestData {

    @Test
    @Order(1)
    void runEntityTest01(){
        log.info("runEntityTest01");
        assertThat(uuid01.compareTo(uuid02)!=0);
        Pet pet = petList.get(2);
        Visit o01 = new Visit();
        o01.setPet(pet);
        o01.setDate(dob01);
        o01.setDescription(name01);
        o01.setUuid(uuid01);
        Visit o02 = new Visit();
        o02.setPet(pet);
        o02.setDate(dob01);
        o01.setDescription(name01);
        o02.setUuid(uuid02);
        assertThat(o01.compareTo(o02)==0);
    }

    @Test
    @Order(2)
    void runEntityTest02(){
        log.info("runEntityTest02");
        assertThat(uuid01.compareTo(uuid02)!=0);
        assertThat(id01.compareTo(id02)!=0);
        Pet pet = petList.get(2);
        Visit o01 = new Visit();
        o01.setPet(pet);
        o01.setDate(dob01);
        o01.setDescription(name01);
        o01.setUuid(uuid01);
        o01.setId(id01);
        o01.setUuid(uuid01);
        Visit o02 = new Visit();
        o02.setPet(pet);
        o02.setDate(dob01);
        o01.setDescription(name01);
        o02.setUuid(uuid02);
        o02.setId(id02);
        assertThat(o01.compareTo(o02)==0);
    }

    @Test
    @Order(3)
    void runEntityTest11(){
        log.info("runEntityTest11");
        assertThat(uuid01.compareTo(uuid02)!=0);
        Pet pet = petList.get(2);
        Visit o01 = new Visit();
        o01.setPet(pet);
        o01.setDate(dob01);
        o01.setDescription(name01);
        o01.setUuid(uuid01);
        Visit o02 = new Visit();
        o02.setPet(pet);
        o02.setDate(dob02);
        o02.setDescription(name02);
        o02.setUuid(uuid02);
        assertThat(o01.compareTo(o02)!=0);
    }

    @Test
    @Order(4)
    void runEntityTest12(){
        log.info("runEntityTest12");
        assertThat(uuid01.compareTo(uuid02)!=0);
        assertThat(id01.compareTo(id02)!=0);
        Pet pet = petList.get(2);
        Visit o01 = new Visit();
        o01.setPet(pet);
        o01.setDate(dob01);
        o01.setDescription(name01);
        o01.setId(id01);
        o01.setUuid(uuid01);
        Visit o02 = new Visit();
        o02.setPet(pet);
        o02.setDate(dob02);
        o02.setDescription(name02);
        o02.setUuid(uuid02);
        o02.setId(id02);
        assertThat(o01.compareTo(o02)!=0);
    }

    @Test
    @Order(5)
    void runEntityTest21(){
        log.info("runEntityTest21");
        Pet pet = petList.get(2);
        Visit o01 = new Visit();
        o01.setPet(pet);
        o01.setDate(dob01);
        o01.setDescription(name01);
        o01.setUuid(uuid);
        Visit o02 = new Visit();
        o02.setPet(pet);
        o02.setDate(dob02);
        o02.setDescription(name02);
        o02.setUuid(uuid);
        assertThat(o01.compareTo(o02)!=0);
    }

    @Test
    @Order(6)
    void runEntityTest22(){
        log.info("runEntityTest22");
        Pet pet = petList.get(2);
        Visit o01 = new Visit();
        o01.setPet(pet);
        o01.setDate(dob01);
        o01.setDescription(name01);
        o01.setId(id);
        o01.setUuid(uuid);
        Visit o02 = new Visit();
        o02.setPet(pet);
        o02.setDate(dob02);
        o02.setDescription(name02);
        o02.setUuid(uuid);
        o02.setId(id);
        assertThat(o01.compareTo(o02)!=0);
    }

    @Test
    @Order(7)
    void runEntityTest99(){
        log.info("runEntityTest99");
        Collections.sort(visitList);
        assertThat(visitList.size() > 2);
        Iterator<Visit> i =  visitList.iterator();
        Visit firstEntity = i.next();
        while(i.hasNext()){
            Visit secondEntity = i.next();
            int compared = secondEntity.compareTo(firstEntity);
            //log.info("runEntityTest99: "+ compared);
            assertThat(compared > 0);
            firstEntity = secondEntity;
        }
    }
}
