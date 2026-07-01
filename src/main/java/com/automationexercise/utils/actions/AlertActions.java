package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;

public class AlertActions {
    private final WebDriver driver;
    private final WaitManager waitManager;


    public AlertActions(WebDriver driver) {
        this.driver = driver;
        waitManager=new WaitManager(driver);
    }

    public void acceptAlert() {
        waitManager.fluentWait().until(d -> {
                try {
                    d.switchTo().alert().accept();
                    return true;
                } catch (Exception e) {
                    LogsManager.error("Failed to accept alert: " + e.getMessage());
                    return false;
                }
            });
        }

    public void dismissAlert() {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().alert().dismiss();
                return true;
            } catch (Exception e) {
                LogsManager.error("Failed to dismiss alert: " + e.getMessage());
                return false;
            }
        });
    }


    public String getAlertText() {
        return waitManager.fluentWait().until(d -> {
            try {
               String msg= d.switchTo().alert().getText();
                return msg.isEmpty() ? msg : null;
            } catch (Exception e) {
                LogsManager.error("Failed to get alert text: " + e.getMessage());
                return null;
            }
        });
    }

     public void sendKeysToAlert(String text) {
         waitManager.fluentWait().until(d -> {
             try {
                 d.switchTo().alert().sendKeys(text);
                 return true;
             } catch (Exception e) {
                 LogsManager.error("Failed to send keys to alert: " + e.getMessage());
                 return false;
             }
         });
    }
}
