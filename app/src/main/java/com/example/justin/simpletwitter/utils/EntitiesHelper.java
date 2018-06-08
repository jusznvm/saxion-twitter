package com.example.justin.simpletwitter.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.justin.simpletwitter.model.EntitiesHolder;
import com.example.justin.simpletwitter.model.Hashtag;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.model.URL;
import com.example.justin.simpletwitter.model.UserMention;

public class EntitiesHelper {


    public static final String TAG = "EntitiesHelper";

    public SpannableString linkifyStatus(Status status){
        EntitiesHolder entitiesHolder = status.getEntities();

        String statusText = status.getText();

        SpannableString ss = new SpannableString(statusText);


        for (Hashtag hashtag: entitiesHolder.getHashtags()) {
            ss.setSpan(new EntitiesHelper.HashtagClickableSpan(), hashtag.getStartIndex(), hashtag.getEndIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        for (UserMention mention: entitiesHolder.getUserMentions()) {
            ss.setSpan(new EntitiesHelper.UserMentionClickableSpan(), mention.getStartIndex(), mention.getEndIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        for (URL url: entitiesHolder.getUrls()) {
            ss.setSpan(new EntitiesHelper.URLClickableSpan(), url.getStartIndex(), url.getEndIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    class URLClickableSpan extends ClickableSpan {

        @Override
        public void onClick(View textView) {
            TextView newView = (TextView) textView;
            Log.d(TAG, "URLClickableSpan, onClick: " + newView.getText().toString());
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(false);
        }
    }

    class HashtagClickableSpan extends ClickableSpan {

        public void onClick(View textView) {
            TextView newView = (TextView) textView;
            Log.d(TAG, "URLClickable, onClick: " + newView.getText().toString());
            //fragment.getFragmentManager().beginTransaction().replace(R.id.activity_content, new DirectMessageFragment()).addToBackStack(null).commit();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.GREEN);
            ds.setUnderlineText(false); // remove underline
        }
    }

    class UserMentionClickableSpan extends ClickableSpan {

        public void onClick(View textView) {
            TextView newView = (TextView) textView;
            Log.d(TAG, "UsermentionClickable, onClick: " + newView.getText().toString());
            //fragment.getFragmentManager().beginTransaction().replace(R.id.activity_content, new DirectMessageFragment()).addToBackStack(null).commit();

        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.RED);
            ds.setUnderlineText(false); // remove underline
        }
    }
}
