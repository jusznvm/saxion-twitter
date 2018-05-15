package com.example.justin.simpletwitter;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class InfoApi {

    private static InfoApi instance = new InfoApi();

    private static final String API_KEY = "swthavxIcVgN8Eghi5ZtdwVH1";
    private static final String API_SECRET = "ddQLDes9DgGa9gixh4t31h0la3rfSAOKNG1tCfbNWnXw4tLaWh";

    private static final OAuth10aService service = new ServiceBuilder(API_KEY).apiSecret(API_SECRET).build(TwitterApi.instance());

    public static InfoApi getInstance() {
        return instance;
    }

    public static OAuth1RequestToken getRequestToken() throws InterruptedException, ExecutionException, IOException {
        return getService().getRequestToken();
    }

    public static OAuth10aService getService() {
        return service;
    }

}
