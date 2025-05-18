package org.example.pages;

import org.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckOutPage extends BasePage {

    public CheckOutPage(WebDriver driver) {
        super(driver);
    }

    private final By changeAddressButtonLocator = By.cssSelector("a[data-args=\"redirectReason=shipaddressselectChangeClicked\"]");
    private final By addNewAddressButtonLocator = By.id("add-new-address-desktop-sasp-tango-link");

    private final By fullNameFieldLocator = By.id("address-ui-widgets-enterAddressFullName");
    private final By mobileNumberFieldLocator = By.id("address-ui-widgets-enterAddressPhoneNumber");
    private final By streetNameFieldLocator = By.id("address-ui-widgets-enterAddressLine1");
    private final By buildingNameFieldLocator = By.id("address-ui-widgets-enter-building-name-or-number");
    private final By cityFieldLocator = By.id("address-ui-widgets-enterAddressCity");
    private final By districtFieldLocator = By.id("address-ui-widgets-enterAddressDistrictOrCounty");
    private final By landMarkLocator = By.id("address-ui-widgets-landmark");
    private final By homeRadioButtonLocator = By.id("address-ui-widgets-addr-details-res-radio-input");
    private final By addAddressLocator = By.cssSelector("input[class=\"a-button-input\"][data-csa-c-slot-id=\"address-ui-widgets-continue-address-btn-bottom\"]");

    private final By itemsTotalLocator = By.xpath("//li[.//span[contains(text(), 'Items:')]]//div[@class='order-summary-line-definition']");
    private final By shippingLocator = By.xpath("//li[.//span[contains(text(), 'Shipping')]]//div[@class='order-summary-line-definition']");
    private final By discountLocator = By.xpath("//div[@class='a-row a-color-success']//div[contains(@class, 'a-text-right')]");
    private final By orderTotalLocator = By.xpath("//li[.//span[contains(text(), 'Order total')]]//div[@class='order-summary-line-definition']");

    public CheckOutPage clickOnChangeAddressButton() {
        clickIfVisible(changeAddressButtonLocator);
        return this;
    }

    public CheckOutPage clickOnAddNewAddressButton() {
        waitThenClickElement(addNewAddressButtonLocator);
        return this;
    }

    public CheckOutPage enterFullName(String fullName) {
        sendText(fullNameFieldLocator, fullName);
        return this;
    }

    public CheckOutPage enterMobileNumber(String mobileNumber) {
        sendText(mobileNumberFieldLocator, mobileNumber);
        return this;
    }

    public CheckOutPage enterStreetName(String streetName) {
        sendText(streetNameFieldLocator, streetName);
        return this;
    }

    public CheckOutPage enterBuildingName(String buildingName) {
        sendText(buildingNameFieldLocator, buildingName);
        return this;
    }

    public CheckOutPage enterCityName(String cityName) throws InterruptedException {
        sendTextAndTab(cityFieldLocator, cityName);
        return this;
    }

    public CheckOutPage enterDistrict(String district) {
        clickElement(districtFieldLocator);
        sendText(districtFieldLocator, district);
        clickElement(fullNameFieldLocator); // To trigger blur/focus loss
        return this;
    }

    public CheckOutPage enterLandmark(String landmark) {
        sendText(landMarkLocator, landmark);
        return this;
    }

    public CheckOutPage selectHomeAddressType() {
        clickElement(homeRadioButtonLocator);
        return this;
    }

    public void clickOnUseThisAddressButton() {
        clickElement(addAddressLocator);
    }

    public boolean isOrderTotalCorrect() {
        String itemsText = getText(itemsTotalLocator);
        String shippingText = getText(shippingLocator);
        String orderText = getText(orderTotalLocator);
        String discountText = isElementPresent(discountLocator) ? getText(discountLocator) : "0";

        double items = parseAmount(itemsText);
        double shipping = parseAmount(shippingText);
        double discount = parseAmount(discountText);
        double orderTotal = parseAmount(orderText);

        return (items + shipping + discount) == orderTotal;
    }

    private double parseAmount(String text) {
        return Double.parseDouble(text.replaceAll("[^\\d.-]", "").replace(",", "").trim());
    }
}
