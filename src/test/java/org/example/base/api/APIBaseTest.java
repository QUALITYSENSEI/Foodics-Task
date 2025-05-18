package org.example.base.api;

import io.restassured.RestAssured;
import org.example.utils.PropertiesReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;

public class APIBaseTest {

    private static final Logger logger = LoggerFactory.getLogger(APIBaseTest.class);

    @BeforeClass
    public void setUp() {
        String baseUrl = PropertiesReaderUtil.getProperty("base.url");
        if (baseUrl == null || baseUrl.isEmpty()) {
            logger.warn("Base URL property is missing or empty! Check application.properties.");
        } else {
            RestAssured.baseURI = baseUrl;
            logger.info("RestAssured base URI set to: {}", baseUrl);
        }
    }
}
