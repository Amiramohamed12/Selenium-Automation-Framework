package com.automationexercise.validations;

import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

//Soft Assertion
public class Validation extends BaseAssertion{

   private static SoftAssert softAssert = new SoftAssert();
   private static boolean used = false;

    public Validation(WebDriver driver) {
        super(driver);
    }
    public Validation() {
        super();
    }

    @Override
    protected void assertEquals(String actual, String expected, String message) {
        softAssert.assertEquals(actual,expected,message);
        used = true;

    }

    @Override
    protected void assertTrue(boolean condition, String message) {
       softAssert.assertTrue(condition, message);
       used = true;

    }

    @Override
    protected void assertFalse(boolean condition, String message) {
       softAssert.assertEquals(condition, false, message);
       used = true;

    }

    public static void assertAll(ITestResult result) {
        if (!used) return; // If no assertions were made, do nothing
        try {
            softAssert.assertAll();
        }
        catch (AssertionError e)
        {
            LogsManager.error("Assertion failed:", e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        }
        finally {
            softAssert = new SoftAssert(); // Reset the soft assert instance
        }
    }
}
