package com.automationexercices.pages.components;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    private final GUIDriver driver;

    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }
    //locators
    private final By homeButton = By.xpath("//a [.=' Home']");
    private final By productsButton = By.cssSelector("href=\"/products\"");
    private final By cartButton = By.xpath("li> [href=\"/view_cart\"]");
    private final By loginButton = By.cssSelector("href=\"/login\"");
    private final By testcaseButton = By.cssSelector("li> a[href=\"/test_cases\"]");
    private final By apitestingButton = By.cssSelector("li > a[href=\"/api_list\"]");
    private final By videoButton = By.cssSelector("href=\"https://www.youtube.com/c/AutomationExercise\"");
    private final By contactUsButton = By.cssSelector("href=\"/contact_us\"");
    private final By homePageLabel = By.cssSelector("h1 > span");
    private final By deleteAccountButton = By.cssSelector("href=\"/delete_account\"");
    private final By logoutButton = By.cssSelector("href=\"/logout\"");
    private final By userLabel = By.tagName("b");

    //actions
    @Step("Navigate to the base URL")
    public NavigationBarComponent navigate()
    {
        driver.browser().navigateTo(PropertyReader.getProperty("baseURlWeb"));
        return this;
    }
    @Step("Click on Home Button")
    public NavigationBarComponent clickHomeButton()
    {
        driver.element().click(homeButton);
        return this;
    }
    @Step("Click on Products Button")
    public ProductsPage clickOnProductsButton()
    {
        driver.element().click(productsButton);
        return new ProductsPage(driver);
    }
    @Step("Click on Cart Button")
    public CartPage clickOnCartButton()
    {
        driver.element().click(cartButton);
        return new CartPage(driver);
    }
    @Step("Click on Login Button")
    public signUpLoginPage clickOnLoginButton()
    {
        driver.element().click(loginButton);
        return new signUpLoginPage(driver);
    }
    @Step("Click on Test Case Button")
    public TestCasePage clickOnTestCaseButton()
    {
        driver.element().click(testcaseButton);
        return new TestCasePage(driver);
    }
    @Step("Click on API Testing Button")
    public ApiTestingPage clickOnApiTestingButton()
    {
        driver.element().click(apitestingButton);
        return new ApiTestingPage(driver);
    }
    @Step("Click on Video Button")
    public VideoPage clickOnVideoButton()
    {
        driver.element().click(videoButton);
        return new VideoPage(driver);
    }
    @Step("Click on Contact Us Button")
    public ContactUsPage clickOnContactUsButton()
    {
        driver.element().click(contactUsButton);
        return new ContactUsPage(driver);
    }
    @Step("Click On deleteAccountButton Button")
    public DeleteAccountPage clickOnDeleteAccountButton()
    {
        driver.element().click(deleteAccountButton);
        return new DeleteAccountPage(driver);
    }
    @Step("Click On logoutButton Button")
    public signUpLoginPage clickOnLogoutButton()
    {
        driver.element().click(logoutButton);
        return new signUpLoginPage(driver);
    }

    //validations
    @Step("Validate Home Page Label is displayed")
    public NavigationBarComponent verifyHomePage()
    {
        driver.verification().isElemetVisible(homePageLabel);
        return this;
    }
    @Step("Validate User Label is displayed")
    public NavigationBarComponent verifyUserLabel(String expectedName)
    {
        String actualName = driver.element().getText(userLabel);
        LogsManager.info("Actual User Name: " + actualName);
        driver.verification().Equals(actualName, expectedName , "user label doesn't match expected: " + expectedName + ", Actual: " + actualName);
        return this;
    }


}
