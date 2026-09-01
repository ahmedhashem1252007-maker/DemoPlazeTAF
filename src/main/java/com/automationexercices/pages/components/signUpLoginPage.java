package com.automationexercices.pages.components;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class signUpLoginPage {
    public NavigationBarComponent navigationBar;
    private final String signUpLoginEndPoint = "/login";
    private GUIDriver driver;
    public signUpLoginPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    //locators
    private final By loginEmail = By.cssSelector("[data-qa=\"login-email\"]");
    private final By loginPassword = By.cssSelector("[data-qa=\"login-password\"]");
    private final By loginButton = By.cssSelector("[data-qa=\"login-button\"]");
    private final By signUpName = By.cssSelector("[data-qa=\"signup-name\"]");
    private final By signUpEmail = By.cssSelector("[data-qa=\"signup-email\"]");
    private final By signUpButton = By.cssSelector("[data-qa=\"signup-button\"]");
    private final By getSignUpLabel = By.cssSelector(".signup-form > h2");
    private final By loginError = By.cssSelector(".login-form p");
    private final By registerError = By.cssSelector(".signup-form p");


    //actions
    @Step("Navigate to Register/Login Page")
    public signUpLoginPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseURlWeb") + signUpLoginEndPoint);
        return this;
    }

    @Step("Enter name {email} in login field")
    public signUpLoginPage enterLoginEmail(String email) {
        driver.element().type(loginEmail, email);
        return this;
    }

    @Step("Enter password {password} in login field")
    public signUpLoginPage enterLoginPassword(String password) {
        driver.element().type(loginPassword, password);
        return this;
    }

    @Step("Click login button")
    public signUpLoginPage clickLoginButton() {
        driver.element().click(loginButton);
        return this;
    }
    @Step("Enter name {name} in sign up field")
    public signUpLoginPage enterSignUpName(String name) {
        driver.element().type(signUpName, name);
        return this;
    }
    @Step("Enter email {email} in sign up field")
    public signUpLoginPage enterSignUpEmail(String email) {
        driver.element().type(signUpEmail, email);
        return this;
    }
    @Step("Click on Sign Up Button")
    public signUpLoginPage clickOnSignUpButton() {
        driver.element().click(signUpButton);
        return new signUpLoginPage(driver);
    }

    //validations
    @Step("Verify new user sign up label is displayed")
    public signUpLoginPage verifySignUpLabelIsDisplayed() {
        driver.verification().isElemetVisible(getSignUpLabel);
        return this;
    }

    @Step("Verify Login error msg{errorExpected}")
    public signUpLoginPage verifyLoginErrorMsg(String errorExpected) {
    String errorActual = driver.element().getText(loginError);
    driver.verification().Equals(errorActual, errorExpected, "Login error msg is not as expected");
    return this;
    }
    @Step("Verify register error msg{errorExpected}")
    public signUpLoginPage verifyRegisterErrorMsg(String errorExpected) {
        String errorActual = driver.element().getText(registerError);
        driver.verification().Equals(errorActual, errorExpected, "Register error msg is not as expected");
        return this;
    }


}

