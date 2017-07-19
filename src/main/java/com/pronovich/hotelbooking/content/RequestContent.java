package com.pronovich.hotelbooking.content;

import java.util.HashMap;
import java.util.Map;

public class RequestContent {

    private Map<String, String> requestParameters = new HashMap<>();
    private Map<String, String> wrongValues = new HashMap<>();
//    private HashMap<String, Object> sessionAttributes;
//    private HashMap<String, Object> requestAttributes;
//    private HashMap<String, String[]> requestParameters;

    // поля для атрибутов сессии и запроса

    public RequestContent() {
    }

    public RequestContent(Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public void addWrongValues(Map<String, String> wrongValues) {
        this.wrongValues.putAll(wrongValues);
    }

    public Map<String, String> getWrongValues() {
        return wrongValues;
    }

    public void extractValues() {

    }

    public void insertAttributes() {

    }

    public Map<String, String> getParameters() {
        return requestParameters;
    }

    public void setRequestParameters(Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
    }
}
