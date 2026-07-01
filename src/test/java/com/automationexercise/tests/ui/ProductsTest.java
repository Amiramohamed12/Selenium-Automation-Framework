package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.*;

@Epic("Automation Exercise")
@Feature("UI Products Management")
@Story("Products Management")
@Owner("Amira")
@Severity(SeverityLevel.NORMAL)
public class ProductsTest extends BaseTest {

    @Test
    @Description("Validate searched product details without logging in")
    public void validateSearchedProductDetailsWithoutLoggingInTC(){
        new ProductsPage(driver).navigate()
                .searchByProductName(testData.getJsonData("searchedProduct.name"))
                .clickSearchButton()
                .validateProductDetails(testData.getJsonData("searchedProduct.name"), testData.getJsonData("searchedProduct.price"));
    }
    @Test
    @Description("Validate added to cart message without logging in")
    public void validateAddedToCartMessageWithoutLoggingInTC(){
        new ProductsPage(driver).navigate()
                .clickAddToCartButton(testData.getJsonData("product1.name"))
                .verifyAddedToCartMessage(testData.getJsonData("messages.addedToCart"));
    }




    @BeforeClass
    public void setTestData(){
        testData=new JsonReader("products-data");
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
