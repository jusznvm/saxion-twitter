package com.example.justin.simpletwitter;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

public class AppInfo {

    private static AppInfo instance = new AppInfo();

    private static final String API_KEY = "swthavxIcVgN8Eghi5ZtdwVH1";
    private static final String API_SECRET = "ddQLDes9DgGa9gixh4t31h0la3rfSAOKNG1tCfbNWnXw4tLaWh";
    private static final String CALLBACK_URL = "https://www.google.com";

    private static final OAuth10aService service = new ServiceBuilder(API_KEY).apiSecret(API_SECRET).callback(CALLBACK_URL).build(TwitterApi.instance());
    private static OAuth1RequestToken reqToken;
    private static OAuth1AccessToken accessToken;

    public static AppInfo getInstance() {
        if(instance == null) {
            instance = new AppInfo();
        }
        return instance;
    }

    public static OAuth10aService getService() {
        return service;
    }

    public static OAuth1RequestToken getToken() {
        return reqToken;
    }

    public static void setToken(OAuth1RequestToken token) {
        reqToken = token;
    }

    public static OAuth1AccessToken getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(OAuth1AccessToken newAccessToken) {
        accessToken = newAccessToken;
    }
}
