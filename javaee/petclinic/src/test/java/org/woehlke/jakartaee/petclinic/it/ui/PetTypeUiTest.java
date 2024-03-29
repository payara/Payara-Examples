package org.woehlke.jakartaee.petclinic.it.ui;

import lombok.extern.java.Log;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.woehlke.jakartaee.petclinic.deployments.Deployments;
import org.woehlke.jakartaee.petclinic.deployments.UnitTestData;
import org.woehlke.jakartaee.petclinic.it.ui.pages.HomePage;
import org.woehlke.jakartaee.petclinic.it.ui.pages.PetTypePage;

import java.net.URL;

import static org.jboss.arquillian.graphene.Graphene.goTo;

@Log
@RunAsClient
@RunWith(Arquillian.class)
public class PetTypeUiTest extends UnitTestData {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Deployments.createDeployment();
    }

    @Drone
    WebDriver driver;

    @ArquillianResource
    URL deploymentUrl;

    @Page
    private HomePage homePage;

    @Page
    private PetTypePage petTypePage;

    private void goToPetTypePage(){
        goTo(PetTypePage.class);
        petTypePage.fullscreen();
        Assert.assertTrue(petTypePage.isFlowStateList());
    }

    @Test
    @InSequence(1)
    public void openHomePage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" openHomePage ");
        log.info("------------------------------------------------------------------------------------");
        goTo(HomePage.class);
        homePage.assertPageIsLoaded();
        log.info("------------------------------------------------------------------------------------");
        log.info(" openHomePage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(2)
    public void openPetTypePage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" openPetTypePage ");
        log.info("------------------------------------------------------------------------------------");
        goToPetTypePage();
        Assert.assertTrue(petTypePage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" openPetTypePage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(3)
    public void addNewPetTypePageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewPetTypePageWithCancel ");
        log.info("------------------------------------------------------------------------------------");
        goToPetTypePage();
        Assert.assertTrue(petTypePage.isFlowStateList());
        petTypePage.clickAddNewEntityButton();
        Assert.assertTrue(petTypePage.isFlowStateNew());
        petTypePage.clickCancelNewEntityButton();
        Assert.assertTrue(petTypePage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewPetTypePageWithCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(4)
    public void addNewPetTypePageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewPetTypePageWithSave ");
        log.info("------------------------------------------------------------------------------------");
        goToPetTypePage();
        Assert.assertTrue(petTypePage.isFlowStateList());
        for(String name:petTypeNameArray){
            petTypePage.clickAddNewEntityButton();
            Assert.assertTrue(petTypePage.isFlowStateNew());
            petTypePage.addNewEntity(name);
            Assert.assertTrue(petTypePage.isFlowStateDetails());
            petTypePage.clickCancelDetailsButton();
            Assert.assertTrue(petTypePage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewPetTypePageWithSave DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(5)
    public void openPetTypeDetailsPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" openPetTypeDetailsPage ");
        log.info("------------------------------------------------------------------------------------");
        goToPetTypePage();
        Assert.assertTrue(petTypePage.isFlowStateList());
        petTypePage.clickShowDetailsFormButton0();
        Assert.assertTrue(petTypePage.isFlowStateDetails());
        petTypePage.clickCancelDetailsButton();
        Assert.assertTrue(petTypePage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" openPetTypeDetailsPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(6)
    public void editPetTypePageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" editPetTypePageWithCancel ");
        log.info("------------------------------------------------------------------------------------");
        goToPetTypePage();
        Assert.assertTrue(petTypePage.isFlowStateList());
        for(int i=0; i<petTypeNameArray.length; i++) {
            log.info(" " + i);
            petTypePage.clickShowDetailsFormButton(i);
            Assert.assertTrue(petTypePage.isFlowStateDetails());
            petTypePage.clickShowEditForm();
            Assert.assertTrue(petTypePage.isFlowStateEdit());
            petTypePage.clickCancelEditButton();
            Assert.assertTrue(petTypePage.isFlowStateDetails());
            petTypePage.clickCancelDetailsButton();
            Assert.assertTrue(petTypePage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" editPetTypePageWithCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(7)
    public void editSpecialtyPageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" editSpecialtyPageWithSave ");
        log.info("------------------------------------------------------------------------------------");
        goToPetTypePage();
        Assert.assertTrue(petTypePage.isFlowStateList());
        for(int i=0; i<petTypeNameArray.length; i++){
            log.info(" "+i);
            petTypePage.clickShowDetailsFormButton(i);
            Assert.assertTrue(petTypePage.isFlowStateDetails());
            petTypePage.clickShowEditForm();
            Assert.assertTrue(petTypePage.isFlowStateEdit());
            petTypePage.editNameAddString();
            Assert.assertTrue(petTypePage.isFlowStateDetails());
            petTypePage.clickCancelDetailsButton();
            Assert.assertTrue(petTypePage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" editSpecialtyPageWithSave DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(8)
    public void deletePetTypePageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" deletePetTypePageWithCancel ");
        log.info("------------------------------------------------------------------------------------");
        goToPetTypePage();
        Assert.assertTrue(petTypePage.isFlowStateList());
        for(int i=0; i<petTypeNameArray.length; i++) {
            petTypePage.clickShowDetailsFormButton0();
            Assert.assertTrue(petTypePage.isFlowStateDetails());
            petTypePage.clickShowDeleteForm();
            Assert.assertTrue(petTypePage.isFlowStateDelete());
            petTypePage.clickCancelDeleteButton();
            Assert.assertTrue(petTypePage.isFlowStateDetails());
            petTypePage.clickCancelDetailsButton();
            Assert.assertTrue(petTypePage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" deletePetTypePageWithCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(9)
    public void deletePetTypePageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" deletePetTypePageWithSave ");
        log.info("------------------------------------------------------------------------------------");
        goToPetTypePage();
        Assert.assertTrue(petTypePage.isFlowStateList());
        for(int i=0; i<petTypeNameArray.length; i++) {
            petTypePage.clickShowDetailsFormButton0();
            Assert.assertTrue(petTypePage.isFlowStateDetails());
            petTypePage.clickShowDeleteForm();
            Assert.assertTrue(petTypePage.isFlowStateDelete());
            petTypePage.clickSaveDeleteButton();
            Assert.assertTrue(petTypePage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" deletePetTypePageWithSave DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

}
