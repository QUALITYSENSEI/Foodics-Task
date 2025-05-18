package org.example.utils;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReaderUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonReaderUtil.class);

    private static final JSONObject jsonData;

    // Private constructor to prevent instantiation
    private JsonReaderUtil() {}

    static {
        final String filePath = "src/test/resources/testData.json";
        jsonData = readJsonFile(filePath);
    }

    // Reads the JSON file and returns a JSONObject
    private static JSONObject readJsonFile(String filePath) {
        logger.info("Reading JSON file from: {}", filePath);
        String jsonContent = readFile(filePath);
        if (jsonContent == null) {
            logger.error("Failed to read JSON file: {}", filePath);
            throw new RuntimeException("Could not read JSON file at: " + filePath);
        }
        try {
            JSONObject json = new JSONObject(jsonContent);
            logger.info("Successfully loaded JSON data from {}", filePath);
            return json;
        } catch (Exception e) {
            logger.error("Invalid JSON format in file: {}. Error: {}", filePath, e.getMessage());
            throw new RuntimeException("Invalid JSON format in file: " + filePath, e);
        }
    }

    // Reads file content into a String
    private static String readFile(String filePath) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            logger.debug("File read successfully: {}", filePath);
            return new String(bytes);
        } catch (IOException e) {
            logger.error("IO Exception while reading file {}: {}", filePath, e.getMessage());
            return null;
        }
    }

    // Retrieves the value for a given key from the loaded JSON
    public static String getValue(String key) {
        if (jsonData.has(key)) {
            logger.debug("Retrieved value for key '{}' from JSON.", key);
            return jsonData.getString(key);
        } else {
            logger.error("Key '{}' not found in JSON data.", key);
            throw new IllegalArgumentException("Key '" + key + "' not found in JSON data.");
        }
    }
}
