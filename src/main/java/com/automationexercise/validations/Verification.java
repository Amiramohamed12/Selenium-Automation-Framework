package com.automationexercise.validations;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

//Hard Assertion
public class Verification extends BaseAssertion{

    public Verification (WebDriver driver) {
        super(driver);
    }
    public Verification() {
       // super();
    }

    @Override
    protected void assertEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }
}
