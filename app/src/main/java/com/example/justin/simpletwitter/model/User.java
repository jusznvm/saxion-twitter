package com.example.justin.simpletwitter.model;

/**
 * Created by Justin on 5/10/2018.
 */

public class User {

    private String userName;
    private String name;
    private String imgUrl;

    private String location;
    private String description;

    private int statusCount;
    private int followingCount;
    private int followersCount;

    private boolean hidden;

    // Profile aesthetics
    private String backgroundUrl;
    private String bannerUrl;

    private boolean following;
    private boolean requestedFollow;

    public User(String username, String name, String imgUrl) {
        this.name = name;
        this.userName = username;
        this.imgUrl = imgUrl;
    }

    public User(String username, String name, String imgUrl,
                String location, String description, int statusCount,
                int followersCount, int followingCount,
                boolean following) {
        this.userName = username;
        this.name = name;
        this.imgUrl = imgUrl;
        this.location = location;
        this.description = description;
        this.statusCount = statusCount;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
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

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getStatusCount() {
        return statusCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public boolean isFollowing() {
        return following;
    }
}
