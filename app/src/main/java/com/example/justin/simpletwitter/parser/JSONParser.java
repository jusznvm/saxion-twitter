package com.example.justin.simpletwitter.parser;

import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;

import com.example.justin.simpletwitter.adapter.StatusAdapter;
import com.example.justin.simpletwitter.model.DirectMessage;
import com.example.justin.simpletwitter.model.EntitiesHolder;
import com.example.justin.simpletwitter.model.Hashtag;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.model.User;
import com.example.justin.simpletwitter.model.UserMention;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

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
                 */
                // parse hashtags in tweetObj
                JSONObject entitiesObj = tweetObj.getJSONObject("entities");


                // ArrayList<Entity> hashtagsList = parseHashtags(JSONHashtags);
                // TODO: Make parser work Entities instead of subtypes door de entitiesObj mee te geven en een entitiesHolder te returnen;

                EntitiesHolder entitiesHolder = parseEntities(entitiesObj); // general parse method






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
                status.setEntities(entitiesHolder);


                statuses.add(status);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return statuses;
    }


    private static EntitiesHolder parseEntities(JSONObject entitiesObject){
        EntitiesHolder entitiesHolder = new EntitiesHolder();

        try {
            entitiesHolder.setHashtags(parseHashtags(entitiesObject.getJSONArray("hashtags")));
            entitiesHolder.setUserMentions(parseUserMentions(entitiesObject.getJSONArray("user_mentions")));




        } catch (JSONException e) {
            e.printStackTrace();
        }


        return entitiesHolder;
    }

    private static ArrayList<Hashtag> parseHashtags(JSONArray hashtagArray) {
        ArrayList<Hashtag> hashtags = new ArrayList<>();
        Log.d(TAG, "parseHashtags, hashtagObj: " + hashtagArray);
        try{
            for (int i = 0; i < hashtagArray.length(); i++){
                JSONObject tempObj = hashtagArray.getJSONObject(i);
                JSONArray indicesJSONArray = tempObj.getJSONArray("indices");

                String text = tempObj.getString("text");
                int startIndex = indicesJSONArray.getInt(0);
                int endIndex = indicesJSONArray.getInt(1);

                Hashtag hashtag = new Hashtag(text, startIndex, endIndex);
                Log.d(TAG, "parseHashtags, Hashtag pre add: " + hashtag + i);
                hashtags.add(hashtag);

            }
        }catch (JSONException e){
            e.printStackTrace();
        }



        return hashtags;
    }

    private static ArrayList<UserMention> parseUserMentions(JSONArray mentions){
        ArrayList<UserMention> userMentions = new ArrayList<>();
        Log.d(TAG, "parseUserMentions, mentionsObj: " + mentions);
        try{
            for (int i = 0; i < mentions.length(); i++){
                JSONObject mentionObject = mentions.getJSONObject(i);

                JSONArray indicesArray = mentionObject.getJSONArray("indices");


                String screenName = mentionObject.getString("screen_name");
                String name = mentionObject.getString("name");
                int id = mentionObject.getInt("id");
                String idString = mentionObject.getString("id_str");

                int startIndex = indicesArray.getInt(0);
                int endIndex = indicesArray.getInt(1);


                UserMention userMention = new UserMention(
                        screenName, name,
                        idString, id,
                        startIndex, endIndex);

                Log.d(TAG, "parseUserMentions: userMention: " + userMention);

                userMentions.add(userMention);




            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return userMentions;
    }



    public static User parseUser(JSONObject jsonObject) {
        User userModel = null;
        try {
            String username = jsonObject.getString("screen_name");
            String name = jsonObject.getString("name");
            String imgUrl = jsonObject.getString("profile_image_url");
            String location = jsonObject.getString("location");
            String description = jsonObject.getString("description");
            int statusCount = jsonObject.getInt("statuses_count");
            userModel = new User(username, name, imgUrl,
                            location, description, statusCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "parseUser: " + userModel.getDescription());
        return userModel;
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

                JSONObject entitiesObj = dmObj.getJSONObject("entities");


                EntitiesHolder entitiesHolder = parseEntities(entitiesObj);


                DirectMessage dm = new DirectMessage(
                        recipientID, senderID,
                        dmID, timestamp,
                        text);
                Log.d("DEBUG", "itemCount : " + i);
                dm.setEntitiesHolder(entitiesHolder);
                dms.add(dm);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dms;
    }


}
