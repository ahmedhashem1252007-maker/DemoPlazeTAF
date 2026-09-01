package com.automationexercices.utils;

import com.automationexercices.utils.dataReader.PropertyReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;

public class WaitManager {
    static WebDriver driver;

    public WaitManager(WebDriver driver) {
        this.driver = driver;
    }

    public static FluentWait<WebDriver> fluentWait()
    {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Long.parseLong(PropertyReader.getProperty("DEFAULT_WAIT"))))
                .pollingEvery(Duration.ofMillis(100))
                .ignoreAll(getExeptions());
    }

    private static ArrayList <Class<? extends Exception>> getExeptions()
    {
        ArrayList <Class<? extends Exception>> exeptions = new ArrayList<>();
        exeptions.add(NoSuchElementException.class);
        exeptions.add(StaleElementReferenceException.class);
        exeptions.add(ElementNotInteractableException.class);
        exeptions.add(ElementClickInterceptedException.class);

        return exeptions;
    }
}
