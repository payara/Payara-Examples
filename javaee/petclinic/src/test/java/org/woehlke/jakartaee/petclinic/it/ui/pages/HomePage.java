/*
All the code that follow is
Copyright (c) 2022, Thomas Woehlke. All Rights Reserved.
Not for reuse without permission.
*/

package org.woehlke.jakartaee.petclinic.it.ui.pages;

import java.util.Map;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

@Location("home.jsf")
public class HomePage {

    private Map<String, String> data;
    @Drone
    private WebDriver driver;
    private int timeout = 15;

    @FindBy(id = "listEntityHeaderId")
    private WebElement pageTitle;

    @FindBy(id = "j_idt14:j_idt23_focus")
    @CacheLookup
    private WebElement englisch1;

    @FindBy(id = "j_idt14:j_idt23_input")
    @CacheLookup
    private WebElement englisch2;

    @FindBy(id = "findOwnersForm:inputTextSearchterm")
    @CacheLookup
    private WebElement englisch3;

    @FindBy(css = "a[href='home.jsf']")
    @CacheLookup
    private WebElement home;

    @FindBy(css = "#j_idt14:j_idt15 ul.ui-menu-list.ui-helper-reset li:nth-of-type(6) a.ui-menuitem-link.ui-corner-all")
    @CacheLookup
    private WebElement information1;

    @FindBy(css = "a.ui-link.ui-widget")
    @CacheLookup
    private WebElement information2;

    @FindBy(css = "a[href='owner.jsf']")
    @CacheLookup
    private WebElement owners;

    private final String pageLoadedText = "add some Pet Types like dog,cat,mouse,";

    private final String pageUrl = "/petclinic/home.jsf";

    @FindBy(css = "a[href='petType.jsf']")
    @CacheLookup
    private WebElement petTypes;

    @FindBy(css = "a[href='specialty.jsf']")
    @CacheLookup
    private WebElement specialties;

    @FindBy(id = "j_idt14:j_idt25")
    @CacheLookup
    private WebElement uibutton1;

    @FindBy(id = "findOwnersForm:searchButton")
    @CacheLookup
    private WebElement uibutton2;

    @FindBy(id = "findOwnersForm:clearSearchButton")
    @CacheLookup
    private WebElement uibutton3;

    @FindBy(css = "a[href='veterinarian.jsf']")
    @CacheLookup
    private WebElement veterinarians;

    public HomePage() {
    }

    public HomePage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public HomePage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public HomePage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }

    /**
     * Click on Home Link.
     *
     * @return the HomePage class instance.
     */
    public HomePage clickHomeLink() {
        home.click();
        return this;
    }

    /**
     * Click on Information Link.
     *
     * @return the HomePage class instance.
     */
    public HomePage clickInformation1Link() {
        information1.click();
        return this;
    }

    /**
     * Click on Information Link.
     *
     * @return the HomePage class instance.
     */
    public HomePage clickInformation2Link() {
        information2.click();
        return this;
    }

    /**
     * Click on Owners Link.
     *
     * @return the HomePage class instance.
     */
    public HomePage clickOwnersLink() {
        owners.click();
        return this;
    }

    /**
     * Click on Pet Types Link.
     *
     * @return the HomePage class instance.
     */
    public HomePage clickPetTypesLink() {
        petTypes.click();
        return this;
    }

    /**
     * Click on Specialties Link.
     *
     * @return the HomePage class instance.
     */
    public HomePage clickSpecialtiesLink() {
        specialties.click();
        return this;
    }

    /**
     * Click on Uibutton Button.
     *
     * @return the HomePage class instance.
     */
    public HomePage clickUibutton1Button() {
        uibutton1.click();
        return this;
    }

    /**
     * Click on Uibutton Button.
     *
     * @return the HomePage class instance.
     */
    public HomePage clickUibutton2Button() {
        uibutton2.click();
        return this;
    }

    /**
     * Click on Uibutton Button.
     *
     * @return the HomePage class instance.
     */
    public HomePage clickUibutton3Button() {
        uibutton3.click();
        return this;
    }

    /**
     * Click on Veterinarians Link.
     *
     * @return the HomePage class instance.
     */
    public HomePage clickVeterinariansLink() {
        veterinarians.click();
        return this;
    }

    /**
     * Fill every fields in the page.
     *
     * @return the HomePage class instance.
     */
    public HomePage fill() {
        setEnglisch1DropDownListField();
        setEnglisch2DropDownListField();
        setEnglisch3TextField();
        return this;
    }

    /**
     * Fill every fields in the page and submit it to target page.
     *
     * @return the HomePage class instance.
     */
    public HomePage fillAndSubmit() {
        fill();
        return submit();
    }

    /**
     * Set default value to Englisch Drop Down List field.
     *
     * @return the HomePage class instance.
     */
    public HomePage setEnglisch1DropDownListField() {
        return setEnglisch1DropDownListField(data.get("ENGLISCH_1"));
    }

    /**
     * Set value to Englisch Drop Down List field.
     *
     * @return the HomePage class instance.
     */
    public HomePage setEnglisch1DropDownListField(String englisch1Value) {
        englisch1.sendKeys(englisch1Value);
        return this;
    }

    /**
     * Set default value to Englisch Drop Down List field.
     *
     * @return the HomePage class instance.
     */
    public HomePage setEnglisch2DropDownListField() {
        return setEnglisch2DropDownListField(data.get("ENGLISCH_2"));
    }

    /**
     * Set value to Englisch Drop Down List field.
     *
     * @return the HomePage class instance.
     */
    public HomePage setEnglisch2DropDownListField(String englisch2Value) {
        new Select(englisch2).selectByVisibleText(englisch2Value);
        return this;
    }

    /**
     * Set default value to Englisch Text field.
     *
     * @return the HomePage class instance.
     */
    public HomePage setEnglisch3TextField() {
        return setEnglisch3TextField(data.get("ENGLISCH_3"));
    }

    /**
     * Set value to Englisch Text field.
     *
     * @return the HomePage class instance.
     */
    public HomePage setEnglisch3TextField(String englisch3Value) {
        englisch3.sendKeys(englisch3Value);
        return this;
    }

    /**
     * Submit the form to target page.
     *
     * @return the HomePage class instance.
     */
    public HomePage submit() {
        // TODO
        // clickUibuttonButton();
        return this;
    }

    /**
     * Unset default value from Englisch Drop Down List field.
     *
     * @return the HomePage class instance.
     */
    public HomePage unsetEnglisch2DropDownListField() {
        return unsetEnglisch2DropDownListField(data.get("ENGLISCH_2"));
    }

    /**
     * Unset value from Englisch Drop Down List field.
     *
     * @return the HomePage class instance.
     */
    public HomePage unsetEnglisch2DropDownListField(String englisch2Value) {
        new Select(englisch2).deselectByVisibleText(englisch2Value);
        return this;
    }

    /**
     * Verify that the page loaded completely.
     *
     * @return the HomePage class instance.
     */
    public HomePage verifyPageLoaded() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getPageSource().contains(pageLoadedText);
            }
        });
        return this;
    }

    /**
     * Verify that current page URL matches the expected URL.
     *
     * @return the HomePage class instance.
     */
    public HomePage verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }

    public void assertPageIsLoaded() {
        assertThat(pageTitle.isDisplayed());
        assertThat( "Specialties".compareTo(pageTitle.getText())==0 );
    }

    public void fullscreen(){
        driver.manage().window().fullscreen();
    }
}
