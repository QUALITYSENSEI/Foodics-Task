package org.example.base.ui;

import org.example.factory.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class UiBaseTest {

    private static final Logger logger = LoggerFactory.getLogger(UiBaseTest.class);

    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        logger.info("Setting up WebDriver for UI tests.");
        driver = WebDriverSingleton.getDriver();
        logger.info("WebDriver setup complete: {}", driver != null ? driver.getClass().getSimpleName() : "null");
    }

    @AfterClass
    public void tearDown() {
        logger.info("Tearing down WebDriver after UI tests.");
        WebDriverSingleton.quitDriver();
        logger.info("WebDriver teardown complete.");
    }
}
