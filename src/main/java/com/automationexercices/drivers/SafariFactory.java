package com.automationexercices.drivers;

import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.URI;

public class SafariFactory extends AbstractDriver {

    public SafariOptions getOptions(){
        SafariOptions options = new SafariOptions();
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }


    @Override
    public WebDriver createDriver()
    {
        if (PropertyReader.getProperty("executionType").equals("Local") ||
                PropertyReader.getProperty("executionType").equals("LocalHeadless"))
        {
            return new SafariDriver(getOptions());
        }
        else if (PropertyReader.getProperty("executionType").equals("Remote"))
            try {
                return new RemoteWebDriver(
                        new URI("https://" + remoteHost + ":" + remotePort + "/wd/hub").toURL(),getOptions()
                );
            }
            catch (Exception e)
            {
                LogsManager.error("Error creating RemoteWebDriver: " + e.getMessage());
                throw new RuntimeException("Error creating RemoteWebDriver: " + e.getMessage(), e);
            }
        else
        {
            LogsManager.error("invalid exeption type: " + PropertyReader.getProperty("executionType"));
            throw new IllegalArgumentException("invalid exeption type: " + PropertyReader.getProperty("executionType"));
        }


    }

}
