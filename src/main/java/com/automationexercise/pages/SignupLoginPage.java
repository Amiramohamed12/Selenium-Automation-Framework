package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
     //locators
    private final By emailLogin=By.cssSelector("form[action=\"/login\"] input[name=\"email\"]");
    private final By passwordLogin=By.cssSelector("form[action=\"/login\"] input[name=\"password\"]");
    private final By loginButton=By.cssSelector("[data-qa=\"login-button\"]");
    private final By signupButton = By.cssSelector("[data-qa='signup-button']");
    private final By nameSignup=By.cssSelector("[data-qa=\"signup-name\"]");
    private final By emailSignup=By.cssSelector("[data-qa=\"signup-email\"]");
    private final By loginErrorMessage = By.xpath("//p[@style='color: red;']");
    private final  By signupErrorMessage=By.cssSelector(".signup-form p");


    private final String signupLoginEndPoint=("login");
    private final GUIDriver driver;
    public NavigationBarComponent navigationBarComponent;
    public SignupLoginPage(GUIDriver driver) {
        this.driver=driver;
        navigationBarComponent = new NavigationBarComponent(driver);
    }

    //actions
    @Step("Navigate to signup/login page")
    public SignupLoginPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+signupLoginEndPoint);
        return this;
    }

    @Step("Enter login email")
    public SignupLoginPage enterLoginEmail(String email) {
        driver.element().type(emailLogin, email);
        return this;
    }

    @Step("Enter login password")
    public SignupLoginPage enterLoginPassword( String password) {
        driver.element().type(passwordLogin,password);
        return this;
    }
    @Step("Click login button")
    public SignupLoginPage clickLoginButton() {
        driver.element().click(loginButton);
        return this;
    }
    @Step("Enter signup name")
    public SignupLoginPage enterSignupName(String name) {
        driver.element().type(nameSignup, name);
        return this;
    }

    @Step("Enter signup email")
    public SignupLoginPage enterSignupEmail(String email) {
        driver.element().type(emailSignup, email);
        return this;
    }

    @Step("Click signup button")
    public SignupLoginPage clickSignupButton() {
        driver.element().click(signupButton);
        return this;
    }

    //validation
    @Step("Verify signup/login page opened")
    public SignupLoginPage verifySignupLoginPage() {
        driver.verification().assertPageUrl(PropertyReader.getProperty("baseUrlWeb")+signupLoginEndPoint);
        return this;
    }



   @Step("Verify login error message")
    public SignupLoginPage verifyLoginErrorMessage(String expectedError) {
        driver.verification().Equals(driver.element().getText(loginErrorMessage),expectedError,"Error message doesn't match");
        return this;
    }
    @Step("Verify signup error message")
    public SignupLoginPage verifySignupErrorMessage(String expectedError) {
        String actualError = driver.element().getText(signupErrorMessage);
        driver.verification().Equals(actualError,expectedError,"Error message does not match");
        return this;
    }

}
