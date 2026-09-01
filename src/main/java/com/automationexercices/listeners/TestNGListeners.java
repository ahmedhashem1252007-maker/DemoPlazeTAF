package com.automationexercices.listeners;

import com.automationexercices.FileUtils;
import com.automationexercices.drivers.UITest;
import com.automationexercices.drivers.WebDriverProvider;
import com.automationexercices.media.ScreenRecordManager;
import com.automationexercices.media.ScreenShotManager;
import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import com.automationexercices.utils.report.AllureAttachmentManager;
import com.automationexercices.utils.report.AllureConstants;
import com.automationexercices.utils.report.AllureEnvironmentManager;
import com.automationexercices.utils.report.AllureReportGenerator;
import com.automationexercices.Validations.Validation;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners implements IExecutionListener, IInvokedMethodListener, ITestListener {

    public void onExecutionStart() {
        LogsManager.info("Test Execution started");
        cleanTestOutputDirectories();
        LogsManager.info("Directories cleaned");
        createTestOutputDirectories();
        LogsManager.info("Directories created");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info("Allure environment set");
    }

    public void onExecutionFinish() {
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Test Execution Finished");
    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            if (testResult.getInstance() instanceof UITest)
            {
                ScreenRecordManager.startRecording();
            }
            LogsManager.info("Test Case " + testResult.getName() + " started");
        }    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = null;
        if (method.isTestMethod()) {
            if (testResult.getInstance() instanceof UITest) {
                ScreenRecordManager.stopRecording(testResult.getName());
                if (testResult.getInstance() instanceof WebDriverProvider provider) {
                    driver = provider.getWebDriver(); //initialize driver from WebDriverProvider
                }
                switch (testResult.getStatus()) {
                    case ITestResult.SUCCESS -> ScreenShotManager.takeFullPageScreenshot(driver, testResult.getName());
                    case ITestResult.FAILURE -> ScreenShotManager.takeFullPageScreenshot(driver, testResult.getName());
                    case ITestResult.SKIP -> ScreenShotManager.takeFullPageScreenshot(driver, testResult.getName());
                }
                AllureAttachmentManager.attachRecords(testResult.getName());
            }

            Validation.assertAll(testResult);
            AllureAttachmentManager.attachLogs();
        }
    }

    public void onTestSuccess(ITestResult result) {
        LogsManager.info("TestCase: " + result.getName() + "passed");
    }

    public void onTestFailure(ITestResult result) {
        LogsManager.info("TestCase: " + result.getName() + "failed");
    }

    public void onTestSkipped(ITestResult result) {
        LogsManager.info("TestCase: " + result.getName() + "skipped");
    }

    // cleaning and creating dirs (logs, screenshots, recordings, allure-results)
    private void cleanTestOutputDirectories() {
        // Implement logic to clean test output directories
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenShotManager.SCREENSHOTS_PATH));
        FileUtils.cleanDirectory(new File(ScreenRecordManager.RECORDINGS_PATH));
        FileUtils.forceDeleteDirectory(new File(LogsManager.LOGS_PATH + File.separator + "logs.log"));
    }

    private void createTestOutputDirectories() {
        // Implement logic to create test output directories
        FileUtils.createDirectory(ScreenShotManager.SCREENSHOTS_PATH);
        FileUtils.createDirectory(ScreenRecordManager.RECORDINGS_PATH);
    }
}
