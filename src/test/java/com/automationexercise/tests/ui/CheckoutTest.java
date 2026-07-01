package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.Description;
import io.qameta.allure.*;
import org.testng.annotations.*;


@Epic("Checkout Management")
@Feature("UI Checkout Management")
@Story("Checkout Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Amira")
public class CheckoutTest extends BaseTest {
   private String timeStamp= TimeManager.getSimpleTimeStamp();

    @Description("Register new user")
    @Test
    public void registerNewUserTC() {
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
        ).verifyUserCreatedSuccessfully();

    }
    @Description("Login user")
    @Test(dependsOnMethods = "registerNewUserTC")
    public void loginUserTC() {
        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timeStamp+ "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBarComponent.
                verifyUserLabel(testData.getJsonData("name"));



    }
    @Description("Add to cart")
    @Test(dependsOnMethods = {"loginUserTC", "registerNewUserTC"})
    public void addToCartTC() {
        new ProductsPage(driver).navigate()
                .clickAddToCartButton(testData.getJsonData("product.name"))
                .verifyAddedToCartMessage(testData.getJsonData("messages.addedToCart"))
                .clickViewCartButton()
                .validateProductDetails(
                        testData.getJsonData("product.name"),
                        testData.getJsonData("product.price"),
                        testData.getJsonData("product.quantity"),
                        testData.getJsonData("product.totalPrice")
                );
    }
    @Description("Checkout")
    @Test(dependsOnMethods = {"addToCartTC", "loginUserTC", "registerNewUserTC"})
    public void checkout() {
        new CartPage(driver)
                .clickProceedToCheckoutButton()
                .validateDeliveryAddress(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber"))
                .validateBillingAddress(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber"));

                new UserManagementAPI().deleteUserAccount(testData.getJsonData("email")+timeStamp+"@gmail.com", testData.getJsonData("password"))
                        .verifyUserDeletedSuccessfully();
    }




    @BeforeClass
    public void setUp() {
        driver=new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        testData= new JsonReader("checkout-data");
    }


    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}
