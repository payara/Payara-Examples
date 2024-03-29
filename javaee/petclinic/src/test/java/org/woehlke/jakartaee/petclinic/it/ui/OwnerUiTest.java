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
import org.woehlke.jakartaee.petclinic.it.ui.pages.OwnerPage;
import org.woehlke.jakartaee.petclinic.owner.Owner;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import java.net.URL;

import static org.jboss.arquillian.graphene.Graphene.goTo;

@Log
@RunAsClient
@RunWith(Arquillian.class)
public class OwnerUiTest extends UnitTestData {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Deployments.createDeployment();
    }

    @Drone
    private WebDriver driver;

    @ArquillianResource
    private URL deploymentUrl;

    @Page
    private HomePage homePage;

    @Page
    private OwnerPage ownerPage;

    private void goToOwnerPage(){
        goTo(OwnerPage.class);
        ownerPage.fullscreen();
        Assert.assertTrue(ownerPage.isFlowStateList());
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
    public void openOwnerPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" openOwnerPage ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" openOwnerPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(3)
    public void addNewOwnerPageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewOwnerPage ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        ownerPage.clickAddNewEntityButton();
        Assert.assertTrue(ownerPage.isFlowStateNew());
        ownerPage.clickCancelNewEntityButton();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewOwnerPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(4)
    public void addNewOwnerPageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewOwnerPageWithSave ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        for(Owner oo:ownerList){
            ownerPage.clickAddNewEntityButton();
            Assert.assertTrue(ownerPage.isFlowStateNew());
            ownerPage.addNewEntity(oo);
            Assert.assertTrue(ownerPage.isFlowStateDetails());
            ownerPage.clickCancelDetailsButton();
            Assert.assertTrue(ownerPage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewOwnerPageWithSave DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(5)
    public void openOwnerDetailsPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" openOwnerDetailsPage ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        ownerPage.clickShowDetailsFormButton0();
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        ownerPage.clickCancelDetailsButton();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" openOwnerDetailsPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(6)
    public void editOwnerPageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" editOwnerPageWithCancel ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        ownerPage.clickShowDetailsFormButton0();
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        // ----------------------------------
        ownerPage.clickShowEditForm();
        Assert.assertTrue(ownerPage.isFlowStateEdit());
        ownerPage.clickCancelEditButton();
        // ----------------------------------
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        ownerPage.clickCancelDetailsButton();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" editOwnerPageWithCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(7)
    public void editOwnerPageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" editOwnerPageWithSave ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        for(int i=0; i<ownerList.size(); i++){
            log.info(" "+i);
            ownerPage.clickShowDetailsFormButton(i);
            Assert.assertTrue(ownerPage.isFlowStateDetails());
            ownerPage.clickShowEditForm();
            Assert.assertTrue(ownerPage.isFlowStateEdit());
            ownerPage.editNameAddString();
            Assert.assertTrue(ownerPage.isFlowStateDetails());
            ownerPage.clickCancelDetailsButton();
            Assert.assertTrue(ownerPage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" editOwnerPageWithSave DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(8)
    public void addNewPetToOwnerPageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewPetToOwnerPage ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        ownerPage.clickShowDetailsFormButton1();
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        // ----------------------------------
        ownerPage.clickAddNewPetButton();
        Assert.assertTrue(ownerPage.isFlowStateNewPet());
        ownerPage.clickCancelNewPetButton();
        // ----------------------------------
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        ownerPage.clickCancelDetailsButton();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewPetToOwnerPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(9)
    public void addNewPetToOwnerPageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewPetToOwnerPageWithSave ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        // ----------------------------------
        for(Pet pet:petList){
            ownerPage.clickShowDetailsFormButton2();
            Assert.assertTrue(ownerPage.isFlowStateDetails());
            ownerPage.clickAddNewPetButton();
            Assert.assertTrue(ownerPage.isFlowStateNewPet());
            ownerPage.clickAddAndSaveNewPet(pet);
            //+++
            Assert.assertTrue(ownerPage.isFlowStateDetails());
            ownerPage.clickCancelDetailsButton();
            Assert.assertTrue(ownerPage.isFlowStateList());
        }
        // ----------------------------------
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewPetToOwnerPageWithSave DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(10)
    public void editPetOfOwnerPageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" editPetOfOwnerPageWithCancel ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        ownerPage.clickShowDetailsFormButton2();
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        // ----------------------------------
        int j= Math.min(petList.size(), 8);
        for(int i=0;i<j;i++) {
            ownerPage.clickShowEditPetForm(i);
            Assert.assertTrue(ownerPage.isFlowStateEditPet());
            ownerPage.clickCancelEditedPetButton();
        }
        // ----------------------------------
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        ownerPage.clickCancelDetailsButton();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" editPetOfOwnerPageWithCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(11)
    public void editPetOfOwnerPageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" editPetOfOwnerPageWithSave ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        ownerPage.clickShowDetailsFormButton2();
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        // ----------------------------------
        int j= Math.min(petList.size(), 8);
        for(int i=0;i<j;i++) {
            ownerPage.clickShowEditPetForm(i);
            Assert.assertTrue(ownerPage.isFlowStateEditPet());
            ownerPage.clickSaveEditPetButton();
        }
        // ----------------------------------
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        ownerPage.clickCancelDetailsButton();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" editPetOfOwnerPageWithSave DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(12)
    public void addNewVisitToOwnersFirstPetPageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" editNewVisitToOwnersFirstPetPageWithCancel ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        ownerPage.clickShowDetailsFormButton2();
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        // ----------------------------------
        ownerPage.clickAddNewVisitToPet0Button();
        Assert.assertTrue(ownerPage.isFlowStateNewVisit());
        ownerPage.clickCancelNewVisitButton();
        // ----------------------------------
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        ownerPage.clickCancelDetailsButton();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" editNewVisitToOwnersFirstPetPageWithCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(13)
    public void addNewVisitToOwnersFirstPetPageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" editNewVisitToOwnersFirstPetPageWithSave ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        ownerPage.clickShowDetailsFormButton2();
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        // ----------------------------------
        for(Visit v: visitList){
            ownerPage.clickAddNewVisitToPet0Button();
            Assert.assertTrue(ownerPage.isFlowStateNewVisit());
            ownerPage.clickAddAndSaveNewVisitButton(v.getDate(),v.getDescription());
            Assert.assertTrue(ownerPage.isFlowStateDetails());
        }
        // ----------------------------------
        ownerPage.clickCancelDetailsButton();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" editNewVisitToOwnersFirstPetPageWithSave DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(14)
    public void deleteOwnerPageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" deleteOwnerPageWithCancel ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        for(int i=0; i<ownerList.size(); i++) {
            ownerPage.clickShowDetailsFormButton(i);
            Assert.assertTrue(ownerPage.isFlowStateDetails());
            // ----------------------------------
            ownerPage.clickDeleteSelectedButton();
            Assert.assertTrue(ownerPage.isFlowStateDelete());
            ownerPage.clickCancelDeleteButton();
            // ----------------------------------
            Assert.assertTrue(ownerPage.isFlowStateDetails());
            ownerPage.clickCancelDetailsButton();
            Assert.assertTrue(ownerPage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" deleteOwnerPageWithCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(15)
    public void deleteOwnerPageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" deleteOwnerPageWithSave ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        ownerPage.fullscreen();
        for(int i=0; i<ownerList.size(); i++) {
            ownerPage.clickShowDetailsFormButton0();
            Assert.assertTrue(ownerPage.isFlowStateDetails());
            ownerPage.clickDeleteSelectedButton();
            Assert.assertTrue(ownerPage.isFlowStateDelete());
            ownerPage.clickConfirmDeleteButton();
            Assert.assertTrue(ownerPage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" deleteOwnerPageWithSave DONE ");
        log.info("------------------------------------------------------------------------------------");
    }
}
