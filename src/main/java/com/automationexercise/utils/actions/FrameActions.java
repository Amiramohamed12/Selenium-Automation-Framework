package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class FrameActions {
    private final WebDriver driver;
    private final WaitManager waitManager;

    public FrameActions(WebDriver driver) {
        this.driver = driver;
        waitManager = new WaitManager(driver);
    }

    public void switchToFrameByIndex(int index){
        driver.switchTo().frame(index);
        waitManager.fluentWait().until(d -> {;
            try {
                driver.switchTo().frame(index);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
    /**
     * switch to frame by name or id
     * @param nameOrId
     */
    public void switchToFrameByNameOrID(String nameOrId){
        waitManager.fluentWait().until(d -> {
            try {
                driver.switchTo().frame(nameOrId);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * switch to frame by web element
     *  * @param locator
     */
    public void switchToFrameByElement(By locator){
        waitManager.fluentWait().until(d -> {
            try {
                driver.switchTo().frame(d.findElement(locator));
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * switch to default content
     *
     */
    public void switchToDefaultContent(){
        waitManager.fluentWait().until(d -> {
           // try {
                d.switchTo().defaultContent();
                return true;
           /* } catch (Exception e) {
                return false;
            }*/
        });
    }

     /**
     * switch to parent frame
     *
     */
    public void switchToParentFrame(){
        driver.switchTo().parentFrame();
    }

}
