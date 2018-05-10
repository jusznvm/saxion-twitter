package com.example.justin.simpletwitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Justin on 5/9/2018.
 */

public class JSONParser {

    public static void JSONParser() {}

    public static ArrayList<Status> parseStatus(JSONObject jsonObject) {
        ArrayList<Status> statuses = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("statuses");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject tweetObj = jsonArray.getJSONObject(i);

                // Parse tweet stuff
                String tweetText = tweetObj.getString("text");
                String createdAt = tweetObj.getString("created_at");

                int retweetCount = tweetObj.getInt("retweet_count");
                int favoriteCount = tweetObj.getInt("retweet_count");

                boolean favorited = tweetObj.getBoolean("favorited");
                boolean retweeted = tweetObj.getBoolean("retweeted");

                // Create a status object
                Status status = new Status(tweetText, createdAt,
                        retweetCount, favoriteCount,
                        favorited, retweeted);
                statuses.add(status);

                // Parse user info
                JSONObject userObj = tweetObj.getJSONObject("user");

                String userName = userObj.getString("screen_name");
                String name = userObj.getString("name");
                String imgUrl = userObj.getString("profile_image_url");

                // Create a user object
                User user = new User(userName, name, imgUrl);

                // Set the right user to the tweet
                status.setUser(user);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return statuses;
    }

}
