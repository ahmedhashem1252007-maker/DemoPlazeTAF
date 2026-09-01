package com.automationexercices.tests.ui;

import com.automationexercices.apis.UserManagementAPI;
import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.drivers.UITest;
import com.automationexercices.pages.components.CartPage;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.pages.components.ProductsPage;
import com.automationexercices.pages.components.signUpLoginPage;
import com.automationexercices.tests.BaseTest;
import com.automationexercices.utils.dataReader.JsonReader;
import com.automationexercices.utils.logs.TimeManager;
import io.qameta.allure.*;
import org.testng.annotations.*;
@Epic("Checkout Management")
@Feature("UI Checkout Management")
@Story("Checkout Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ahmed")
@UITest
public class CheckOutTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimestamp();
    @Test
    public void registerNewAccount() {
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
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyUserCreatedSuccessfully();
    }

    @Test(dependsOnMethods = "registerNewAccount")
    public void loginToAccount() {
        new signUpLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBar
                .verifyUserLabel(testData.getJsonData("name"));

    }

    @Test(dependsOnMethods = {"loginToAccount", "registerNewAccount"})
    public void addProductToCart() {
        new ProductsPage(driver)
                .navigate()
                .clickAddToCart(testData.getJsonData("Product.name"))
                .validateItemAddedLabel(testData.getJsonData("messages.cartAdded"))
                .clickOnViewCart()
                .verifyProductDetailsOnCart(
                        testData.getJsonData("Product.name"),
                        testData.getJsonData("Product.price"),
                        testData.getJsonData("Product.quantity"),
                        testData.getJsonData("Product.total")
                );
    }

    @Test(dependsOnMethods = {"addProductToCart", "loginToAccount", "registerNewAccount"})
    public void checkout() {
        new CartPage(driver)
                .clickProceedToCheckout()
                .verifyDeliveryAddress(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyBillingAddress(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber")
                );
    }
    @Test(dependsOnMethods = {"checkout","addProductToCart", "loginToAccount", "registerNewAccount"})
    public void deleteAccountAsPostCondition() {
        new UserManagementAPI()
                .deleteUserAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password")
                )
                .verifyUserDeletedSuccessfully();
    }

    //Configurations
    @BeforeClass
    protected void setUp() {
        testData = new JsonReader("checkout-data");
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        driver.browser().closeExtensionTab();
    }

    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }

}
