package org.example.pages;

import org.example.base.BasePage;
import org.example.utils.PropertiesReaderUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By leftSideMenuLocator = By.id("nav-hamburger-menu");
    private final By seeAllMenuButtonLocator = By.xpath("//div[contains(text(),'See all')]");
    private final By videoGamesLocator = By.xpath("//a[@data-menu-id='16']");
    private final By allVideoGamesLocator = By.xpath("//a[contains(text(),'All Video Games')]");
    private final By signInPage = By.cssSelector("div[id=\"nav-link-accountList\"] a[data-nav-role=\"signin\"]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage navigateToHomePage() {
        driver.get(PropertiesReaderUtil.getProperty("baseURL"));
        return this;
    }

    public SignInPage navigateToLoginPage() {
        clickElement(signInPage);
        return new SignInPage(driver);
    }

    public HomePage clickOnLeftSideMenu() {
        clickElement(leftSideMenuLocator);
        return this;
    }

    public HomePage clickOnSeeAllMenuButton() {
        clickElement(seeAllMenuButtonLocator);
        return this;
    }

    public HomePage clickOnVideoGames() {
        clickElement(videoGamesLocator);
        return this;
    }

    public VideoGamesPage clickOnAllVideoGames() {
        waitThenClickElement(allVideoGamesLocator);
        return new VideoGamesPage(driver);
    }
}
