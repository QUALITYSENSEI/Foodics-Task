package org.example.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class BasePage {

    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    protected WebDriver driver;
    private final Duration defaultTimeout = Duration.ofSeconds(3);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        logger.info("BasePage initialized with WebDriver: {}", driver.getClass().getSimpleName());
    }

    // --------------------------------------------
    // Basic Element Actions
    // --------------------------------------------

    public WebElement findElement(By locator) {
        logger.debug("Finding element by locator: {}", locator);
        return driver.findElement(locator);
    }

    public List<WebElement> getAllElements(By locator) {
        logger.debug("Getting all elements by locator: {}", locator);
        return driver.findElements(locator);
    }

    public void clickElement(By locator) {
        logger.info("Clicking element by locator: {}", locator);
        findElement(locator).click();
    }

    public void sendText(By locator, String text) {
        logger.info("Sending text '{}' to element by locator: {}", text, locator);
        findElement(locator).sendKeys(text);
    }

    public String getText(By locator) {
        logger.debug("Getting text from element by locator: {}", locator);
        return findElement(locator).getText();
    }

    public boolean isElementPresent(By locator) {
        try {
            boolean present = driver.findElement(locator).isDisplayed();
            logger.debug("Element present: {} -> {}", locator, present);
            return present;
        } catch (Exception e) {
            logger.warn("Element not present: {}. Exception: {}", locator, e.getMessage());
            return false;
        }
    }

    // --------------------------------------------
    // Advanced Element Actions
    // --------------------------------------------

    public void waitThenClickElement(By locator) {
        logger.info("Waiting for element to be clickable: {}", locator);
        WebDriverWait wait = new WebDriverWait(driver, defaultTimeout);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            element.click();
            logger.info("Clicked element: {}", locator);
        } catch (ElementClickInterceptedException e) {
            logger.warn("Element click intercepted, retrying with JavaScript for: {}", locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void clickIfVisible(By locator) {
        try {
            logger.info("Checking if element is visible to click: {}", locator);
            WebDriverWait wait = new WebDriverWait(driver, defaultTimeout);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.click();
            logger.info("Element clicked (was visible): {}", locator);
        } catch (TimeoutException ignored) {
            logger.warn("Element not visible in given time: {}", locator);
        }
    }

    public void sendTextAndTab(By locator, String text) throws InterruptedException {
        logger.info("Sending text '{}' and TAB to element: {}", text, locator);
        WebElement element = findElement(locator);
        Thread.sleep(2000);
        element.sendKeys(text);
        Thread.sleep(2000);
        element.sendKeys(Keys.TAB);
    }

    // --------------------------------------------
    // Dropdowns
    // --------------------------------------------

    public void selectOptionFromDropdown(By locator, String option) {
        logger.info("Selecting option '{}' from dropdown: {}", option, locator);
        Select select = new Select(findElement(locator));
        select.selectByVisibleText(option);
    }

    // --------------------------------------------
    // Use Case Specific Methods
    // --------------------------------------------

    public void addAllProductsToCart(By priceLocator, By nextPageButtonLocator, double priceThreshold) {
        logger.info("Adding products under price {} to cart (max 10 pages)", priceThreshold);
        int pageCount = 0;
        final int maxPages = 10;
        int totalAdded = 0;

        while (pageCount++ < maxPages) {
            List<WebElement> priceElements = getAllElements(priceLocator);
            boolean foundUnderThreshold = false;

            for (WebElement priceElement : priceElements) {
                try {
                    String rawPrice = priceElement.getText().replace(",", "").trim();
                    if (rawPrice.isEmpty()) continue;

                    double price = Double.parseDouble(rawPrice);
                    if (price >= priceThreshold) continue;

                    foundUnderThreshold = true;

                    WebElement productContainer = priceElement.findElement(
                            By.xpath("./ancestor::div[contains(@class, 'puis-card-container')]")
                    );

                    WebElement addToCartButton = productContainer.findElement(
                            By.xpath(".//button[contains(@class, 'a-button-text')]")
                    );

                    try {
                        addToCartButton.click();
                        logger.info("Added product to cart with price: {}", price);
                    } catch (ElementClickInterceptedException e) {
                        logger.warn("Add to cart button click intercepted, retrying with JavaScript.");
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
                    }

                    totalAdded++;

                } catch (Exception e) {
                    logger.error("Error adding product to cart: {}", e.getMessage());
                }
            }

            if (foundUnderThreshold) {
                logger.info("Found and added product under threshold. Total added: {}", totalAdded);
                return;
            }

            try {
                clickElement(nextPageButtonLocator);
                logger.info("Moving to next page: {}", pageCount);
                Thread.sleep(3000);
            } catch (Exception e) {
                logger.warn("No more pages or navigation failed: {}", e.getMessage());
                break;
            }
        }
    }
}
