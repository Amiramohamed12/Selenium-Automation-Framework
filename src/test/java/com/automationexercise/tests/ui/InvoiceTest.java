package com.automationexercise.tests.ui;


import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.*;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Payment Management")
@Feature("UI Payment Management")
@Story("Payment Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Amira")
public class InvoiceTest extends BaseTest {

    private String timeStamp= TimeManager.getSimpleTimeStamp();

    @Description("Register new user")
    @Test
    public void registerTC() {
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
    @Test(dependsOnMethods = "registerTC")
    public void loginTC() {
        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timeStamp+ "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBarComponent.
                verifyUserLabel(testData.getJsonData("name"));



    }
    @Description("Add to cart")
    @Test(dependsOnMethods = {"loginTC", "registerTC"})
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
    @Test(dependsOnMethods = {"addToCartTC", "loginTC", "registerTC"})
    public void checkoutTC() {
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


    }
    @Description("Payment test")
    @Test(dependsOnMethods = {"checkoutTC","loginTC","registerTC","addToCartTC"})
    public void paymentTC() {
        new CheckoutPage(driver)
                .clickPlaceOrderButton().enterCardDetails(testData.getJsonData("cardName"), testData.getJsonData("cardNumber"), testData.getJsonData("cardCVC"),
                        testData.getJsonData("cardExpiryMonth"), testData.getJsonData("cardExpiryYear"))
                .verifySuccessMessage(testData.getJsonData("messages.paymentSuccess"));
    }

   /* @Description("Invoice test")
    @Test(dependsOnMethods = {"checkoutTC","loginTC","registerTC","addToCartTC","paymentTC"})
    public void invoiceTC() {
        new PaymentPage(driver)
                .clickDownloadInvoiceButton()
                .verifyInvoiceDownloaded(testData.getJsonData("invoiceName"));
    }*/

    @Description("Delete created user")
    @Test(dependsOnMethods = {"paymentTC","loginTC","registerTC","addToCartTC","checkoutTC"})
    public void deleteUserAsPostconditionTC() {
        new UserManagementAPI().deleteUserAccount(testData.getJsonData("email") + timeStamp+ "@gmail.com", testData.getJsonData("password"));
    }



    @BeforeClass
    public void setUp() {
        driver=new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        testData= new JsonReader("payment-data");
    }


    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }

}
