package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPage {
    private final GUIDriver driver;

    public CheckoutPage(GUIDriver driver) {
        this.driver=driver;
    }

    //variables
    private final String endPoint="checkout";

    //locators
    private final By placeOrderButton= By.cssSelector("a[href=\"/payment\"]");
    //delivery
    private final By deliveryName= By.cssSelector("#address_delivery .address_firstname");
    private final By deliveryCompany= By.cssSelector("#address_delivery .address_address1 ");
    private final By deliveryAddress1= By.xpath("//ul[@id=\"address_delivery\"]/li[@class=\"address_address1 address_address2\"][2]");
    private final By deliveryAddress2= By.xpath("//ul[@id=\"address_delivery\"]/li[@class=\"address_address1 address_address2\"][3]");
    private final By deliveryCityStateZipCode= By.xpath("//ul[@id=\"address_delivery\"]/li[@class=\"address_city address_state_name address_postcode\"]");
    private final By deliveryCountry= By.cssSelector("#address_delivery .address_country_name");
    private final By deliveryPhone= By.cssSelector("#address_delivery .address_phone");

    //billing
    private final By billingName= By.cssSelector("#address_invoice .address_firstname");
    private final By billingCompany= By.cssSelector("#address_invoice .address_address1 ");
    private final By billingAddress1= By.xpath("//ul[@id=\"address_invoice\"]/li[@class=\"address_address1 address_address2\"][2]");
    private final By billingAddress2= By.xpath("//ul[@id=\"address_invoice\"]/li[@class=\"address_address1 address_address2\"][3]");
    private final By billingCityStateZipCode= By.xpath("//ul[@id=\"address_invoice\"]/li[@class=\"address_city address_state_name address_postcode\"]");
    private final By billingCountry= By.cssSelector("#address_invoice .address_country_name");
    private final By billingPhone= By.cssSelector("#address_invoice .address_phone");

    @Step("Navigate to Checkout Page")
    public CheckoutPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+ endPoint);
        return this;
    }
    @Step("Click on place order button")
    public PaymentPage clickPlaceOrderButton(){
        driver.element().click(placeOrderButton);
        return new PaymentPage(driver);
    }

    //validations

    @Step("Validate Delivery Address Info")
    public CheckoutPage validateDeliveryAddress(String title ,String firstName,String lastName, String company, String address1, String address2,
                                              String city,String state, String zip, String country, String phone) {
        driver.validation().Equals(driver.element().getText(deliveryName),title+". "+firstName+" "+lastName , " Delivery Name is not matched")
                .Equals(driver.element().getText(deliveryCompany), company, " Delivery Company is not matched")
                .Equals(driver.element().getText(deliveryAddress1), address1, " Delivery Address1 is not matched")
                .Equals(driver.element().getText(deliveryAddress2), address2, " Delivery Address2 is not matched")
                .Equals(driver.element().getText(deliveryCityStateZipCode), zip + " " +city+" " + state, " Delivery CityStateZip is not matched")
                .Equals(driver.element().getText(deliveryCountry), country, " Delivery Country is not matched")
                .Equals(driver.element().getText(deliveryPhone), phone, " Delivery Phone is not matched");
        return this;
    }


    @Step("Validate Billing Address Info")
    public CheckoutPage validateBillingAddress(String title ,String firstName,String lastName, String company, String address1
            , String address2,String city,String state, String zip, String country, String phone) {
        driver.validation().Equals(driver.element().getText(deliveryName),title+". "+firstName+" "+lastName , " Billing Name is not matched")
                .Equals(driver.element().getText(billingCompany), company, " Billing Company is not matched")
                .Equals(driver.element().getText(deliveryAddress1), address1, " Billing Address1 is not matched")
                .Equals(driver.element().getText(billingAddress2), address2, " Billing Address2 is not matched")
                .Equals(driver.element().getText(billingCityStateZipCode), zip + " " +city+" " + state, " Billing CityStateZip is not matched")
                .Equals(driver.element().getText(billingCountry), country, " Billing Country is not matched")
                .Equals(driver.element().getText(billingPhone), phone, " Billing Phone is not matched");
        return this;
    }
}
