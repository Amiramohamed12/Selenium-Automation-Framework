package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetailsPage {
    private final GUIDriver driver;
    public ProductDetailsPage(GUIDriver driver) {
        this.driver=driver;
    }

    //variables
    private final String endPoint="product-details/1";

    //locators
    private final By productName= By.cssSelector(".product-information h2");
    private final By productPrice= By.cssSelector(".product-information span>span");
    private final By name = By.id("name");
    private final By email = By.id("email");
    private final By reviewTextArea = By.id("review");
    private final By reviewButton = By.id("button-review");
    private final By thanksForReviewMessage = By.cssSelector("#review-section span");


    //actions
    @Step("Navigate to Product Details Page")
    public ProductDetailsPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+ endPoint);
        return this;
    }

    @Step("Type name in review section")
    public ProductDetailsPage typeName(String name){
        driver.element().type(this.name,name);
        return this;
    }

    @Step("Type email address in review section")
    public ProductDetailsPage typeEmail(String email){
        driver.element().type(this.email,email);
        return this;
    }
    @Step("Type review in review section")
    public ProductDetailsPage typeReview(String review){
        driver.element().type(reviewTextArea,review);
        driver.element().click(reviewButton);
        return this;
    }

    @Step("Verify thanks for review message ")
    public ProductDetailsPage verifyReviewMessage(String message){
        String actualMessage=driver.element().getText((thanksForReviewMessage));
        driver.verification().Equals(actualMessage,message,"Thanks for review message does not match");
        return this;
    }

    //verify product details after clicking on view product
    @Step("Validate product details after clicking on view product")
    public ProductDetailsPage validateProductDetails(String expectedName,String expectedPrice){
        String actualProductName=driver.element().getText(productName);
        String actualProductPrice=driver.element().getText(productPrice);
        driver.validation().Equals(actualProductName, expectedName,"Product name does not match");
        driver.validation().Equals(actualProductPrice, expectedPrice,"Product price does not match");
        return this;
    }







}
