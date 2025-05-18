package org.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReaderUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesReaderUtil.class);

    private static final Properties properties;

    // Private constructor to prevent instantiation
    private PropertiesReaderUtil() {}

    static {
        properties = new Properties();
        try (InputStream input = PropertiesReaderUtil.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input != null) {
                properties.load(input);
                logger.info("application.properties loaded successfully.");
            } else {
                logger.error("Unable to find application.properties in the classpath.");
            }

        } catch (IOException ex) {
            logger.error("IOException occurred while loading application.properties: {}", ex.getMessage(), ex);
        }
    }

    // Get property value as String
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        logger.debug("Property '{}' requested. Value: '{}'", key, value);
        return value;
    }

    // Get property value as Boolean
    public static boolean getBooleanProperty(String key) {
        String value = properties.getProperty(key);
        boolean booleanValue = Boolean.parseBoolean(value);
        logger.debug("Boolean property '{}' requested. Value: '{}'", key, booleanValue);
        return booleanValue;
    }
}
