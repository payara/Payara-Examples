/*
All the code that follow is
Copyright (c) 2022, Thomas Woehlke. All Rights Reserved.
Not for reuse without permission.
*/

package org.woehlke.jakartaee.petclinic.it.ui.pages;
import java.util.Map;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.woehlke.jakartaee.petclinic.application.framework.views.CrudFlowState;


@Location("petType.jsf")
public class PetTypePage implements CrudFlowStatePage {
    private Map<String, String> data;
    @Drone
    private WebDriver driver;
    private int timeout = 15;

    @FindBy(id="petTypeViewFlowFlowState")
    private WebElement crudFlowState;

    @FindBy(id = "entityDataTableForm:showNewFormButton")
    private WebElement showNewFormButton;

    @FindBy(id = "addEntityForm:cancelNew")
    private WebElement cancelNewButton;

    @FindBy(id = "addEntityForm:newPetTypeName")
    private WebElement newEntityNameInput;

    @FindBy(id = "addEntityForm:saveNewButton")
    private WebElement saveNewButton;

    @FindBy(id = "detailsEntityForm:showEditFormButton")
    private WebElement showEditFormButton;

    @FindBy(id = "editPetTypeForm:cancelEdit")
    private WebElement cancelEditButton;

    @FindBy(id = "editPetTypeForm:editPetTypeName")
    private WebElement editEntityNameInput;

    @FindBy(id = "editPetTypeForm:saveEditButton")
    private WebElement saveEditButton;

    @FindBy(id = "detailsEntityForm:deleteSelectedButton")
    private WebElement showDeleteFormButton;

    @FindBy(id="deleteEntityForm:cancelDelete")
    private WebElement canceDeleteButton;

    @FindBy(id="deleteEntityForm:deleteButton")
    private WebElement saveDeleteButton;

    @FindBy(id = "findEntityForm:searchButton")
    private WebElement searchButton;

    @FindBy(id = "findEntityForm:clearSearchButton")
    private WebElement clearSearchButton;


    @FindBy(id = "entityDataTableForm:entityDataTable:0:showDetailsFormButton")
    @CacheLookup
    private WebElement showDetailsFormButton0;

    @FindBy(id = "entityDataTableForm:entityDataTable:1:showDetailsFormButton")
    @CacheLookup
    private WebElement showDetailsFormButton1;

    @FindBy(id = "entityDataTableForm:entityDataTable:2:showDetailsFormButton")
    @CacheLookup
    private WebElement showDetailsFormButton2;

    @FindBy(id = "entityDataTableForm:entityDataTable:3:showDetailsFormButton")
    @CacheLookup
    private WebElement showDetailsFormButton3;

    @FindBy(id = "entityDataTableForm:entityDataTable:4:showDetailsFormButton")
    @CacheLookup
    private WebElement showDetailsFormButton4;

    public void clickShowDetailsFormButton(int i) {
        switch (i){
            case 0: clickShowDetailsFormButton0(); break;
            case 1: clickShowDetailsFormButton1(); break;
            case 2: clickShowDetailsFormButton2(); break;
            case 3: clickShowDetailsFormButton3(); break;
            case 4: clickShowDetailsFormButton4(); break;
            default: break;
        }
    }

    @FindBy(id="detailsEntityForm:cancelDetails")
    private WebElement cancelDetailsButton;


    public PetTypePage clickAddNewEntityButton() {
        //showNewFormButton.sendKeys(Keys.END);
        //showNewFormButton.sendKeys(Keys.DOWN);
        //showNewFormButton.sendKeys(Keys.ENTER);
        Graphene.guardHttp(showNewFormButton).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickCancelNewEntityButton() {
        //cancelNewButton.sendKeys(Keys.DOWN);
        //cancelNewButton.sendKeys(Keys.END);
        //cancelNewButton.sendKeys(Keys.ENTER);
        Graphene.guardHttp(cancelNewButton).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage addNewEntity(String name) {
        newEntityNameInput.sendKeys(name);
        //saveNewButton.sendKeys(Keys.DOWN);
        //saveNewButton.sendKeys(Keys.END);
        //saveNewButton.sendKeys(Keys.ENTER);
        Graphene.guardHttp(saveNewButton).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickShowEditForm() {
        //showEditFormButton.sendKeys(Keys.DOWN);
        //showEditFormButton.sendKeys(Keys.END);
        //showEditFormButton.sendKeys(Keys.ENTER);
        Graphene.guardHttp(showEditFormButton).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickCancelEditButton() {
        Graphene.guardHttp(cancelEditButton).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage editNameAddString() {
        String name = editEntityNameInput.getText();
        name += " Test";
        editEntityNameInput.sendKeys(name);
        //saveEditButton.sendKeys(Keys.DOWN);
        //saveEditButton.sendKeys(Keys.END);
        //saveEditButton.sendKeys(Keys.ENTER);
        Graphene.guardHttp(saveEditButton).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickShowDeleteForm() {
        //showDeleteFormButton.sendKeys(Keys.DOWN);
        //showDeleteFormButton.sendKeys(Keys.END);
        //showDeleteFormButton.sendKeys(Keys.ENTER);
        Graphene.guardHttp(showDeleteFormButton).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickCancelDeleteButton() {
        //canceDeleteButton.sendKeys(Keys.DOWN);
        //canceDeleteButton.sendKeys(Keys.END);
        //canceDeleteButton.sendKeys(Keys.ENTER);
        Graphene.guardHttp(canceDeleteButton).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickSaveDeleteButton() {
        //saveDeleteButton.sendKeys(Keys.DOWN);
        //saveDeleteButton.sendKeys(Keys.END);
        //saveDeleteButton.sendKeys(Keys.ENTER);
        Graphene.guardHttp(saveDeleteButton).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickSearchButton() {
        Graphene.guardHttp(searchButton).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickClearSearchButton() {
        //clearSearchButton.sendKeys(Keys.DOWN);
        //clearSearchButton.sendKeys(Keys.END);
        //clearSearchButton.sendKeys(Keys.ENTER);
        Graphene.guardHttp(clearSearchButton).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickShowDetailsFormButton0() {
        //showDetailsFormButton0.sendKeys(Keys.DOWN);
        //showDetailsFormButton0.sendKeys(Keys.END);
        //showDetailsFormButton0.sendKeys(Keys.ENTER);
        Graphene.guardHttp(showDetailsFormButton0).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickShowDetailsFormButton1() {
        //showDetailsFormButton1.sendKeys(Keys.DOWN);
        //showDetailsFormButton1.sendKeys(Keys.END);
        //showDetailsFormButton1.sendKeys(Keys.ENTER);
        Graphene.guardHttp(showDetailsFormButton1).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickShowDetailsFormButton2() {
        //showDetailsFormButton2.sendKeys(Keys.DOWN);
        //showDetailsFormButton2.sendKeys(Keys.END);
        //showDetailsFormButton2.sendKeys(Keys.ENTER);
        Graphene.guardHttp(showDetailsFormButton2).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickShowDetailsFormButton3() {
        //showDetailsFormButton3.sendKeys(Keys.DOWN);
        //showDetailsFormButton3.sendKeys(Keys.END);
        //showDetailsFormButton3.sendKeys(Keys.ENTER);
        Graphene.guardHttp(showDetailsFormButton3).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickShowDetailsFormButton4() {
        //showDetailsFormButton4.sendKeys(Keys.DOWN);
        //showDetailsFormButton4.sendKeys(Keys.END);
        //showDetailsFormButton4.sendKeys(Keys.ENTER);
        Graphene.guardHttp(showDetailsFormButton4).click();
        this.fullscreen();
        return this;
    }

    public PetTypePage clickCancelDetailsButton() {
        //cancelDetailsButton.sendKeys(Keys.DOWN);
        //cancelDetailsButton.sendKeys(Keys.END);
        //cancelDetailsButton.sendKeys(Keys.ENTER);
        Graphene.guardHttp(cancelDetailsButton).click();
        this.fullscreen();
        return this;
    }

    @Override
    public boolean isFlowStateList() {
        return CrudFlowState.LIST.name().compareTo(crudFlowState.getText())==0;
    }

    @Override
    public boolean isFlowStateDetails() {
        return CrudFlowState.DETAILS.name().compareTo(crudFlowState.getText())==0;
    }

    @Override
    public boolean isFlowStateNew() {
        return CrudFlowState.NEW.name().compareTo(crudFlowState.getText())==0;
    }

    @Override
    public boolean isFlowStateEdit() {
        return CrudFlowState.EDIT.name().compareTo(crudFlowState.getText())==0;
    }

    @Override
    public boolean isFlowStateDelete() {
        return CrudFlowState.DELETE.name().compareTo(crudFlowState.getText())==0;
    }

    @Override
    public boolean isFlowStateSearchResult() {
        return CrudFlowState.LIST_SEARCH_RESULT.name().compareTo(crudFlowState.getText())==0;
    }

    public void fullscreen(){
        driver.manage().window().fullscreen();
    }

    public PetTypePage() {
    }

    public PetTypePage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public PetTypePage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public PetTypePage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }
}
