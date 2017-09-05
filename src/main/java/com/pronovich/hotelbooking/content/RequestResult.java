package com.pronovich.hotelbooking.content;

public class RequestResult {

    /**
     * Path to page
     */
    private String page;

    /**
     * Type of navigation (forward / redirect)
     */
    private NavigationType navigationType;

    public RequestResult(String page, NavigationType navigationType) {
        this.page = page;
        this.navigationType = navigationType;
    }

    public String getPage() {
        return page;
    }

    public NavigationType getNavigationType() {
        return navigationType;
    }
}
