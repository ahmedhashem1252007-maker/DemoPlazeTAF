package com.automationexercices.drivers;

import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class EdgeFactory extends AbstractDriver{


    private EdgeOptions getOptions()
    {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addExtensions(haramBlurExtension);
        switch (PropertyReader.getProperty("executionType"))
        {
            case "LocalHeadless" -> options.addArguments("--headless=new");
            case "Remote" ->
            {
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-extensions");
                options.addArguments("--headless=new");
            }
        }
        return options;
    }

    @Override
    public WebDriver createDriver()
    {

        if (PropertyReader.getProperty("executionType").equals("Local") ||
                PropertyReader.getProperty("executionType").equals("LocalHeadless"))
        {
            return new EdgeDriver(getOptions());
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

