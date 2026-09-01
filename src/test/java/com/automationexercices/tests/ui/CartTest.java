package com.automationexercices.tests.ui;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.drivers.UITest;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.pages.components.ProductsPage;
import com.automationexercices.tests.BaseTest;
import com.automationexercices.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {


    @Epic("Cart Exercise")
    @Feature("UI Cart Management")
    @Story("Cart Management")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ahmed")
    @Test
    @UITest
    public void verifyProductDetailsOnCartWithOutLogin() {
        new ProductsPage(driver)
                .navigate()
                .clickAddToCart(testData.getJsonData("product.name"))
                .validateItemAddedLabel(testData.getJsonData("messages.cartAdded"))
                .clickOnViewCart()
                .verifyProductDetailsOnCart(
                        testData.getJsonData("product.name"),
                        testData.getJsonData("product.price"),
                        testData.getJsonData("product.quantity"),
                        testData.getJsonData("product.total")
                );

    }




    //Configurations
    @BeforeMethod
    protected void preCondition()
    {
        testData = new JsonReader("products-data");
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
