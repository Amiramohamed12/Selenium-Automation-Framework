package com.automationexercise.utils.actions;

import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActions {
    private final WebDriver driver;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    public void maximizeWindow(){
        driver.manage().window().maximize();
    }

    public String getCurrentUrl(){
        LogsManager.info("Getting current URL: " + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    public void navigateTo(String url){
        LogsManager.info("Navigating to URL: " + url);
        driver.get(url);
    }

     public void refreshPage(){
        driver.navigate().refresh();
    }

    public void closeCurrentWindow(){
        driver.close();
    }

    public void openNewWindow(){
        driver.switchTo().newWindow(WindowType.WINDOW);
    }


}
