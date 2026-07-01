package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.SignupPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("User Registration")
@Owner("Amira")
@Severity(SeverityLevel.NORMAL)
public class RegisterTest extends BaseTest {
    String timeStamp=TimeManager.getSimpleTimeStamp();

    @Description("Verify that user can register successfully")
    @Test
    public void validSignUpTC() {
        new SignupLoginPage(driver).navigate()
                .enterSignupName(testData.getJsonData("name"))
                .enterSignupEmail(testData.getJsonData("email") + timeStamp + "@gmail.com")
                .clickSignupButton();
        new SignupPage(driver)
                .fillRegisterationForm(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("password"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobileNumber")
                )
                .clickCreateAccountButton()
                .verifyAccountCreated();

        new UserManagementAPI().deleteUserAccount(testData.getJsonData("email") + timeStamp + "@gmail.com", testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }
    @Description("Verify that user can't register with existing email and name")
    @Test
    public void verifyErrorMessageWhenAccountCreatedBefore()
    {
        //precondition > create a user account
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timeStamp+ "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterSignupName(testData.getJsonData("name"))
                .enterSignupEmail(testData.getJsonData("email") + timeStamp + "@gmail.com")
                .clickSignupButton().verifySignupErrorMessage(testData.getJsonData("messages.error"));

        new UserManagementAPI().deleteUserAccount(testData.getJsonData("email") + timeStamp + "@gmail.com", testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @BeforeClass
    public void setTestData(){
        testData=new JsonReader("register-data");
    }

    @BeforeMethod
    public void setUp() {
        driver=new GUIDriver();
        new NavigationBarComponent(driver).navigate();
    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }

}
