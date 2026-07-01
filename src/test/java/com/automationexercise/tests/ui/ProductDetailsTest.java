package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.ProductDetailsPage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.*;

@Epic("Products Management")
@Feature("UI Product Details")
@Story("Product Details")
@Owner("Amira")
@Severity(SeverityLevel.NORMAL)
public class ProductDetailsTest extends BaseTest {
    @Description("Validate product details without logging in")
    @Test
    public void validateProductDetailsWithoutLoggingInTC(){
        new ProductsPage(driver).navigate()
                .clickViewProductButton(testData.getJsonData("product.name"))
                .validateProductDetails(testData.getJsonData("product.name"), testData.getJsonData("product.price") );
    }

    @Description("Verify thanks for review message")
    @Test
    public void verifyThanksForReviewMessageTC(){
       // new ProductDetailsPage(driver).navigate()
        new ProductsPage(driver).navigate()
                .clickViewProductButton(testData.getJsonData("product.name"))
                .typeName(testData.getJsonData("review.name"))
                .typeEmail(testData.getJsonData("review.email"))
                .typeReview(testData.getJsonData("review.review"))
                .verifyReviewMessage(testData.getJsonData("messages.thanksForReview"));
    }

    @BeforeClass
    public void setTestData(){
        testData=new JsonReader("product-details-data");
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
