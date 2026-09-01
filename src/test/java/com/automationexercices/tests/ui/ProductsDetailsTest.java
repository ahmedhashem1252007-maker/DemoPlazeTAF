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

public class ProductsDetailsTest extends BaseTest {

    @Epic("Automation Exercise")
    @Feature("UI Products Management")
    @Story("Products Management")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ahmed")
    @Test
    @UITest
    public void verifyProductDetailsTC()
    {
        new ProductsPage(driver)
                .navigate()
                .ClickOnViewProduct(testData.getJsonData("Product.name"))
                .verifyProductDetails(
                        testData.getJsonData("Product.name"),
                        testData.getJsonData("Product.price")
                );
    }

    @Test
    public void verifyReviewMessageTC()
    {
        new ProductsPage(driver)
                .navigate()
            .ClickOnViewProduct(testData.getJsonData("Product.name"))
                .addReview(
                        testData.getJsonData("review.name"),
                        testData.getJsonData("review.email"),
                        testData.getJsonData("review.review")
                )
                .verifyReviewMsg(testData.getJsonData("messages.reviewMsg"));
    }




    //Configurations
    @BeforeMethod
    protected void preCondition()
    {
        testData = new JsonReader("products-details");
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
