package com.automationexercices.utils.Actions;

import com.automationexercices.utils.WaitManager;
import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;

public class AlertActions {
    private final WebDriver driver;
    private final WaitManager waitManager;
    public AlertActions(WebDriver driver)
    {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //Accept the alert
    public void acceptAlert()
    {
        waitManager.fluentWait().until(d ->
        {
            try
            {
                d.switchTo().alert().accept();
                return true;
            }catch (Exception e)
            {
                LogsManager.error("Field to accept alert:" , e.getMessage());
                return false;
            }
        });
    }
    //Accept the alert
    public void dismissAlert()
    {
        waitManager.fluentWait().until(d ->
        {
            try
            {
                d.switchTo().alert().dismiss();
                return true;
            }catch (Exception e)
            {
                LogsManager.error("Field to dismiss alert:" , e.getMessage());
                return false;
            }
        });
    }
    //Accept the alert
    public String getTextAlert()
    {
        return (String) waitManager.fluentWait().until(d ->
        {
            try
            {
                String text = d.switchTo().alert().getText();
                return  !text.isEmpty() ? text : null;
            }
            catch (Exception e)
            {
                LogsManager.error("Field to get text from alert:" , e.getMessage());
                return false;
            }
        });
    }
    //Set Allert Text
    public void setTextAlert(String text)
    {
        waitManager.fluentWait().until(d ->
        {
            try
            {
                d.switchTo().alert().sendKeys(text);
                return true;
            }
            catch (Exception e)
            {
                LogsManager.error("Field to set text to alert:" , e.getMessage());
                return false;
            }
        });
    }
}
