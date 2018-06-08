package com.example.justin.simpletwitter.utils;

public  class TwitterAPI {

    /**
     * GET
     * The following URLS all require a GET-verb.
     */

    /*
     * STATUSES
     */
    public static final String STATUSES_HOME_TIMELINE = "https://api.twitter.com/1.1/statuses/home_timeline.json";
    public static final String STATUSES_USER_TIMELINE = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";
    public static final String STATUSES_USER_MENTIONS = "https://api.twitter.com/1.1/statuses/mentions_timeline.json";

    /*
     * FAVORITES
     */
    public static final String FAVORITES_LIST = "https://api.twitter.com/1.1/favorites/list.json?screen_name=";

    /*
     * DIRECT MESSAGE
     */
    public static final String DMS_EVENTS_LIST = "https://api.twitter.com/1.1/direct_messages/events/list.json";
    public static final String DMS_EVENT_SHOW = "https://api.twitter.com/1.1/direct_messages/events/show.json";

    /*
     * USER
     */
    public static final String USER_SHOW = "https://api.twitter.com/1.1/users/show.json?screen_name=";
    public static final String ACCOUNT_CREDENTIALS = "https://api.twitter.com/1.1/account/verify_credentials.json";

    /*
     * SEARCH
     */
    public static final String SEARCH_USER = "https://api.twitter.com/1.1/users/search.json?q="; // requires query
    public static final String SEARCH_TWEET = "https://api.twitter.com/1.1/search/tweets.json?q="; // requires query

    /**
     * POST
     * The follow URLS all require a POST-verb
     */
    public static final String STATUSES_UPDATE = "https://api.twitter.com/1.1/statuses/update.json?status=";

    /*
     * FAVORITE
     */
    public static final String FAVORITE_STATUS_CREATE = "https://api.twitter.com/1.1/favorites/create.json?id=";
    public static final String FAVORITE_STATUS_DESTROY = "https://api.twitter.com/1.1/favorites/destroy.json?id=";

    /*
     * RETWEET
     */
    public static final String RETWEET_STATUS = "https://api.twitter.com/1.1/statuses/retweet/"; // Needs ID and '.json'
    public static final String UNRETWEET_STATUS = "https://api.twitter.com/1.1/statuses/unretweet/"; // Needs ID and '.json'

    /*
     * FOLLOW
     */

    public static final String UNFOLLOW_USER = "https://api.twitter.com/1.1/friendships/destroy.json?screen_name="; // Needs screenName;
    public static final String FOLLOW_USER = "https://api.twitter.com/1.1/friendships/create.json?screen_name="; // Needs screenName;


}
