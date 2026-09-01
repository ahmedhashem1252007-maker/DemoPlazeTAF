package com.automationexercices.pages.components;

import com.automationexercices.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class signupPage {
    private final GUIDriver driver;
    public signupPage(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By name = By.id("name");
    private final By email = By.id("email");
    private final By password = By.id("password");
    private final By day = By.id("days");
    private final By month = By.id("months");
    private final By year = By.id("years");
    private final By newsletter = By.id("newsletter");
    private final By specialOffers = By.id("optin");
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By address1 = By.id("address1");
    private final By address2 = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipcode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("[data-qa='create-account']");
    private final By accountCreatedLabel = By.cssSelector("h2 > b");
    private final By continueButton = By.cssSelector("[data-qa='continue-button']");



    //action
    @Step("Choose Title {title} in sign up form") // Mr || Mrs
    private signupPage chooseTitle (String title) {
        // Implementation for choosing title
     By titleLocator = By.cssSelector("input[name='title'][value='" + title + "']");
        driver.element().click(titleLocator);
        return this;
    }
    @Step("Fill Register Form")
    public signupPage fillRegisterationForm (String title,
                                             String password,
                                             String day,
                                             String month,
                                             String year,
                                             String firstName,
                                             String lastName,
                                             String company,
                                             String address1,
                                             String address2,
                                             String country,
                                             String state,
                                             String city,
                                             String zipcode,
                                             String mobileNumber)
    {
        chooseTitle(title);
        driver.element().type(this.password, password);
        driver.element().selectFromDropDown(this.day, day);
        driver.element().selectFromDropDown(this.month, month);
        driver.element().selectFromDropDown(this.year, year);
        driver.element().click(newsletter);
        driver.element().click(specialOffers);
        driver.element().type(this.firstName, firstName);
        driver.element().type(this.lastName, lastName);
        driver.element().type(this.company, company);
        driver.element().type(this.address1, address1);
        driver.element().type(this.address2, address2);
        driver.element().selectFromDropDown(this.country, country);
        driver.element().type(this.state, state);
        driver.element().type(this.city, city);
        driver.element().type(this.zipcode, zipcode);
        driver.element().type(this.mobileNumber, mobileNumber);
        return this;
    }
    @Step("Click Create Account Button")
    public signupPage clickCreateAccountButton ()
    {
        driver.element().click(createAccountButton);
        return this;
    }
    @Step("Click On Continue Button")
    public NavigationBarComponent ClickContinueButton ()
    {
        driver.element().click(continueButton);
        return new NavigationBarComponent(driver);
    }

    //validations
    @Step("Verify Account Created")
    public signupPage verifyAccountCreated ()
    {
        driver.verification().isElemetVisible(accountCreatedLabel);
        return this;
    }


}
