package com.example.justin.simpletwitter.parser;

import android.util.Log;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.model.DirectMessage;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Justin on 5/9/2018.
 */

public class JSONParser {

    public static void JSONParser() {}

    public static ArrayList<Status> parseStatus(JSONArray jsonArray) {
        ArrayList<Status> statuses = new ArrayList<>();
        try {
//            JSONArray jsonArray = jsonObject.getJSONArray("statuses");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject tweetObj = jsonArray.getJSONObject(i);

                // Parse tweet stuff
                String tweetText = tweetObj.getString("text");
                String createdAt = tweetObj.getString("created_at");

                int retweetCount = tweetObj.getInt("retweet_count");
                int favoriteCount = tweetObj.getInt("favorite_count");

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

                JSONObject entity = tweetObj.getJSONObject("entities");
                parseEntity(entity);

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

    public static ArrayList<DirectMessage> parseDMs(JSONObject jsonObject) {
        ArrayList<DirectMessage> dms = new ArrayList<>();
        Log.d("Parsing DMs", "jsonObject: " + jsonObject);
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("events");
            for(int i = 0; i < jsonArray.length(); i++) {
                // Get a DMObject
                JSONObject dmObj = jsonArray.getJSONObject(i);


                // Get the DM Content from the DMObject
                JSONObject dmContent = dmObj.getJSONObject("message_create");

                long timestamp = dmObj.getInt("created_timestamp");
                long dmID = dmObj.getInt("id");

                long recipientID = dmContent.getJSONObject("target").getLong("recipient_id");
                long senderID = dmContent.getLong("sender_id");

                String text = dmContent.getJSONObject("message_data").getString("text");

                DirectMessage dm = new DirectMessage(
                        recipientID, senderID,
                        dmID, timestamp,
                        text);
                Log.d("DEBUG", "itemCount : " + i);
                dms.add(dm);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dms;
    }

    public static void parseEntity(JSONObject jsonObject) {

    }
}
