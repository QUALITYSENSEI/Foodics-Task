package org.example.pages;

import org.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private final By totalPriceLocator = By.id("sc-subtotal-amount-buybox");
    private final By processToBuyButtonLocator = By.name("proceedToRetailCheckout");
    private final By totalItemsLocator = By.id("sc-subtotal-label-buybox");
    private final By noThanksButton = By.id("prime-decline-button");

    public CartPage getTotalPriceLocator(double price) {
        double totalPrice = Double.parseDouble(
                getText(totalPriceLocator)
                        .trim()
                        .replace(",", "")
                        .replace("EGP ", "")
        );

        String itemText = getText(totalItemsLocator);
        int numberOfItems = Integer.parseInt(itemText.replaceAll("[^0-9]", ""));

        double averagePrice = totalPrice / numberOfItems;
        System.out.println("Average Price = " + averagePrice);

        return this;
    }

    public CheckOutPage clickOnBuyButton() {
        clickElement(processToBuyButtonLocator);
        clickIfVisible(noThanksButton);
        return new CheckOutPage(driver);
    }
}
