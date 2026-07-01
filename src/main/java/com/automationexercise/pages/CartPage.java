package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    private final GUIDriver driver;
    public CartPage(GUIDriver driver) {
        this.driver=driver;
    }

    //variables
    private final String endPoint="view_cart";

    //locators
    private final By proceedToCheckoutButton=By.cssSelector(".col-sm-6 a");
    private final By email=By.id("susbscribe_email");
    private final By subscribeButton=By.id("subscribe");
    private final By subscriptionMessage=By.cssSelector("#success-subscribe div");

    //dynamic locators
    private By productName(String productName) {
        return By.xpath("//td[@class=\"cart_description\"]//a[.='" + productName + "']");
    }
    private By productPrice(String productName) {
        return By.xpath("//td[@class=\"cart_description\"]//a[.='" + productName + "']/ancestor::tr/td[@class=\"cart_price\"]/p");
    }
    private By productQuantity(String productName) {
        return By.xpath("//td[@class=\"cart_description\"]//a[.='" + productName + "']/ancestor::tr/td[@class=\"cart_quantity\"]/button");
    }
    private By productTotalPrice(String productName) {
        return By.xpath("//td[@class=\"cart_description\"]//a[.='" + productName + "']/ancestor::tr/td[@class=\"cart_total\"]/p");
    }
    private By removeProductButton(String productName) {
        return By.xpath("//td[@class=\"cart_description\"]//a[.='" + productName + "']/ancestor::tr/td[@class=\"cart_delete\"]/a"); //TODO :ADD /a in the end
    }

    //actions
    @Step("Navigate to Cart Page")
    public CartPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + endPoint);
        return this;
    }
    @Step("Click Proceed to checkout button")
    public CheckoutPage clickProceedToCheckoutButton(){
        driver.element().click(this.proceedToCheckoutButton);
        return new CheckoutPage(driver);
    }
    @Step("Type email for subscription")
    public CartPage typeEmail(String email){
        driver.element().type(this.email, email);
        driver.element().click(subscribeButton);
        return this;
    }
    @Step("Remove product from cart")
    public CartPage clickRemoveProductButton(String productName){
        driver.element().click(removeProductButton(productName));
        return this;
    }


    //validations
    @Step("Verify subscription message")
    public CartPage verifySubscriptionMessage(String message){
        driver.verification().Equals(driver.element().getText(subscriptionMessage), message,"Subscription message is not matched");
        return this;
    }
    @Step("Validate product details on cart")
    public CartPage validateProductDetails(String expectedName, String expectedPrice, String expectedQuantity, String expectedTotalPrice){
       String name = driver.element().getText(this.productName(expectedName));
       String price = driver.element().getText(this.productPrice(expectedName));
       String quantity=driver.element().getText(this.productQuantity(expectedName));
       String totalPrice=driver.element().getText(this.productTotalPrice(expectedName));
       driver.validation().Equals(name, expectedName,"Product name is not matched");
       driver.validation().Equals(price,expectedPrice,"Product price is not matched");
       driver.validation().Equals(quantity, expectedQuantity,"Product quantity is not matched");
       driver.validation().Equals(totalPrice, expectedTotalPrice,"Product total price is not matched");
        return this;
    }

}
