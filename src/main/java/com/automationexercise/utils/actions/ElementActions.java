package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class ElementActions {
    private WebDriver driver;
    private WaitManager waitManager;


    public ElementActions(WebDriver driver){
        this.driver=driver;
        waitManager =new WaitManager(driver);

    }

    public ElementActions click(By locator) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                element.click();
                return true;
            } catch (Exception e) {
                LogsManager.error("Failed to click" + locator+e.getMessage());
                return false;
            }
        });
        return this;
    }

    public ElementActions type(By locator, String text) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                element.sendKeys(text);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }
    public ElementActions selectFromDropdown(By locator,String text){
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                new Select(element).selectByVisibleText(text);
                LogsManager.info("Selected " , text , " from dropdown");
                return true;
            } catch (Exception e) {
                return false;
            }

        });
        return this;

    }

    public String getText(By locator) {
        return waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);
                LogsManager.info("Getting elemet"+locator);
                scrollToElementJS(locator);
                LogsManager.info("Getting text from element: " + locator);
                String msg = element.getText();
                return !msg.isEmpty() ? msg : null;
            } catch (Exception e) {
                LogsManager.error("Failed to get text from element: " + locator+e.getMessage());
                return null;
            }
        });

    }

    //upload file
    public ElementActions uploadFile(By locator, String filePath) {
        String absolutePath = System.getProperty("user.dir") + File.separator+ filePath;
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                element.sendKeys(absolutePath);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }

    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    public ElementActions hover (By locator) {
          waitManager.fluentWait().until(d ->{
                  try{
                      WebElement element = d.findElement(locator);
                      scrollToElementJS(locator);
                      new Actions(d).moveToElement(element).perform();
                      return true;
                  } catch (Exception e) {
                      LogsManager.error("Failed to hover on element: " + locator+e.getMessage());
                      return false;
                  }
       });
          return this;
    }

    //function to scroll to an element using js
    public void scrollToElementJS(By locator) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("""
            arguments[0].scrollIntoView({behaviour:"auto",block:"center",inline:"center"});""", findElement(locator));
    }

}
