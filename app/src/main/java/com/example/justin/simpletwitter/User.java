package com.example.justin.simpletwitter;

/**
 * Created by Justin on 5/10/2018.
 */

public class User {

    private String userName;
    private String name;
    private String imgUrl;

    public User(String username, String name, String imgUrl) {
        this.name = name;
        this.userName = username;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
