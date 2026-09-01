package com.automationexercices.tests.ui;

import com.automationexercices.apis.UserManagementAPI;
import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.drivers.UITest;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.pages.components.signUpLoginPage;
import com.automationexercices.tests.BaseTest;
import com.automationexercices.utils.dataReader.JsonReader;
import com.automationexercices.utils.logs.TimeManager;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ahmed")
@UITest
public class LoginTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimestamp();

    @Test
    @Description("Verify User can login with valid credentials")
    public void validLoginTC()
    {
        new UserManagementAPI().createRegisterUserAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email") + timestamp + "@gmail.com",
                testData.getJsonData("password"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new signUpLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBar
                .verifyUserLabel(testData.getJsonData("name"));

        new UserManagementAPI().deleteUserAccount(
                testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @Test
    @Description("Verify error message when logging in with invalid email")
    public void inValidLoginUsingInValidEmailTC()
    {
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new signUpLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email")  + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .verifyLoginErrorMsg(testData.getJsonData("messages.error"));
        new UserManagementAPI().deleteUserAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @Test
    @Description("Verify error message when logging in with invalid password")
    public void inValidLoginUsingInValidPassWordTC()
    {
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new signUpLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp  + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password") + timestamp)
                .clickLoginButton()
                .verifyLoginErrorMsg(testData.getJsonData("messages.error"));
        new UserManagementAPI().deleteUserAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }



    //Configurations
    @BeforeMethod
    public void preCondition()
            {
                testData = new JsonReader("login-data");
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
