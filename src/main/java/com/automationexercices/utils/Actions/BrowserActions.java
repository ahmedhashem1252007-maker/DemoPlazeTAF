package com.automationexercices.utils.Actions;

import com.automationexercices.utils.WaitManager;
import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActions {
    private final WebDriver driver;
    public BrowserActions(WebDriver driver)
    {
        this.driver = driver;
    }

    public void maximize()
    {
        driver.manage().window().maximize();
    }
    //get curren URL
    public String getCurrentURL()
    {
        String url = driver.getCurrentUrl();
        LogsManager.info("Current URL: " + url);
        return url;
    }
    //Navigate to specific URL
    public void navigateTo(String url)

    {
        driver.get(url);
        LogsManager.info("Navigated to: " + url);
    }
    // Refresh the current page
    public void refresh()
    {
        driver.navigate().refresh();
    }
    // Close the current page
    public void close()

    {
        driver.close();
    }
    // Open a new window
    public void openNewWindow()
    {
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

    // Close extension tab
    public void closeExtensionTab() {
        String currentWindowHandle = driver.getWindowHandle();
        try {
            WaitManager.fluentWait().until(
                    d -> driver.getWindowHandles().size() > 1 // wait until extension tab is opened
            );
            driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString()).close();
            driver.switchTo().window(currentWindowHandle);
            LogsManager.info("Extension tab closed");

        } catch (org.openqa.selenium.TimeoutException e) {
            LogsManager.info("No extension tab opened, continuing test...");
        }
    }

}
