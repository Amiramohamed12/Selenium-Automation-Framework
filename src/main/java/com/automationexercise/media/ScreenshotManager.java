package com.automationexercise.media;

import com.automationexercise.report.AllureAttachmentManager;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.logs.LogsManager;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenshotManager {

    public static final String SCREENSHOTS_PATH= "test-output/screenshots/";

    //take full page screenshot
    public static void takeFullPageScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Capture screenshot using TakesScreenshot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + TimeManager.getTimeStamp()+ ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);


            AllureAttachmentManager.attachScreenshot(screenshotName,screenshotFile.getAbsolutePath());
            LogsManager.info("Capturing Screenshot Succeeded");
        } catch (Exception e) {
            LogsManager.error("Failed to Capture Screenshot " + e.getMessage());
        }
    }
    //take screenshot for specific element
    public static void takeElementScreenshot(WebDriver driver,By locator) {
        try {
            // Capture screenshot using TakesScreenshot
            File screenshotSrc = driver.findElement(locator).getScreenshotAs(OutputType.FILE);;
            String ariaName=driver.findElement(locator).getAccessibleName();  //Element name in page
            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + ariaName + "-" + TimeManager.getTimeStamp()+ ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            // TODO:
            AllureAttachmentManager.attachScreenshot(screenshotFile.getName(), screenshotFile.getAbsolutePath());
            LogsManager.info("Capturing Screenshot Succeeded");
        } catch (Exception e) {
            LogsManager.error("Failed to Capture Screenshot " + e.getMessage());
        }
    }
}
