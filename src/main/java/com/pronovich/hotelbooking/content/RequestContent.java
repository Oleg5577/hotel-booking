package com.pronovich.hotelbooking.content;

import java.util.HashMap;
import java.util.Map;

public class RequestContent {

    private Map<String, String> requestParameters = new HashMap<>();
    private HashMap<String, Object> requestAttributes = new HashMap<>();
    private Map<String, Object> sessionAttributes = new HashMap<>();
    private Map<String, String> wrongValues = new HashMap<>();

//    private HashMap<String, String[]> requestParameters;

    public RequestContent() {
    }

    public RequestContent(Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(Map<String,String> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public HashMap<String, Object> getRequestAttributes() {
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
