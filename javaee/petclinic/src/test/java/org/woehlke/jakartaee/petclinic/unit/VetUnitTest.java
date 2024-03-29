package org.woehlke.jakartaee.petclinic.unit;


import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.woehlke.jakartaee.petclinic.deployments.UnitTestData;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;
import org.woehlke.jakartaee.petclinic.vet.Vet;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VetUnitTest extends UnitTestData {

    @Test
    @Order(1)
    void runEntityTest01(){
        log.info("runEntityTest01");
        assertThat(uuid01.compareTo(uuid02)!=0);
        assertThat(name01.compareTo(name02)!=0);
        Vet o01 = new Vet();
        o01.setLastName(name01);
        o01.setFirstName(name02);
        o01.setUuid(uuid01);
        Vet o02 = new Vet();
        o02.setLastName(name01);
        o02.setFirstName(name02);
        o02.setUuid(uuid02);
        assertThat(o01.compareTo(o02)==0);
    }

    @Test
    @Order(2)
    void runEntityTest02(){
        log.info("runEntityTest02");
        assertThat(name01.compareTo(name02)!=0);
        assertThat(uuid01.compareTo(uuid02)!=0);
        assertThat(id01.compareTo(id02)!=0);
        Vet o01 = new Vet();
        o01.setLastName(name01);
        o01.setFirstName(name02);
        o01.setId(id01);
        o01.setUuid(uuid01);
        Vet o02 = new Vet();
        o02.setLastName(name01);
        o02.setFirstName(name02);
        o02.setUuid(uuid02);
        o02.setId(id02);
        assertThat(o01.compareTo(o02)==0);
    }

    @Test
    @Order(3)
    void runEntityTest11(){
        log.info("runEntityTest11");
        assertThat(name01.compareTo(name02)!=0);
        assertThat(uuid01.compareTo(uuid02)!=0);
        Vet o01 = new Vet();
        o01.setLastName(name01);
        o01.setFirstName(name01);
        o01.setUuid(uuid01);
        Vet o02 = new Vet();
        o02.setLastName(name02);
        o02.setFirstName(name02);
        o02.setUuid(uuid02);
        assertThat(o01.compareTo(o02)!=0);
    }

    @Test
    @Order(4)
    void runEntityTest12(){
        log.info("runEntityTest12");
        assertThat(name01.compareTo(name02)!=0);
        assertThat(uuid01.compareTo(uuid02)!=0);
        assertThat(id01.compareTo(id02)!=0);
        Vet o01 = new Vet();
        o01.setLastName(name01);
        o01.setFirstName(name01);
        o01.setId(id01);
        o01.setUuid(uuid01);
        Vet o02 = new Vet();
        o02.setLastName(name02);
        o02.setFirstName(name02);
        o02.setUuid(uuid02);
        o02.setId(id02);
        assertThat(o01.compareTo(o02)!=0);
    }

    @Test
    @Order(5)
    void runEntityTest21(){
        log.info("runEntityTest21");
        assertThat(name01.compareTo(name02)!=0);
        Vet o01 = new Vet();
        o01.setLastName(name01);
        o01.setFirstName(name01);
        o01.setUuid(uuid);
        Vet o02 = new Vet();
        o02.setLastName(name02);
        o02.setFirstName(name02);
        o02.setUuid(uuid);
        assertThat(o01.compareTo(o02)!=0);
    }

    @Test
    @Order(6)
    void runEntityTest22(){
        log.info("runEntityTest22");
        assertThat(name01.compareTo(name02)!=0);
        Vet o01 = new Vet();
        o01.setLastName(name01);
        o01.setFirstName(name01);
        o01.setId(id);
        o01.setUuid(uuid);
        Vet o02 = new Vet();
        o02.setLastName(name02);
        o02.setFirstName(name02);
        o02.setUuid(uuid);
        o02.setId(id);
        assertThat(o01.compareTo(o02)!=0);
    }

    @Test
    @Order(7)
    void runEntityTest99(){
        log.info("runEntityTest99");
        Collections.sort(vetList);
        assertThat(vetList.size() > 2);
        Iterator<Vet> i =  vetList.iterator();
        Vet firstEntity = i.next();
        while(i.hasNext()){
            Vet secondEntity = i.next();
            int compared = secondEntity.compareTo(firstEntity);
            assertThat(compared > 0);
            firstEntity = secondEntity;
        }
    }

    @Test
    @Order(8)
    public void testGetSpecialtiesAsList01(){
        log.info("testGetSpecialtiesAsList01");
        int expectedSize  = 0;
        Vet entity = new Vet();
        int size = entity.getSpecialties().size();
        assertThat(expectedSize==size);
    }

    @Test
    @Order(9)
    public void testGetSpecialtiesAsList02(){
        log.info("testGetSpecialtiesAsList02");
        int expectedSize  = 1;
        Vet entity = new Vet();
        String specialtyName = UnitTestData.specialtyNames[0];
        assertThat("Surgeon".compareTo(specialtyName)==0);
        Specialty specialty = new Specialty();
        specialty.setName(specialtyName);
        entity.addSpecialty(specialty);
        int size = entity.getSpecialties().size();
        assertThat(expectedSize == size);
    }

    @Test
    @Order(10)
    public void testGetSpecialtiesAsList03(){
        log.info("testGetSpecialtiesAsList03");
        Vet entity = new Vet();
        String specialtyName =  UnitTestData.specialtyNames[0];
        assertThat("Surgeon".compareTo(specialtyName)==0);
        Specialty specialty = new Specialty();
        specialty.setName(specialtyName);
        entity.addSpecialty(specialty);
        specialty = entity.getSpecialties().iterator().next();
        assertThat(specialtyName == specialty.getName());
    }

    @Test
    @Order(11)
    public void testGetSpecialtiesAsList04() {
        log.info("testGetSpecialtiesAsList04");
        Vet entity = new Vet();
        int expectedSize  = specialtySet.size();
        entity.setSpecialties(specialtySet);
        int size = entity.getSpecialtiesAsList().size();
        assertThat(expectedSize == size);
    }

    @Test
    @Order(12)
    public void testGetSpecialtiesAsList05() {
        log.info("testGetSpecialtiesAsList05");
        Vet entity = new Vet();
        entity.setSpecialties(specialtySet);
        List<Specialty> listSpecialty = entity.getSpecialtiesAsList();
        assertThat(specialtySet.size() == listSpecialty.size());
        assertThat(listSpecialty.size() > 2);
        Iterator<Specialty> i =  listSpecialty.iterator();
        Specialty firstSpecialty = i.next();
        while(i.hasNext()){
            Specialty secondSpecialty = i.next();
            int compared = secondSpecialty.compareTo(firstSpecialty);
            assertThat(compared > 0);
            firstSpecialty = secondSpecialty;
        }
    }

}
