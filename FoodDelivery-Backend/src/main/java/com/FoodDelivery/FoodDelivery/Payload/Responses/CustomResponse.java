package com.FoodDelivery.FoodDelivery.Payload.Responses;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom response class for sending information back to front end
 */
public class CustomResponse {
    /**
     * Map containing key "message" and response ("message":"XYZ")
     */
    private Map<String, String> response = new HashMap();

    public CustomResponse(String key, String value) {
        response.put(key, value);
    }
    public CustomResponse(){}

    public Map<String, String> getResponse() {
        return response;
    }

    public void setResponse(Map<String, String> message) {
        this.response = message;
    }
}
