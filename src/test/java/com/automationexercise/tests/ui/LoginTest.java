package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.*;

@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("User Login")
@Owner("Amira")
public class LoginTest extends BaseTest {

    String timeStamp = TimeManager.getSimpleTimeStamp();

  @Description("Verify that user can login successfully")
  @Test
  public void validLoginTC() {
      new UserManagementAPI().createRegisterUserAccount(
              testData.getJsonData("name"),
              testData.getJsonData("email") + timeStamp + "@gmail.com",
              testData.getJsonData("password"),
              testData.getJsonData("firstName"),
              testData.getJsonData("lastName"))
              .verifyUserCreatedSuccessfully();
      new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email")+timeStamp+"@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBarComponent
                .verifyUserLabel(testData.getJsonData("name"));

      new UserManagementAPI().deleteUserAccount(testData.getJsonData("email") + timeStamp + "@gmail.com", testData.getJsonData("password"))
              .verifyUserDeletedSuccessfully();

  }
   @Description("Verify that user can't login with invalid email")
    @Test
    public void invalidLoginEmailTC() {
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();
        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email")+"123"+timeStamp+"@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .verifyLoginErrorMessage(testData.getJsonData("messages.error"));

        new UserManagementAPI().deleteUserAccount(testData.getJsonData("email") + timeStamp + "@gmail.com", testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }
    @Description("Verify that user can't login with invalid password")
    @Test
    public void invalidLoginPasswordTC() {
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();
        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email")+timeStamp+"@gmail.com")
                .enterLoginPassword(testData.getJsonData("password")+" ")
                .clickLoginButton()
                .verifyLoginErrorMessage(testData.getJsonData("messages.error"));

        new UserManagementAPI().deleteUserAccount(testData.getJsonData("email") + timeStamp + "@gmail.com", testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }


    @BeforeClass
    public void setTestData() {
        testData= new JsonReader("login-data");
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
