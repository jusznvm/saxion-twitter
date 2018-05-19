package com.example.justin.simpletwitter.parser;

import android.util.Log;

import com.example.justin.simpletwitter.model.DirectMessage;
import com.example.justin.simpletwitter.model.Entity;
import com.example.justin.simpletwitter.model.Hashtag;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 5/9/2018.
 */

public class JSONParser {

    private static final String TAG = "JSONParser";



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


                /*
                Parse all entity JSONArrays in the entityObj and link them to a status
                then set the entity URL location in the status creation
                 */
                // parse hashtags in tweetObj
                JSONObject hashtagObj = tweetObj.getJSONObject("entities");

                JSONArray JSONHashtags = hashtagObj.getJSONArray("hashtags");

                ArrayList<Entity> hashtagsList = parseHashtags(JSONHashtags);
                // TODO: Make parser work on



                // Create a status object
                Status status = new Status(tweetText, createdAt,
                        retweetCount, favoriteCount,
                        favorited, retweeted);

                // Parse user info
                JSONObject userObj = tweetObj.getJSONObject("user");

                String userName = userObj.getString("screen_name");
                String name = userObj.getString("name");
                String imgUrl = userObj.getString("profile_image_url");

                // Create a user object
                User user = new User(userName, name, imgUrl);

                // Set the right user and entities to the tweet
                status.setUser(user);
                status.setHashtagEntities(hashtagsList);


                statuses.add(status);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return statuses;
    }




    /*
    TODO: Make parser return a lists for each entity available
     */
    private static ArrayList<Entity> parseHashtags(JSONArray entityObj) {
        ArrayList<Entity> hashtags = new ArrayList<>();
        Log.d(TAG, "parseHashtags, entityObj: " + entityObj);
        try{
            for (int i = 0; i < entityObj.length(); i++){
                JSONObject tempObj = entityObj.getJSONObject(i);
                JSONArray indicesJSONArray = tempObj.getJSONArray("indices");

                String text = tempObj.getString("text");
                int startIndex = indicesJSONArray.getInt(0);
                int endIndex = indicesJSONArray.getInt(1);

                Hashtag hashtag = new Hashtag(text, startIndex, endIndex);
                Log.d(TAG, "parseHashtags, Hashtag pre add: " + hashtag);
                hashtags.add(hashtag);

            }
        }catch (JSONException e){
            e.printStackTrace();
        }



        return hashtags;
    }

    public static ArrayList<DirectMessage> parseDMs(JSONObject jsonObject) {
        ArrayList<DirectMessage> dms = new ArrayList<>();
        Log.d(TAG, "parseDMs, jsonObject: " + jsonObject);
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

                JSONObject hashtagObj = dmObj.getJSONObject("entities");

                JSONArray JSONEntities = hashtagObj.getJSONArray("hashtags");

                ArrayList<Entity> hashtagsList = parseHashtags(JSONEntities);


                DirectMessage dm = new DirectMessage(
                        recipientID, senderID,
                        dmID, timestamp,
                        text);
                Log.d("DEBUG", "itemCount : " + i);
                dm.setHashtagEntities(hashtagsList);
                dms.add(dm);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dms;
    }

}
