package com.automationexercices.utils.Actions;

import com.automationexercices.utils.WaitManager;
import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameActions {
    private final WebDriver driver;
    private final WaitManager waitManager;
    public FrameActions(WebDriver driver)
    {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //Switch to frame by index
    public void switchToFrameByIndex(int index)
    {
        waitManager.fluentWait().until(d ->
        {
            try
            {
                d.switchTo().frame(index);
                LogsManager.info("Switched to frame by index: " + index);
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        });

        }
        //Switch to frame by name or id
    public void switchToFrameByNameOrId(String nameOrId)
    {
        waitManager.fluentWait().until(d -> {
            try
            {
                d.switchTo().frame(nameOrId);
                LogsManager.info("Switched to frame by name or id: " + nameOrId);
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        });
    }
    //Switch to frame by webelement
    public void switchToFrameByWebElement(By frameLocator)
    {
        waitManager.fluentWait().until(d ->
        {
            try
            {
                d.switchTo().frame(d.findElement(frameLocator));
                LogsManager.info("Switched to frame by webelement: " + frameLocator);
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        });
    }
    //Switch to default content
    public void switchToDefaultContent()
    {
        waitManager.fluentWait().until(d ->
        {
            try
            {
                d.switchTo().defaultContent();
                LogsManager.info("Switched to default content");
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        });
    }

}
