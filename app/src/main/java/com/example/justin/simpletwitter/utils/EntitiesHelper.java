package com.example.justin.simpletwitter.utils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.adapter.DMAdapter;
import com.example.justin.simpletwitter.fragment.home.HashtagSearchFragment;
import com.example.justin.simpletwitter.fragment.home.SearchFragment;
import com.example.justin.simpletwitter.fragment.profile.UserProfileFragment;
import com.example.justin.simpletwitter.model.DirectMessage;
import com.example.justin.simpletwitter.model.User;
import com.example.justin.simpletwitter.model.entity.Entity;
import com.example.justin.simpletwitter.model.entity.Hashtag;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.model.entity.URL;
import com.example.justin.simpletwitter.model.entity.UserMention;

import java.util.ArrayList;

public class EntitiesHelper {


    public static final String TAG = "EntitiesHelper";


    public static SpannableString linkifyStatus(Status status, Fragment fragment) {

        ArrayList<Entity> entitiesList = status.getEntitiesList();
        String statusText = status.getText();

        SpannableString ss = new SpannableString(statusText);

        if (!(entitiesList.size() > 0)){
            return ss;
        }

        for (Entity entity : entitiesList) {
            if (entity instanceof UserMention){
                ss.setSpan(new EntitiesHelper.MyClickableSpan((UserMention)entity, fragment), entity.getStartIndex(), entity.getEndIndex(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }

            if (entity instanceof Hashtag){
                ss.setSpan(new EntitiesHelper.MyClickableSpan((Hashtag)entity, fragment), entity.getStartIndex(), entity.getEndIndex(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }

            if (entity instanceof URL){
                ss.setSpan(new EntitiesHelper.MyClickableSpan((URL)entity, fragment), entity.getStartIndex(), entity.getEndIndex(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return ss;
    }

    public static SpannableString linkifyDM(DirectMessage dm) {




        ArrayList<Entity> entitiesList = dm.getEntities();
        String statusText = dm.getText();

        SpannableString ss = new SpannableString(statusText);

        if (!(entitiesList.size() > 0)){
            return ss;
        }

        for (Entity entity : entitiesList) {
            if (entity instanceof UserMention){
                ss.setSpan(new EntitiesHelper.MyClickableSpan((UserMention)entity, null), entity.getStartIndex(), entity.getEndIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (entity instanceof Hashtag){
                ss.setSpan(new EntitiesHelper.MyClickableSpan((Hashtag)entity, null), entity.getStartIndex(), entity.getEndIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (entity instanceof URL){
                ss.setSpan(new EntitiesHelper.MyClickableSpan((URL)entity, null), entity.getStartIndex(), entity.getEndIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }


        return ss;
    }


    /**
     * Inner classes
     */
    static class MyClickableSpan extends ClickableSpan {
        Entity entity = null;
        Fragment fragment = null;


        public MyClickableSpan(UserMention userMentionEntity, Fragment fragment) {

            this.entity = userMentionEntity;
            if (fragment != null){
                this.fragment = fragment;
            }
            this.fragment = fragment;
        }

        public MyClickableSpan(Hashtag hashtagEntity, Fragment fragment) {

            this.entity = hashtagEntity;
            if (fragment != null){
                this.fragment = fragment;
            }
        }

        public MyClickableSpan(URL urlEntity, Fragment fragment) {

            this.entity = urlEntity;
            if (fragment != null){
                this.fragment = fragment;
            }
        }

        @Override
        public void onClick(View textView) {
            TextView newView = (TextView) textView;
            doAction();
            Log.d(TAG, "Entity clicked, ClickableSpan, onClick(View content): " + newView.getText().toString());
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(false);
        }

        public void doAction(){
            Fragment frag = null;
            Log.d(TAG, "onClick: tvScreenName called");
            Bundle bundle = new Bundle();

            Log.d(TAG, "doAction: entity type: " + entity.getType());
            if (entity.getType().equals("UserMention")){
                frag = new UserProfileFragment();
                bundle.putString("name", entity.getText());
                frag.setArguments(bundle);
            }
            else if (entity.getType().equals("Hashtag")){
                frag = new HashtagSearchFragment();
                bundle.putString("text", entity.getText());
                frag.setArguments(bundle);
            }

            else if (entity.getType().equals("URL")){

            }
            else{
                return;
            }
            fragment.getFragmentManager().beginTransaction().replace(R.id.activity_content, frag).addToBackStack(null).commit();
        }
    }
}
