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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.woehlke.jakartaee.petclinic.application.framework.views.CrudFlowState;


@Location("veterinarian.jsf")
public class VeterinarianPage implements CrudFlowStatePage {
    private Map<String, String> data;
    @Drone
    private WebDriver driver;
    private int timeout = 15;

    @FindBy(id="vetViewFlowFlowState")
    private WebElement crudFlowState;

    @FindBy(id = "entityDataTableForm:showNewFormButton")
    private WebElement showNewFormButton;

    @FindBy(id = "addNewEntityForm:cancelNew")
    private WebElement cancelNewButton;

    @FindBy(id = "addNewEntityForm:firstNameAddNew")
    private WebElement newSurnameInput;

    @FindBy(id = "addNewEntityForm:lastNameAddNew")
    private WebElement newLastnameInput;

    @FindBy(id = "addNewEntityForm:saveNewButton")
    private WebElement saveNewButton;

    @FindBy(id = "detailsEntityForm:showEditFormButton")
    private WebElement showEditFormButton;

    @FindBy(id = "editEntityForm:cancelEdit")
    private WebElement cancelEditButton;

    @FindBy(id = "editEntityForm:firstNameEdit")
    private WebElement editSurnameInput;

    @FindBy(id = "editEntityForm:lastNameEdit")
    private WebElement editLastnameInput;

    @FindBy(id = "editEntityForm:saveEditButton")
    private WebElement saveEditButton;

    @FindBy(id = "detailsEntityForm:deleteSelectedButton")
    private WebElement showDeleteFormButton;

    @FindBy(id="deleteEntityForm:cancelDelete")
    private WebElement canceDeleteButton;

    @FindBy(id = "deleteEntityForm:saveDeleteButton")
    private WebElement saveDeleteButton;

    @FindBy(id = "findEntityForm:searchButton")
    private WebElement searchButton;

    @FindBy(id = "findEntityForm:clearSearchButton")
    private WebElement clearSearchButton;


    @FindBy(id = "entityDataTableForm:entityDataTable:0:showDetailsFormButton")
    private WebElement showDetailsFormButton0;

    @FindBy(id = "entityDataTableForm:entityDataTable:1:showDetailsFormButton")
    private WebElement showDetailsFormButton1;

    @FindBy(id = "entityDataTableForm:entityDataTable:2:showDetailsFormButton")
    private WebElement showDetailsFormButton2;

    @FindBy(id = "entityDataTableForm:entityDataTable:3:showDetailsFormButton")
    private WebElement showDetailsFormButton3;

    @FindBy(id = "entityDataTableForm:entityDataTable:4:showDetailsFormButton")
    private WebElement showDetailsFormButton4;

    @FindBy(id="detailsEntityForm:cancelDetails")
    private WebElement cancelDetailsButton;

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

    public VeterinarianPage clickAddNewEntityButton() {
        Graphene.guardHttp(showNewFormButton).click();
        return this;
    }

    public VeterinarianPage clickCancelNewEntityButton() {
        Graphene.guardHttp(cancelNewButton).click();
        return this;
    }

    public VeterinarianPage addNewEntity(String surname, String lastname) {
        newSurnameInput.sendKeys(surname);
        newLastnameInput.sendKeys(lastname);
        Graphene.guardHttp(saveNewButton).click();
        return this;
    }

    public VeterinarianPage clickShowEditForm() {
        Graphene.guardHttp(showEditFormButton).click();
        return this;
    }

    public VeterinarianPage clickCancelEditButton() {
        Graphene.guardHttp(cancelEditButton).click();
        return this;
    }

    public VeterinarianPage editNameAddString() {
        String surname= editSurnameInput.getText();
        String lastname= editLastnameInput.getText();
        surname += " TEST";
        lastname += " TEST";
        editSurnameInput.sendKeys(surname);
        editLastnameInput.sendKeys(lastname);
        Graphene.guardHttp(saveEditButton).click();
        return this;
    }

    public VeterinarianPage clickShowDeleteForm() {
        Graphene.guardHttp(showDeleteFormButton).click();
        return this;
    }

    public VeterinarianPage clickCancelDeleteButton() {
        Graphene.guardHttp(canceDeleteButton).click();
        return this;
    }

    public VeterinarianPage clickSaveDeleteButton() {
        Graphene.guardHttp(saveDeleteButton).click();
        return this;
    }

    public VeterinarianPage clickSearchButton() {
        Graphene.guardHttp(searchButton).click();
        return this;
    }

    public VeterinarianPage clickClearSearchButton() {
        Graphene.guardHttp(clearSearchButton).click();
        return this;
    }

    public VeterinarianPage clickShowDetailsFormButton0() {
        Graphene.guardHttp(showDetailsFormButton0).click();
        return this;
    }

    public VeterinarianPage clickShowDetailsFormButton1() {
        Graphene.guardHttp(showDetailsFormButton1).click();
        return this;
    }

    public VeterinarianPage clickShowDetailsFormButton2() {
        Graphene.guardHttp(showDetailsFormButton2).click();
        return this;
    }

    public VeterinarianPage clickShowDetailsFormButton3() {
        Graphene.guardHttp(showDetailsFormButton3).click();
        return this;
    }

    public VeterinarianPage clickShowDetailsFormButton4() {
        Graphene.guardHttp(showDetailsFormButton4).click();
        return this;
    }

    public VeterinarianPage clickCancelDetailsButton() {
        Graphene.guardHttp(cancelDetailsButton).click();
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

    public VeterinarianPage() {
    }

    public VeterinarianPage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public VeterinarianPage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public VeterinarianPage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }
}
