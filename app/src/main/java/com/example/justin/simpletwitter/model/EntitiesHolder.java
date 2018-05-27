package com.example.justin.simpletwitter.model;

import java.util.ArrayList;

/**
 * Created by Yunus on 23-5-2018.
 */

public class EntitiesHolder {

    private ArrayList<Hashtag> hashtags = new ArrayList<>();
    private ArrayList<UserMention> userMentions = new ArrayList<>();
    private ArrayList<URL> urls = new ArrayList<>();

    public EntitiesHolder(){

    }

    public ArrayList<UserMention> getUserMentions() {
        return userMentions;
    }

    public void setUserMentions(ArrayList<UserMention> userMentions) {
        this.userMentions = userMentions;
    }

    public ArrayList<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public ArrayList<URL> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<URL> urls) {
        this.urls = urls;
    }
}
