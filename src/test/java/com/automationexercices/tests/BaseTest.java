package com.automationexercices.tests;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.drivers.WebDriverProvider;
import com.automationexercices.utils.dataReader.JsonReader;
import org.openqa.selenium.WebDriver;

public class BaseTest implements WebDriverProvider {
    protected GUIDriver driver;
    protected JsonReader testData;




    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }
}
