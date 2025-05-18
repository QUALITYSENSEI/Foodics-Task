package org.example.pages;

import org.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VideoGamesPage extends BasePage {

    public VideoGamesPage(WebDriver driver) {
        super(driver);
    }

    private final By freeShippingFilterLocator = By.xpath("//ul[@aria-labelledby='p_n_free_shipping_eligible-title']");
    private final By newFilterConditionLocator = By.xpath("//span[text()='New']");
    private final By sortByPriceLocator = By.xpath("//*[@id='s-result-sort-select']");
    private final By priceLocator = By.xpath("//span[@class='a-price-whole']");
    private final By nextPageButtonLocator = By.linkText("Next");
    private final By goCartButton = By.id("nav-cart");

    public VideoGamesPage clickOnFreeShippingFilter() {
        clickElement(freeShippingFilterLocator);
        return this;
    }

    public VideoGamesPage clickOnNewConditionFilter() {
        clickElement(newFilterConditionLocator);
        return this;
    }

    public VideoGamesPage clickOnFilterByPrice(String option) {
        selectOptionFromDropdown(sortByPriceLocator, option);
        return this;
    }

    public VideoGamesPage addItemToCart(double price) {
        addAllProductsToCart(priceLocator, nextPageButtonLocator, price);
        return this;
    }

    public CartPage clickOnGoToCart() {
        waitThenClickElement(goCartButton);
        return new CartPage(driver);
    }
}
