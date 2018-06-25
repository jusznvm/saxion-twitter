package com.example.justin.simpletwitter.utils;

import com.example.justin.simpletwitter.model.DirectMessage;
import com.example.justin.simpletwitter.model.entity.Entity;
import com.example.justin.simpletwitter.model.entity.Hashtag;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.model.entity.URL;
import com.example.justin.simpletwitter.model.User;
import com.example.justin.simpletwitter.model.entity.UserMention;

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
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tweetObj = jsonArray.getJSONObject(i);

                // Parse tweet stuff
                int tweetID = tweetObj.getInt("id");

                String tweetIDString = tweetObj.getString("id_str");
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

//                EntitiesHolder entitiesHolder = parseEntities(entitiesObj); // general parse method
                ArrayList<Entity> entitiesList = parseEntities(entitiesObj);

                // Create a status object
                Status status = new Status(
                        tweetIDString, tweetText, createdAt,
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
                status.setEntitiesList(entitiesList);

                statuses.add(status);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return statuses;
    }

    private static ArrayList<Entity> parseEntities(JSONObject entitiesObject) {
        ArrayList<Entity> entitiesList = new ArrayList<>();

        try {
            entitiesList.addAll(parseHashtags(entitiesObject.getJSONArray("hashtags")));
            entitiesList.addAll(parseUserMentions(entitiesObject.getJSONArray("user_mentions")));
            entitiesList.addAll(parseURLs(entitiesObject.getJSONArray("urls")));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entitiesList;
    }

    private static ArrayList<Hashtag> parseHashtags(JSONArray hashtagArray) {
        ArrayList<Hashtag> hashtags = new ArrayList<>();
        try {
            for (int i = 0; i < hashtagArray.length(); i++) {
                JSONObject tempObj = hashtagArray.getJSONObject(i);
                JSONArray indicesJSONArray = tempObj.getJSONArray("indices");

                String text = tempObj.getString("text");
                int startIndex = indicesJSONArray.getInt(0);
                int endIndex = indicesJSONArray.getInt(1);

                Hashtag hashtag = new Hashtag(text, startIndex, endIndex);
                hashtags.add(hashtag);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hashtags;
    }

    private static ArrayList<URL> parseURLs(JSONArray URLs) {
        ArrayList<URL> urls = new ArrayList<>();
        try {
            for (int i = 0; i < URLs.length(); i++) {
                JSONObject URLObject = URLs.getJSONObject(i);

                JSONArray indicesArray = URLObject.getJSONArray("indices");

                String link = URLObject.getString("url");
                String displayUrl = URLObject.getString("display_url");
                String expandedUrl = URLObject.getString("expanded_url");

                int startIndex = indicesArray.getInt(0);
                int endIndex = indicesArray.getInt(1);

                URL url = new URL(
                        link, startIndex, endIndex, displayUrl, expandedUrl
                );

                urls.add(url);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return urls;
    }

    private static ArrayList<UserMention> parseUserMentions(JSONArray mentions) {
        ArrayList<UserMention> userMentions = new ArrayList<>();
        try {
            for (int i = 0; i < mentions.length(); i++) {
                JSONObject mentionObject = mentions.getJSONObject(i);

                JSONArray indicesArray = mentionObject.getJSONArray("indices");


                String screenName = mentionObject.getString("screen_name");
                String name = mentionObject.getString("name");
                int id = mentionObject.getInt("id");
                String idString = mentionObject.getString("id_str");

                int startIndex = indicesArray.getInt(0);
                int endIndex = indicesArray.getInt(1);

                UserMention userMention = new UserMention(
                        screenName, startIndex, endIndex, name, idString, id
                );

                userMentions.add(userMention);

            }
        } catch (JSONException e) {
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

            if (!jsonObject.isNull("profile_location")) {
                String profileLocation = jsonObject.getString("profile_location");
            }

            int statusCount = jsonObject.getInt("statuses_count");
            int followersCount = jsonObject.getInt("followers_count");
            int followingCount = jsonObject.getInt("friends_count");

            boolean isProtected = jsonObject.getBoolean("protected");
            boolean following = jsonObject.getBoolean("following");

            userModel = new User(username, name, imgUrl,
                    location, description, statusCount,
                    followersCount, followingCount,
                    following);

            userModel.setBackgroundUrl(jsonObject.getString("profile_background_image_url_https"));
            userModel.setBannerUrl(jsonObject.getString("profile_banner_url"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userModel;
    }

    public static ArrayList<DirectMessage> parseDMs(JSONObject jsonObject) {
        ArrayList<DirectMessage> dms = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("events");
            for (int i = 0; i < jsonArray.length(); i++) {
                // Get a DMObject
                JSONObject dmObj = jsonArray.getJSONObject(i);


                // Get the DM Content from the DMObject
                JSONObject dmContent = dmObj.getJSONObject("message_create");

                long timestamp = dmObj.getInt("created_timestamp");
                long dmID = dmObj.getInt("id");

                long recipientID = dmContent.getJSONObject("target").getLong("recipient_id");
                long senderID = dmContent.getLong("sender_id");


                String text = dmContent.getJSONObject("message_data").getString("text");

                JSONObject entitiesObj = dmContent.getJSONObject("message_data").getJSONObject("entities");


//                EntitiesHolder entitiesHolder = parseEntities(entitiesObj);
                ArrayList<Entity> entities = parseEntities(entitiesObj);

                DirectMessage dm = new DirectMessage(
                        recipientID, senderID,
                        dmID, timestamp,
                        text);
                dm.setEntities(entities);
                dms.add(dm);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dms;
    }

    public static ArrayList<User> parseUserList(JSONArray jsonArray) {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject userObj = jsonArray.getJSONObject(i);
                User user = parseUser(userObj);
                users.add(user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

}
