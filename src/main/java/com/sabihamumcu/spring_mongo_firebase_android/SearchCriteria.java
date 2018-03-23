package com.sabihamumcu.spring_mongo_firebase_android;

/**
 * Created by sabis on 2/11/2018.
 */

public class SearchCriteria {

    private String searchCriteria;

    public SearchCriteria() {
    }

    public SearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}
