package org.example.factory;

import org.example.utils.PropertiesReaderUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WebDriverSingleton {

    private static final Logger logger = LoggerFactory.getLogger(WebDriverSingleton.class);

    private static WebDriver driver;

    // Private constructor to prevent instantiation
    private WebDriverSingleton() {}

    public static WebDriver getDriver() {
        if (driver == null) {
            logger.info("Initializing WebDriver instance.");

            ChromeOptions options = new ChromeOptions();

            // Load Chrome options from properties file
            if (PropertiesReaderUtil.getBooleanProperty("chrome.options.start-maximized")) {
                options.addArguments("--start-maximized");
                logger.debug("Chrome option set: --start-maximized");
            }
            if (PropertiesReaderUtil.getBooleanProperty("chrome.options.disable-notifications")) {
                options.addArguments("--disable-notifications");
                logger.debug("Chrome option set: --disable-notifications");
            }
            if (PropertiesReaderUtil.getBooleanProperty("chrome.options.incognito")) {
                options.addArguments("--incognito");
                logger.debug("Chrome option set: --incognito");
            }
            if (PropertiesReaderUtil.getBooleanProperty("chrome.options.headless")) {
                options.addArguments("--headless"); // For Docker containers
                logger.debug("Chrome option set: --headless");
            }

            driver = new ChromeDriver(options);
            logger.info("ChromeDriver created successfully.");

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            logger.debug("Implicit wait set to 20 seconds.");
        } else {
            logger.info("Reusing existing WebDriver instance.");
        }

        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            logger.info("Quitting WebDriver instance.");
            try {
                driver.quit();
                logger.info("WebDriver quit successfully.");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver: {}", e.getMessage(), e);
            } finally {
                driver = null;
            }
        } else {
            logger.info("WebDriver instance is already null; nothing to quit.");
        }
    }
}
