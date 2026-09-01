package com.automationexercices.tests.ui;

import com.automationexercices.apis.UserManagementAPI;
import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.drivers.UITest;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.pages.components.signUpLoginPage;
import com.automationexercices.pages.components.signupPage;
import com.automationexercices.tests.BaseTest;
import com.automationexercices.utils.dataReader.JsonReader;
import com.automationexercices.utils.logs.TimeManager;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("User Registration")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ahmed")
@UITest
public class RegisterTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimestamp();

    //Tests
    @Test
    @Description("Verify User can sign up with valid data")
    public void validSignUpTC()
    {
        new signUpLoginPage(driver).navigate()
                .enterSignUpName(testData.getJsonData("name"))
                .enterSignUpEmail(testData.getJsonData("email")+ TimeManager.getSimpleTimestamp()+"@gmail.com")
                .clickOnSignUpButton();
                new signupPage(driver)

                .fillRegisterationForm(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("password"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipCode"),
                        testData.getJsonData("mobileNumber")
                )
                .clickCreateAccountButton()
                .verifyAccountCreated();

        new UserManagementAPI().deleteUserAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }

    @Test
    @Description("Verify User can't sign up with in valid data")
    public void verifyErrorMessageWhenAccountCreatedBefore() {
        //precondition > create a user account
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipCode"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyUserCreatedSuccessfully();
        new signUpLoginPage(driver).navigate()
                .enterSignUpName(testData.getJsonData("name"))
                .enterSignUpEmail(testData.getJsonData("email")+ timestamp + "@gmail.com")
                .clickOnSignUpButton()
                .verifyRegisterErrorMsg(testData.getJsonData("messages.error"));

        new UserManagementAPI().deleteUserAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }



    //Configurations

    @BeforeMethod
    public void preCondition()
    {
        testData = new JsonReader("register-data");
    }

    @BeforeMethod
    public void setUp()
    {
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        driver.browser().closeExtensionTab();
    }
    @AfterMethod
    public void tearDown()
    {
        driver.quitDriver();
    }
}
