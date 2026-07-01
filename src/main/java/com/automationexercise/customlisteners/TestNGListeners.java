package com.automationexercise.customlisteners;

import com.automationexercise.FileUtils;
import com.automationexercise.drivers.WebDriverProvider;
import com.automationexercise.media.ScreenrecordManager;
import com.automationexercise.media.ScreenshotManager;
import com.automationexercise.report.AllureAttachmentManager;
import com.automationexercise.report.AllureConstants;
import com.automationexercise.report.AllureEnvironmentManager;
import com.automationexercise.report.AllureReportGenerator;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.validations.Validation;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners implements IInvokedMethodListener, IExecutionListener, ITestListener{
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {

            ScreenrecordManager.startRecording();
            LogsManager.info("Test Case " + testResult.getName() + " started");
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = null;
        if (method.isTestMethod())
        {
            ScreenrecordManager.stopRecording(testResult.getName());
            Validation.assertAll(testResult);
            if (testResult.getInstance() instanceof WebDriverProvider provider)
                driver = provider.getWebDriver(); //initialize driver from WebDriverProvider
            switch (testResult.getStatus()){
                case ITestResult.SUCCESS -> ScreenshotManager.takeFullPageScreenshot(driver,"passed-" + testResult.getName());
                case ITestResult.FAILURE -> ScreenshotManager.takeFullPageScreenshot(driver,"failed-" + testResult.getName());
                case ITestResult.SKIP -> ScreenshotManager.takeFullPageScreenshot(driver,"skipped-" + testResult.getName());
            }

            AllureAttachmentManager.attachLogs();
            AllureAttachmentManager.attachRecords(testResult.getName());
        }


    }


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

public void onExecutionFinish(){
    AllureReportGenerator.copyHistory();
    AllureReportGenerator.generateReports(false);
    AllureReportGenerator.generateReports(true);
    AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
    LogsManager.info("Test Execution Finished");
}


public void onTestSuccess(ITestResult result) {
    LogsManager.info("Test Case " + result.getName() + " passed");
}

public void onTestFailure(ITestResult result) {
    LogsManager.info("Test Case " + result.getName() + " failed");
}

public void onTestSkipped(ITestResult result) {
    LogsManager.info("Test Case " + result.getName() + " skipped");
}
// cleaning and creating dirs (logs, screenshots, recordings,allure-results)
private void cleanTestOutputDirectories() {
    // Implement logic to clean test output directories
    FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
    FileUtils.cleanDirectory(new File(ScreenshotManager.SCREENSHOTS_PATH));
    FileUtils.cleanDirectory(new File(ScreenrecordManager.RECORDINGS_PATH));
    FileUtils.cleanDirectory(new File("src/test/resources/downloads/"));
    FileUtils.cleanDirectory(new File(LogsManager.LOGS_PATH +"logs.log"));
}

private void createTestOutputDirectories() {
    // Implement logic to create test output directories
    FileUtils.createDirectory(ScreenshotManager.SCREENSHOTS_PATH);
    FileUtils.createDirectory(ScreenrecordManager.RECORDINGS_PATH);
    FileUtils.createDirectory("src/test/resources/downloads/");

}

}