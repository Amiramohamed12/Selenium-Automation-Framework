package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.google.j2objc.annotations.Property;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

public class ProductsPage {

    private final GUIDriver driver;
    public NavigationBarComponent navigationBarComponent;

    public ProductsPage(GUIDriver driver) {
        this.driver=driver;
        navigationBarComponent=new NavigationBarComponent(driver);
    }
    //variables
    private final String endPoint="products";

    //locators
    private By searchBox=By.id("search_product");
    private By searchButton=By.id("submit_search");
    private By viewCartButton=By.xpath("//u[.=\"View Cart\"]");
    private By continueShoppingButton=By.xpath("//button[.=\"Continue Shopping\"]");
    private By addedToCartMessage=By.cssSelector("p.text-center");


    //dynamic locators
    private By productName(String productName) {
        return By.xpath("//div[@class='overlay-content'] /p[.='" + productName + "']");
    }

    private By productPrice(String productName) {
        return By.xpath("//div[@class='overlay-content'] /p[.='" + productName + "'] //preceding-sibling::h2");
    }

    private By addToCartButton(String productName){
        return By.xpath("//div[@class='overlay-content']/p[.='" + productName + "']/following-sibling::a");
    }
    private By hoverOnProduct(String productName) {
        return By.xpath("//p[.='" + productName + "']");
    }

    private By viewProduct(String productName) {
        return By.cssSelector("[href=\"/product_details/1\"]");
    }

    //actions
    @Step("Navigate to Products Page")
    public ProductsPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+ endPoint);
        return this;
    }
    @Step("Search for product {productName}")
    public ProductsPage searchByProductName(String productName){
       driver.element().type(searchBox, productName);
        return this;
    }
    @Step("Click search button")
    public ProductsPage clickSearchButton(){
        driver.element().click(searchButton);
        return this;
    }


    @Step("Click add to cart button")
    public ProductsPage clickAddToCartButton(String productName){
        driver.element().hover(hoverOnProduct(productName));
        driver.element().click(addToCartButton(productName));
        return this;
    }

    @Step("Click view product button")
    public ProductDetailsPage clickViewProductButton(String productName){
        driver.element().click(viewProduct(productName));
        return new ProductDetailsPage(driver);
    }

    @Step("Click view cart button")
    public CartPage clickViewCartButton(){
        driver.element().click(viewCartButton);
        return new CartPage(driver);
    }

    @Step("Click continue shopping button")
    public ProductsPage clickContinueShoppingButton(){
        driver.element().click(continueShoppingButton);
        return this;
    }

    //validations
    @Step("Verify added to cart message {expectedMessage}")
    public ProductsPage verifyAddedToCartMessage(String expectedMessage){
        String actualMessage=driver.element().getText(addedToCartMessage);
        driver.verification().Equals(actualMessage,expectedMessage," Added to cart Message doesn't match");
        return this;
    }

    //validations
    @Step("Validate product details for {productName} with price {productPrice}")
    public ProductsPage validateProductDetails(String productName, String productPrice) {
        String actualProductName = driver.element().hover(productName(productName)).getText(productName(productName));
        String actualProductPrice = driver.element().hover(productName(productName)).getText(this.productPrice(productName));
        LogsManager.info("Validating product details for: " + actualProductName, " with price: " + actualProductPrice);
        driver.validation().Equals(actualProductName, productName, "Product name does not match");
        driver.validation().Equals(actualProductPrice, productPrice, "Product price does not match");
        return this;
    }


}
