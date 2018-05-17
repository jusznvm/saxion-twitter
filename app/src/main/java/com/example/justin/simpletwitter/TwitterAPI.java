package com.example.justin.simpletwitter;

public  class TwitterAPI {

    /**
     * GET
     * The following URLS all require a GET-verb.
     */

    /*
        STATUSES
     */
    public static final String STATUSES_HOME_TIMELINE = "https://api.twitter.com/1.1/statuses/home_timeline.json";
    public static final String STATUSES_USER_TIMELINE = "https://api.twitter.com/1.1/statuses/user_timeline.json";
    public static final String STATUSES_USER_MENTIONS = "https://api.twitter.com/1.1/statuses/mentions_timeline.json";


    /*
     * DIRECT MESSAGE
     */
    public static final String DMS_EVENTS_LIST = "https://api.twitter.com/1.1/direct_messages/events/list.json";
    public static final String DMS_EVENT_SHOW = "https://api.twitter.com/1.1/direct_messages/events/show.json";


    /**
     * POST
     * The follow URLS all require a POST-verb
     */

    public static final String STATUSES_UPDATE = "https://api.twitter.com/1.1/statuses/update.json?status=";


}
