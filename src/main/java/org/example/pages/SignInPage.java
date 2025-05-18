package org.example.pages;

import org.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage extends BasePage {

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    private final By phoneNumberFieldLocator = By.xpath("//input[@name='email']");
    private final By continueButtonLocator = By.id("continue");
    private final By passwordFieldLocator = By.id("ap_password");
    private final By signInButtonLocator = By.id("signInSubmit");

    public SignInPage enterPhoneNumber(String phoneNumber) {
        sendText(phoneNumberFieldLocator, phoneNumber);
        return this;
    }

    public SignInPage clickOnContinueButton() {
        clickElement(continueButtonLocator);
        return this;
    }

    public SignInPage enterPassword(String password) {
        sendText(passwordFieldLocator, password);
        return this;
    }

    public HomePage clickOnSignInButton() {
        clickElement(signInButtonLocator);
        return new HomePage(driver);
    }
}
