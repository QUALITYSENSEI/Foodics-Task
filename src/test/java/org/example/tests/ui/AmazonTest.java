package org.example.tests.ui;

import org.example.base.ui.UiBaseTest;
import org.example.pages.CheckOutPage;
import org.example.pages.HomePage;
import org.example.utils.JsonReaderUtil;
import org.testng.annotations.Test;

public class AmazonTest extends UiBaseTest {
    double priceThreshold = 15000;

    @Test(description = "E2E Test: Purchase a video game item under a price threshold from Amazon.eg")
    public void testPurchaseFlow() throws InterruptedException {
        HomePage homePage = new HomePage(driver);

        homePage
                // Step 1: Navigate to Amazon homepage
                .navigateToHomePage()

                // Step 2: Perform login using phone number and password
                .navigateToLoginPage()
                .enterPhoneNumber(JsonReaderUtil.getValue("phoneNumber"))
                .clickOnContinueButton()
                .enterPassword(JsonReaderUtil.getValue("password"))
                .clickOnSignInButton()

                // Step 3: Navigate to All Video Games section
                .clickOnLeftSideMenu()
                .clickOnSeeAllMenuButton()
                .clickOnVideoGames()
                .clickOnAllVideoGames()

                // Step 4: Apply filters - Free Shipping and New Condition
                .clickOnFreeShippingFilter()
                .clickOnNewConditionFilter()

                // Step 5: Sort items by Price: High to Low
                .clickOnFilterByPrice("Price: High to Low")

                // Step 6: Add the first available product below the price threshold (15,000 EGP)
                .addItemToCart(priceThreshold)

                // Step 7: Proceed to cart and validate total price
                .clickOnGoToCart()
                .getTotalPriceLocator(priceThreshold)

                // Step 8: Proceed to checkout and enter address details
                .clickOnBuyButton()
                .clickOnChangeAddressButton()
                .clickOnAddNewAddressButton()
                .enterCityName(JsonReaderUtil.getValue("cityArea"))
                .enterFullName(JsonReaderUtil.getValue("fullName"))
                .enterMobileNumber(JsonReaderUtil.getValue("phoneNumber"))
                .enterStreetName(JsonReaderUtil.getValue("streetName"))
                .enterBuildingName(JsonReaderUtil.getValue("buildingName"))
                .enterDistrict(JsonReaderUtil.getValue("district"))
                .enterLandmark(JsonReaderUtil.getValue("landMark"))
                .selectHomeAddressType()
                .clickOnUseThisAddressButton();
        Thread.sleep(5000);

                //Step 9: make sure that the total amount of all items is correct with the shipping fees if exist
        CheckOutPage checkOutPage=new CheckOutPage(driver);
                        assert checkOutPage.isOrderTotalCorrect();
        Thread.sleep(5000);
    }
}
