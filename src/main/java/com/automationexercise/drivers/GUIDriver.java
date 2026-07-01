package com.automationexercise.drivers;

import com.automationexercise.utils.actions.AlertActions;
import com.automationexercise.utils.actions.BrowserActions;
import com.automationexercise.utils.actions.ElementActions;
import com.automationexercise.utils.actions.FrameActions;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.validations.Validation;
import com.automationexercise.validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;


public class GUIDriver {
    private ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final String browser = PropertyReader.getProperty("browserType");

    public GUIDriver() {      //Init GUIDriver object
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Initializing WebDriver for browser: " + browserType);
        AbstractDriver driverFactory = browserType.getFactory();
        WebDriver driver = ThreadGuard.protect(driverFactory.createDriver());
        driverThreadLocal.set(driver);
    }
    public ElementActions element() {
        return new ElementActions(initDriver());
    }
    public BrowserActions browser() {
        return new BrowserActions(initDriver());
    }
    public FrameActions frame() {
        return new FrameActions(initDriver());
    }
    public AlertActions alert() {
        return new AlertActions(initDriver());
    }
    //soft assertions
    public Validation validation() {
        return new Validation(initDriver());
    }
    // hard assertions
    public Verification verification() {
        return new Verification(initDriver());
    }
    public WebDriver initDriver() {
        return driverThreadLocal.get();
    }

    public void quitDriver() {
        driverThreadLocal.get().quit();
    }
}
