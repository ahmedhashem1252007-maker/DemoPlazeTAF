package com.automationexercices.Validations;

import com.automationexercices.utils.Actions.ElementActions;
import com.automationexercices.utils.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BaseAssertion {
    protected  WebDriver webDriver;
    protected  WaitManager waitManager;
    protected ElementActions elementActions;
    protected BaseAssertion()
    {

    }

    public BaseAssertion(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.waitManager = new WaitManager(webDriver);
        this.elementActions = new ElementActions(webDriver);
    }

    protected abstract void assertTrue(boolean condition , String message);
    protected abstract void assertFalse(boolean condition , String message);
    protected abstract void assertEquals(String actual , String expected , String message);

    public BaseAssertion Equals(String actual , String expected , String message)
    {
        assertEquals(actual , expected , message);
        return this;
    }

    public void isElemetVisible(By locator) {

        boolean flag = waitManager.fluentWait().until(d ->
        {
            try
            {
                d.findElement(locator).isDisplayed();
                return true;
            }
            catch (Exception e)
            {
                return false;
            }

        });
    assertTrue( flag , "Element is not visible: " + locator );
    }

    //verify page url
    public void assertPageUrl(String expectedUrl)
    {
        String actualUrl = webDriver.getCurrentUrl();
        assertEquals(actualUrl, expectedUrl, "Url is not equal to " + expectedUrl + "Actal url is " + actualUrl);
    }
    //verify page title
    public void assertPageTitle(String expectedTitle)
    {
        String actualTitle = webDriver.getTitle();
        assertEquals(actualTitle, expectedTitle, "Title is not equal to " + expectedTitle + "Actal title is " + actualTitle);
    }

    public abstract void assertEquals(Object actual, Object expected, String message);


}
