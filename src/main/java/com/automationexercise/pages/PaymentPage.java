package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentPage {
    private final GUIDriver driver;

    public PaymentPage(GUIDriver driver) {
        this.driver=driver;
    }

    //variables
    private final String endPoint="payment";

    //locators
    private final By cardName=By.cssSelector("[data-qa=\"name-on-card\"]");
    private final By cardNumber=By.cssSelector("[data-qa=\"card-number\"]");
    private final By cardMonth=By.cssSelector("[data-qa=\"expiry-month\"]");
    private final By cardYear=By.cssSelector("[data-qa=\"expiry-year\"]");
    private final By cardCVC=By.cssSelector("[data-qa=\"cvc\"]");
    private final By payButton=By.cssSelector("[data-qa=\"pay-button\"]");
    private final By paymentSuccessMessage =By.xpath("(//b)[2]");
    private final By continueButton=By.cssSelector("[data-qa=\"continue-button\"]");
    private final By downloadInvoiceButton=By.xpath("//a[.=\"Download Invoice\"]");

    //actions
     @Step("Navigate to payment page")
     public PaymentPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + endPoint);
        return this;
     }
     //Enter card details
    @Step("Enter card details")
    public PaymentPage enterCardDetails(String cardName,String cardNumber, String cardCVC,String expiryMonth, String expiryYear){
         driver.element().type(this.cardName,cardName)
                          .type(this.cardNumber,cardNumber)
                          .type(this.cardCVC,cardCVC)
                          .type(cardMonth, expiryMonth)
                           .type(cardYear, expiryYear)
                           .click(payButton);
         return this;

    }

    @Step("Click continue button")
    public NavigationBarComponent clickContinueButton(){
        driver.element().click(continueButton);
        return new NavigationBarComponent(driver);
    }
    @Step("Click download invoice button")
    public PaymentPage clickDownloadInvoiceButton(){
        driver.element().click(downloadInvoiceButton);
        return this;
    }

    //validations
    @Step("Verify payment success message")
    public PaymentPage verifySuccessMessage(String expectedMessage){
        driver.verification().Equals(driver.element().getText(paymentSuccessMessage), expectedMessage,"Payment success message does not match");
        return this;
    }

    @Step("Verify invoice file is downloaded")
    public PaymentPage verifyInvoiceDownloaded(String fileName){
        driver.verification().assertFileExists(fileName,"File is not downloaded");
        return this;
    }
}
