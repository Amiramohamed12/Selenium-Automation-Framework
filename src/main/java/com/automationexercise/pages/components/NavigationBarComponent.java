package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.*;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {

    //locators
    private final By homeButton=By.xpath("//a[text()=\" Home\"]");
    private final By productsButton=By.xpath("//a[text()=\" Products\"]");
    private final By cartButton =By.xpath("//a[text()=\" Cart\"]");
    private final By signUpLoginButton=By.xpath("//a[text()=\" Signup / Login\"]");
    private final By testCasesButton=By.xpath("//a[text()=\" Test Cases\"]");
    private final By logoutButton=By.cssSelector("a[href=\"/logout\"]");
    private final By deleteAccountButton = By.xpath("//a[.=' Delete Account']");
    private final By apiButton = By.xpath("//a[.=' API Testing']");
    private final By videoTutorials = By.xpath("//a[.=' Video Tutorials']");
    private final By contactUsButton = By.xpath("//a[.=' Contact us']");
    private final By homePageLabel = By.cssSelector("h1 > span");
    private final By userLabel = By.tagName("b");


    //private final By productsIcon;
    private final GUIDriver driver;

    public NavigationBarComponent(GUIDriver driver){
      this.driver=driver;
    }

    //actions
    @Step("Navigate to Home Page")
    public NavigationBarComponent navigate()
    {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }
    @Step("Click on Home Button")
    public NavigationBarComponent clickOnHomeButton ()
    {
        driver.element().click(homeButton);
        return this;
    }
    @Step("Click on Products Button")
    public ProductsPage clickOnProductsButton()
    {
        driver.element().click(productsButton);
        return new ProductsPage(driver);
    }
    @Step("Click on Cart Button")
    public CartPage clickOnCartButton()
    {
        driver.element().click(cartButton);
        return new CartPage(driver);
    }
    @Step("Click on Logout Button")
    public LogoutPage clickOnLogoutButton()
    {
        driver.element().click(logoutButton);
        return new LogoutPage(driver);
    }
    @Step("Click on Signup/Login Button")
    public SignupLoginPage clickOnSignupLoginButton()
    {
        driver.element().click(signUpLoginButton);
        return new SignupLoginPage(driver);
    }
    @Step("Click on Test Cases Button")
    public TestCasesPage clickOnTestCasesButton()
    {
        driver.element().click(testCasesButton);
        return new TestCasesPage(driver);
    }
    @Step("Click on Delete Account Button")
    public DeleteAccountPage clickOnDeleteAccountButton()
    {
        driver.element().click(deleteAccountButton);
        return new DeleteAccountPage(driver);
    }
    @Step("Click on ContactUs Button Button")
    public ContactUsPage clickOnContactUsButton()
    {
        driver.element().click(contactUsButton);
        return new ContactUsPage(driver);
    }



    //validations
    @Step("Verify Home Page Label")
    public NavigationBarComponent verifyHomePageLabel() {
        driver.verification().isElementVisible(homePageLabel);
        return this;
    }

    @Step("Verify User Label")
    public NavigationBarComponent verifyUserLabel(String expectedUserLabel){
        driver.verification().Equals(driver.element().getText(userLabel),expectedUserLabel,"User label does not match");
        return this;
    }
}
