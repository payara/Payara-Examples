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


/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 19.01.14
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */
@Log
@RunAsClient
@RunWith(Arquillian.class)
public class SpecialtyUiTest extends UnitTestData {

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
    private SpecialtyPage specialtyPage;

    private void goToSpecialtyPage(){
        goTo(SpecialtyPage.class);
        specialtyPage.fullscreen();
        Assert.assertTrue(specialtyPage.isFlowStateList());
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
    public void openSpecialtyPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" openSpecialtyPage ");
        log.info("------------------------------------------------------------------------------------");
        goToSpecialtyPage();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" openSpecialtyPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(3)
    public void addNewSpecialtyPageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewSpecialtyPageWithCancel ");
        log.info("------------------------------------------------------------------------------------");
        goToSpecialtyPage();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        specialtyPage.clickAddNewEntityButton();
        Assert.assertTrue(specialtyPage.isFlowStateNew());
        specialtyPage.clickCancelNewEntityButton();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewSpecialtyPageWithCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(4)
    public void addNewSpecialtyPageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewSpecialtyPageWithSave ");
        log.info("------------------------------------------------------------------------------------");
        goToSpecialtyPage();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        for(String name:specialtyNameArray){
            specialtyPage.clickAddNewEntityButton();
            Assert.assertTrue(specialtyPage.isFlowStateNew());
            specialtyPage.addNewEntity(name);
            Assert.assertTrue(specialtyPage.isFlowStateDetails());
            specialtyPage.clickCancelDetailsButton();
            Assert.assertTrue(specialtyPage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" addNewSpecialtyPageWithSave DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(5)
    public void openSpecialtyDetailsPage() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" openSpecialtyDetailsPage ");
        log.info("------------------------------------------------------------------------------------");
        goToSpecialtyPage();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        specialtyPage.clickShowDetailsFormButton0();
        Assert.assertTrue(specialtyPage.isFlowStateDetails());
        specialtyPage.clickCancelDetailsButton();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        log.info("------------------------------------------------------------------------------------");
        log.info(" openSpecialtyDetailsPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(6)
    public void editSpecialtyPageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" editSpecialtyPage ");
        log.info("------------------------------------------------------------------------------------");
        goToSpecialtyPage();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        for(int i=0; i<3; i++){
            log.info(" "+i);
            specialtyPage.clickShowDetailsFormButton(i);
            Assert.assertTrue(specialtyPage.isFlowStateDetails());
            specialtyPage.clickShowEditForm();
            Assert.assertTrue(specialtyPage.isFlowStateEdit());
            specialtyPage.clickCancelEditButton();
            Assert.assertTrue(specialtyPage.isFlowStateDetails());
            specialtyPage.clickCancelDetailsButton();
            Assert.assertTrue(specialtyPage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" editSpecialtyPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(7)
    public void editSpecialtyPageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" editSpecialtyPage ");
        log.info("------------------------------------------------------------------------------------");
        goToSpecialtyPage();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        for(int i=0; i<3; i++){
            log.info(" "+i);
            specialtyPage.clickShowDetailsFormButton(i);
            Assert.assertTrue(specialtyPage.isFlowStateDetails());
            specialtyPage.clickShowEditForm();
            Assert.assertTrue(specialtyPage.isFlowStateEdit());
            specialtyPage.editNameAddString();
            Assert.assertTrue(specialtyPage.isFlowStateDetails());
            specialtyPage.clickCancelDetailsButton();
            Assert.assertTrue(specialtyPage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" editSpecialtyPage DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(8)
    public void deleteSpecialtyPageWithCancel() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" deleteSpecialtyPageWithCancel ");
        log.info("------------------------------------------------------------------------------------");
        goToSpecialtyPage();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        for(int i=0; i<3; i++) {
            specialtyPage.clickShowDetailsFormButton(i);
            Assert.assertTrue(specialtyPage.isFlowStateDetails());
            specialtyPage.clickShowDeleteForm();
            Assert.assertTrue(specialtyPage.isFlowStateDelete());
            specialtyPage.clickCancelDeleteButton();
            Assert.assertTrue(specialtyPage.isFlowStateDetails());
            specialtyPage.clickCancelDetailsButton();
            Assert.assertTrue(specialtyPage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" deleteSpecialtyPageWithCancel DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

    @Test
    @InSequence(9)
    public void deleteSpecialtyPageWithSave() {
        log.info("------------------------------------------------------------------------------------");
        log.info(" deleteSpecialtyPageWithSave ");
        log.info("------------------------------------------------------------------------------------");
        goToSpecialtyPage();
        Assert.assertTrue(specialtyPage.isFlowStateList());
        for(int i=0; i<3; i++) {
            specialtyPage.clickShowDetailsFormButton0();
            Assert.assertTrue(specialtyPage.isFlowStateDetails());
            specialtyPage.clickShowDeleteForm();
            Assert.assertTrue(specialtyPage.isFlowStateDelete());
            specialtyPage.clickSaveDeleteButton();
            Assert.assertTrue(specialtyPage.isFlowStateList());
        }
        log.info("------------------------------------------------------------------------------------");
        log.info(" deleteSpecialtyPageWithSave DONE ");
        log.info("------------------------------------------------------------------------------------");
    }

}
