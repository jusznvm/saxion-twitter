package com.example.justin.simpletwitter.model;

/**
 * Created by yunus on 5/27/2018.
 */

public class URL {

    private int startIndex;
    private int endIndex;

    private String url;
    private String displayUrl;
    private String expandedUrl;

    public URL(int startIndex, int endIndex, String url, String displayUrl, String expandedUrl) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.url = url;
        this.displayUrl = displayUrl;
        this.expandedUrl = expandedUrl;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
