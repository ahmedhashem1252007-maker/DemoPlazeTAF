package com.automationexercices.Validations;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

//Hard Assertion
public  class Verification extends BaseAssertion {
    public Verification()
    {
        super();
    }
    public Verification(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(String actual, String expected, String message) {

    }

    @Override
    public void assertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }
}
