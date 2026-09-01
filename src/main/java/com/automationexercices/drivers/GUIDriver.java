package com.automationexercices.drivers;

import com.automationexercices.Validations.Validation;
import com.automationexercices.Validations.Verification;
import com.automationexercices.utils.Actions.AlertActions;
import com.automationexercices.utils.Actions.BrowserActions;
import com.automationexercices.utils.Actions.ElementActions;
import com.automationexercices.utils.Actions.FrameActions;
import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {

    private final  String browser = PropertyReader.getProperty("browserType");
    private  ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public GUIDriver()
    {
        LogsManager.info("Initializing driver for browser: " + browser);
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Starting driver for browser;" + browserType);
        AbstractDriver abstractDriver = browserType.getDriverFactory();
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }
    public ElementActions element()
    {
        return new ElementActions(get());
    }
    public BrowserActions browser()
    {
        return new BrowserActions(get());
    }
    public FrameActions frame()
    {
        return new FrameActions(get());
    }
    public AlertActions alert()
    {
        return new AlertActions(get());
    }
    //soft assertion
    public Validation validation()
    {
        return new Validation(get());
    }
    //hard assertion
    public Verification verification()
    {
        return new Verification(get());
    }


    public WebDriver get()
    {
        return driverThreadLocal.get();
    }


    public void quitDriver()
    {
        driverThreadLocal.get().quit();
    }



}
