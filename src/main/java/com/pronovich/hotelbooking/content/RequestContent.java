package com.pronovich.hotelbooking.content;

import java.util.HashMap;
import java.util.Map;

/**
 * Instance of the class are created in commands and contains all information which necessary for execution of the command
 * Are using as replacement HttpServletRequest for storing parameters and attributes
 * Also used for storing wrong Values map which contains wrong values as a key and message as a value
 */
public class RequestContent {

    /**
     * Map of requestParameters obtained from HttpServletRequest
     */
    private Map<String, String> requestParameters = new HashMap<>();

    /**
     * Map of requestAttributes which are added in the program
     */
    private Map<String, Object> requestAttributes = new HashMap<>();

    /**
     * Map of sessionAttributes which are added in the program
     */
    private Map<String, Object> sessionAttributes = new HashMap<>();

    /**
     * Map of wrongValues which are added in the program in validation
     */
    private Map<String, String> wrongValues = new HashMap<>();

    public RequestContent() {
    }

    public RequestContent(Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public void addRequestAttributes(String key, Object value) {
        this.requestAttributes.put(key, value);
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void addSessionAttribute(String key, Object value) {
        this.sessionAttributes.put(key, value);
    }

    public Map<String, String> getWrongValues() {
        return wrongValues;
    }

    public void addWrongValues(Map<String, String> wrongValues) {
        this.wrongValues.putAll(wrongValues);
    }
}
