package com.example.justin.simpletwitter.model.entity;

/**
 * Created by yunus on 5/27/2018.
 */

public class URL extends Entity {

    private String displayUrl;
    private String expandedUrl;


    public URL(String text, int startIndex, int endIndex, String displayUrl, String expandedUrl) {
        super(text, startIndex, endIndex);
        this.displayUrl = displayUrl;
        this.expandedUrl = expandedUrl;
    }


    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    @Override
    public String getType() {
        return "URL";
    }
}
