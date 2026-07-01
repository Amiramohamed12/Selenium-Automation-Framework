package com.automationexercise.tests.ui;


import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Cart Management")
@Feature("UI Cart Details")
@Story("Cart Details")
@Owner("Amira")
@Severity(SeverityLevel.CRITICAL)

public class CartTest extends BaseTest {

    @Description("Validate product details on cart without login")
    @Test
    public void validateProductDetailsOnCartWithoutLoginTC() {
        new ProductsPage(driver).navigate()
                .clickAddToCartButton(testData.getJsonData("product.name"))
                .verifyAddedToCartMessage(testData.getJsonData("messages.addedToCart"))
                .clickViewCartButton()
                .validateProductDetails(testData.getJsonData("product.name"),testData.getJsonData("product.price"),testData.getJsonData("product.quantity"),testData.getJsonData("product.totalPrice")) ;
    }


    @BeforeClass
    public void setTestData() {
        testData= new JsonReader("cart-data");
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
