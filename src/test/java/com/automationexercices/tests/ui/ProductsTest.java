package com.automationexercices.tests.ui;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.drivers.UITest;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.pages.components.ProductsPage;
import com.automationexercices.tests.BaseTest;
import com.automationexercices.utils.dataReader.JsonReader;
import com.automationexercices.utils.logs.TimeManager;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI Products Management")
@Story("Products Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ahmed")
@UITest
public class ProductsTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimestamp();


    @Test
    @Description("Search for a product and validate its details")
    public void searchForProductsWithOutLogin()
    {
        new ProductsPage(driver).navigate()
                .searchForProduct(testData.getJsonData("searchedProduct.name"));
    }
    @Test
    @Description("Add a product to the cart window with out login in")
    public void addProductToCartWithOutLoginIn()
    {
        new ProductsPage(driver).navigate()
                .clickAddToCart(testData.getJsonData("product.name"))
                .validateItemAddedLabel(
                        testData.getJsonData("messages.cartAdded")
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
