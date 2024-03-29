package org.woehlke.jakartaee.petclinic;

import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.woehlke.jakartaee.petclinic.unit.OwnerUnitTest;
import org.woehlke.jakartaee.petclinic.unit.PetUnitTest;
import org.woehlke.jakartaee.petclinic.unit.PetTypeUnitTest;
import org.woehlke.jakartaee.petclinic.unit.SpecialtyUnitTest;
import org.woehlke.jakartaee.petclinic.unit.VetUnitTest;
import org.woehlke.jakartaee.petclinic.unit.VisitUnitTest;

@Log
@Suite
@SelectClasses({
        SpecialtyUnitTest.class, VetUnitTest.class,
        PetTypeUnitTest.class, PetUnitTest.class,
        VisitUnitTest.class, OwnerUnitTest.class
})
public class EntityUnitTestSuite {

    @BeforeAll
    public void runBeforeSuite(){
      log.info("START");
    }

    @AfterAll
    public void runAfterSuite(){
        log.info("DONE");
    }
}
