package com.automationexercices.Validations;

import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

// Soft assertion
public  class Validation extends BaseAssertion {
private static SoftAssert softAssert = new SoftAssert();
private static boolean used = false; //flag to track usage
    public Validation()
    {
        super();
    }
    public Validation(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        used = true; // Mark that an assertion was made
        softAssert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        used = true; // Mark that an assertion was made
        softAssert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(String actual, String expected, String message) {

    }

    @Override
    public void assertEquals(Object actual, Object expected, String message) {
        used = true; // Mark that an assertion was made
        softAssert.assertEquals(actual, expected, message);
    }

    public static void assertAll(ITestResult result)
    {
        if(!used) return; // If no assertions were made , do nothing
        try
        {
            softAssert.assertAll();
        }
        catch (AssertionError e)
        {
            LogsManager.error("Assertion failed:" , e.getMessage());
            result.setStatus(ITestResult.FAILURE); // Mark the test as failed
            result.setThrowable(e); // Attach the assertion error to the test result
        }
        finally {
            softAssert = new SoftAssert(); //Reset the software assert instance
        }
    }
}
