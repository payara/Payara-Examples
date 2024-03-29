/*
All the code that follow is
Copyright (c) 2022, Thomas Woehlke. All Rights Reserved.
Not for reuse without permission.
*/

package org.woehlke.jakartaee.petclinic.it.ui.pages;
import java.util.Map;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Location("info.jsf")
public class InformationPage {
    private Map<String, String> data;
    @Drone
    private WebDriver driver;
    private int timeout = 15;

    @FindBy(id = "listEntityHeaderId")
    private WebElement pageTitle;


    public void assertPageIsLoaded() {
        assertThat(pageTitle.isDisplayed());
        assertEquals( "Information", pageTitle.getText() );
    }

    public void fullscreen(){
        driver.manage().window().fullscreen();
    }

    public InformationPage() {
    }

    public InformationPage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public InformationPage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public InformationPage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }
}
