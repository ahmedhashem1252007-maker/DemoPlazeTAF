package com.automationexercices.media;

import com.automationexercices.utils.logs.LogsManager;
import com.automationexercices.utils.logs.TimeManager;
import com.automationexercices.utils.report.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenShotManager {
    public static final String SCREENSHOTS_PATH = "test-output/screenshots/";

    //take full page screenshot
    public static void takeFullPageScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Capture screenshot using TakesScreenshot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            AllureAttachmentManager.attachScreenshot(screenshotName, screenshotFile.getAbsolutePath());

            LogsManager.info("Capturing Screenshot Succeeded");
        } catch (Exception e) {
            LogsManager.error("Failed to Capture Screenshot " + e.getMessage());
        }
    }
    //take screenshot of a specific element
    public static void takeElementScreenshot(WebDriver driver, By elementSelector) {
        try {
            // Highlight the element if needed (not implemented here)
            // Capture screenshot using TakesScreenshot
            String ariaName = driver.findElement(elementSelector).getAccessibleName();
            File screenshotSrc = driver.findElement(elementSelector).getScreenshotAs(OutputType.FILE);

            // Clean the timestamp to avoid Windows file name errors
            String safeTimestamp = TimeManager.getTimestamp().replace(":", "-").replace(" ", "_");

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + ariaName + "-" + safeTimestamp + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            // TODO: Attach the screenshot to Allure if needed

            LogsManager.info("Capturing Element Screenshot Succeeded");
        } catch (Exception e) {
            LogsManager.error("Failed to Capture Element Screenshot: " + e.getMessage());
        }
    }
}