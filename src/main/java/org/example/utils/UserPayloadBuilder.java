package org.example.utils;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserPayloadBuilder {

    private static final Logger logger = LoggerFactory.getLogger(UserPayloadBuilder.class);

    public static String userPayload(String name, String job) {
        logger.info("Building user payload. Name: '{}', Job: '{}'", name, job);
        JSONObject payload = new JSONObject();
        payload.put("name", name);
        payload.put("job", job);
        String result = payload.toString();
        logger.debug("Payload built: {}", result);
        return result;
    }
}
