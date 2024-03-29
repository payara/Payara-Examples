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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.woehlke.jakartaee.petclinic.deployments.Deployments;
import org.woehlke.jakartaee.petclinic.deployments.UnitTestData;
import org.woehlke.jakartaee.petclinic.it.ui.pages.*;

import java.net.URL;

import static org.jboss.arquillian.graphene.Graphene.goTo;

@Log
@RunAsClient
@RunWith(Arquillian.class)
public class TellTheStoryUiTest  extends UnitTestData {

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
    private InformationPage informationPage;

    @Page
    private VeterinarianPage veterinarianPage;

    @Page
    private SpecialtyPage specialtyPage;

    @Page
    private PetTypePage petTypePage;

    @Page
    private OwnerPage ownerPage;

    private void goToOwnerPage(){
        goTo(OwnerPage.class);
        ownerPage.fullscreen();
        Assert.assertTrue(ownerPage.isFlowStateList());
    }

    private void goToPetTypePage(){
        goTo(PetTypePage.class);
        petTypePage.fullscreen();
        Assert.assertTrue(petTypePage.isFlowStateList());
    }

    private void goToSpecialtyPage(){
        goTo(SpecialtyPage.class);
        specialtyPage.fullscreen();
        Assert.assertTrue(specialtyPage.isFlowStateList());
    }

    private void goToVeterinarianPage(){
        goTo(VeterinarianPage.class);
        veterinarianPage.fullscreen();
        Assert.assertTrue(veterinarianPage.isFlowStateList());
    }

    @Test
    @InSequence(1)
    public void homePage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" homePage ");
        log.info("------------------------------------------------------------------------------------");
        goTo(HomePage.class);
        homePage.fullscreen();
        homePage.assertPageIsLoaded();
        log.info("------------------------------------------------------------------------------------");
        log.info(" homePage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(2)
    public void informationPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" informationPage ");
        log.info("------------------------------------------------------------------------------------");
        goTo(InformationPage.class);
        informationPage.fullscreen();
        informationPage.assertPageIsLoaded();
        log.info("------------------------------------------------------------------------------------");
        log.info(" informationPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(3)
    public void specialtyPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyPage ");
        log.info("------------------------------------------------------------------------------------");
        goToSpecialtyPage();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(4)
    public void specialtyAddNewForm() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyAddNewForm ");
        log.info("------------------------------------------------------------------------------------");
        specialtyPage.clickAddNewEntityButton();
        specialtyPage.fullscreen();
        Assert.assertTrue(specialtyPage.isFlowStateNew());
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyAddNewForm DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(5)
    public void specialtyAddNewFormCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyAddNewFormCancel ");
        log.info("------------------------------------------------------------------------------------");
        specialtyPage.clickCancelNewEntityButton();
        specialtyPage.fullscreen();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyAddNewFormCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(6)
    public void specialtyDetailsForm() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyDetailsForm ");
        log.info("------------------------------------------------------------------------------------");
        specialtyPage.clickShowDetailsFormButton0();
        specialtyPage.fullscreen();
        Assert.assertTrue(specialtyPage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyDetailsForm DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(7)
    public void specialtyEditForm() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyEditForm ");
        log.info("------------------------------------------------------------------------------------");
        specialtyPage.clickShowEditForm();
        specialtyPage.fullscreen();
        Assert.assertTrue(specialtyPage.isFlowStateEdit());
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyEditForm DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(8)
    public void specialtyEditFormCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyEditFormCancel ");
        log.info("------------------------------------------------------------------------------------");
        specialtyPage.clickCancelEditButton();
        specialtyPage.fullscreen();
        Assert.assertTrue(specialtyPage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyEditFormCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(9)
    public void specialtyDeleteForm() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyDeleteForm ");
        log.info("------------------------------------------------------------------------------------");
        specialtyPage.clickShowDeleteForm();
        specialtyPage.fullscreen();
        Assert.assertTrue(specialtyPage.isFlowStateDelete());
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyDeleteForm DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(10)
    public void specialtyDeleteFormCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyDeleteFormCancel ");
        log.info("------------------------------------------------------------------------------------");
        specialtyPage.clickCancelDeleteButton();
        specialtyPage.fullscreen();
        Assert.assertTrue(specialtyPage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyDeleteFormCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(11)
    public void specialtyDetailsFormCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyDetailsFormCancel ");
        log.info("------------------------------------------------------------------------------------");
        specialtyPage.clickCancelDetailsButton();
        specialtyPage.fullscreen();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" specialtyDetailsFormCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(12)
    public void petTypePage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypePage ");
        log.info("------------------------------------------------------------------------------------");
        goToPetTypePage();
        Assert.assertTrue(petTypePage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypePage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(13)
    public void petTypeAddNewPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeAddNewPage ");
        log.info("------------------------------------------------------------------------------------");
        petTypePage.clickAddNewEntityButton();
        petTypePage.fullscreen();
        Assert.assertTrue(petTypePage.isFlowStateNew());
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeAddNewPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(14)
    public void petTypeAddNewPageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeAddNewPageCancel ");
        log.info("------------------------------------------------------------------------------------");
        petTypePage.clickCancelNewEntityButton();
        petTypePage.fullscreen();
        Assert.assertTrue(petTypePage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeAddNewPageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(15)
    public void petTypeDetailsPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeDetailsPage ");
        log.info("------------------------------------------------------------------------------------");
        petTypePage.clickShowDetailsFormButton0();
        petTypePage.fullscreen();
        Assert.assertTrue(petTypePage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeDetailsPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(16)
    public void petTypeEditPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeEditPage ");
        log.info("------------------------------------------------------------------------------------");
        petTypePage.clickShowEditForm();
        petTypePage.fullscreen();
        Assert.assertTrue(petTypePage.isFlowStateEdit());
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeEditPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(17)
    public void petTypeEditPageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeEditPageCancel ");
        log.info("------------------------------------------------------------------------------------");
        petTypePage.clickCancelEditButton();
        petTypePage.fullscreen();
        Assert.assertTrue(petTypePage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeEditPageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(18)
    public void petTypeDeletePage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeDeletePage ");
        log.info("------------------------------------------------------------------------------------");
        petTypePage.clickShowDeleteForm();
        petTypePage.fullscreen();
        Assert.assertTrue(petTypePage.isFlowStateDelete());
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeDeletePage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(19)
    public void petTypeDeletePageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeDeletePageCancel ");
        log.info("------------------------------------------------------------------------------------");
        petTypePage.clickCancelDeleteButton();
        petTypePage.fullscreen();
        Assert.assertTrue(petTypePage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeDeletePageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(20)
    public void petTypeDetailsPageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeDetailsPageCancel ");
        log.info("------------------------------------------------------------------------------------");
        petTypePage.clickCancelDetailsButton();
        petTypePage.fullscreen();
        Assert.assertTrue(petTypePage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" petTypeDetailsPageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(21)
    public void veterinarianPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianPage ");
        log.info("------------------------------------------------------------------------------------");
        goToVeterinarianPage();
        Assert.assertTrue(veterinarianPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(22)
    public void veterinariaAddNewPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinariaAddNewPage ");
        log.info("------------------------------------------------------------------------------------");
        veterinarianPage.clickAddNewEntityButton();
        veterinarianPage.fullscreen();
        Assert.assertTrue(veterinarianPage.isFlowStateNew());
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinariaAddNewPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(23)
    public void veterinariaAddNewPageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinariaAddNewPageCancel ");
        log.info("------------------------------------------------------------------------------------");
        veterinarianPage.clickCancelNewEntityButton();
        veterinarianPage.fullscreen();
        Assert.assertTrue(veterinarianPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinariaAddNewPageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }


    @Test
    @InSequence(24)
    public void veterinarianDetailsPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianDetailsPage ");
        log.info("------------------------------------------------------------------------------------");
        veterinarianPage.clickShowDetailsFormButton0();
        veterinarianPage.fullscreen();
        Assert.assertTrue(veterinarianPage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianDetailsPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(25)
    public void veterinarianEditPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianEditPage ");
        log.info("------------------------------------------------------------------------------------");
        veterinarianPage.clickShowEditForm();
        veterinarianPage.fullscreen();
        Assert.assertTrue(veterinarianPage.isFlowStateEdit());
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianEditPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(26)
    public void veterinarianEditPageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianEditPageCancel ");
        log.info("------------------------------------------------------------------------------------");
        veterinarianPage.clickCancelEditButton();
        veterinarianPage.fullscreen();
        Assert.assertTrue(veterinarianPage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianEditPageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(27)
    public void veterinarianDeletePage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianDeletePage ");
        log.info("------------------------------------------------------------------------------------");
        veterinarianPage.clickShowDeleteForm();
        veterinarianPage.fullscreen();
        Assert.assertTrue(veterinarianPage.isFlowStateDelete());
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianDeletePage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(28)
    public void veterinarianDeletePageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianDeletePageCancel ");
        log.info("------------------------------------------------------------------------------------");
        veterinarianPage.clickCancelDeleteButton();
        veterinarianPage.fullscreen();
        Assert.assertTrue(veterinarianPage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianDeletePageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(29)
    public void veterinarianDetailsPageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianDetailsPageCancel ");
        log.info("------------------------------------------------------------------------------------");
        veterinarianPage.clickCancelDetailsButton();
        veterinarianPage.fullscreen();
        Assert.assertTrue(veterinarianPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" veterinarianDetailsPageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(30)
    public void ownerPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerPage ");
        log.info("------------------------------------------------------------------------------------");
        goToOwnerPage();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(31)
    public void ownerAddNewPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerAddNewPage ");
        log.info("------------------------------------------------------------------------------------");
        ownerPage.clickAddNewEntityButton();
        ownerPage.fullscreen();
        Assert.assertTrue(ownerPage.isFlowStateNew());
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerAddNewPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(32)
    public void ownerAddNewPageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerAddNewPageCancel ");
        log.info("------------------------------------------------------------------------------------");
        ownerPage.clickCancelNewEntityButton();
        ownerPage.fullscreen();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerAddNewPageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(33)
    public void ownerDetailsPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerDetailsPage ");
        log.info("------------------------------------------------------------------------------------");
        ownerPage.clickShowDetailsFormButton0();
        ownerPage.fullscreen();
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerDetailsPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(34)
    public void ownerEditPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerEditPage ");
        log.info("------------------------------------------------------------------------------------");
        ownerPage.clickShowEditForm();
        ownerPage.fullscreen();
        Assert.assertTrue(ownerPage.isFlowStateEdit());
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerEditPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(35)
    public void ownerEditPageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerEditPageCancel ");
        log.info("------------------------------------------------------------------------------------");
        ownerPage.clickCancelEditButton();
        ownerPage.fullscreen();
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerEditPageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(36)
    public void ownerDeletePage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerDeletePage ");
        log.info("------------------------------------------------------------------------------------");
        ownerPage.clickDeleteSelectedButton();
        ownerPage.fullscreen();
        Assert.assertTrue(ownerPage.isFlowStateDelete());
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerDeletePage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(37)
    public void ownerDeletePageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerDeletePageCancel ");
        log.info("------------------------------------------------------------------------------------");
        ownerPage.clickCancelDeleteButton();
        ownerPage.fullscreen();
        Assert.assertTrue(ownerPage.isFlowStateDetails());
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerDeletePageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(38)
    public void ownerDetailsPageCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerDetailsPageCancel ");
        log.info("------------------------------------------------------------------------------------");
        ownerPage.clickCancelDetailsButton();
        ownerPage.fullscreen();
        Assert.assertTrue(ownerPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" ownerDetailsPageCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

}
