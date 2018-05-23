package com.example.justin.simpletwitter.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.fragment.DirectMessageFragment;
import com.example.justin.simpletwitter.fragment.profile.UserProfileFragment;
import com.example.justin.simpletwitter.model.EntitiesHolder;
import com.example.justin.simpletwitter.model.Hashtag;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.model.User;
import com.example.justin.simpletwitter.model.UserMention;
import com.example.justin.simpletwitter.parser.JSONParser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {

    private static final String TAG = "StatusAdapter";
    private ArrayList<Status> statuses;
    private Fragment fragment;

    private Context context;

    public static final String SCREENNAME_KEY = "screenname";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvText;
        private TextView tvUsername;
        private TextView tvScreenname;

        private ImageView ivAvatar;

        private Button btnFav, btnReply, btnRetweet;

        public ViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_tweet_text);
            tvUsername = itemView.findViewById(R.id.tv_user_name);
            tvScreenname = itemView.findViewById(R.id.tv_user_screen_name);

            ivAvatar = itemView.findViewById(R.id.iv_user_avatar);

            btnFav = itemView.findViewById(R.id.btn_fav_tweet);
            btnRetweet = itemView.findViewById(R.id.btn_retweet);
            btnReply = itemView.findViewById(R.id.btn_reply);
        }
    }

    public StatusAdapter(ArrayList<Status> statuses, Fragment fragment) {
        this.statuses = statuses;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Get status with the position & get the user of the status too.
        Status status = statuses.get(position);
        User user = status.getUser();

        // Declare and init all the UI Components

        // Protect ya neck
            // For tweet
        String favoriteCount = String.valueOf(status.getFavoriteCount());
        String retweetCount = String.valueOf(status.getRetweetCount());

            // For user
        String screenName = "@" + user.getUserName();
        String imgUrl= user.getImgUrl();

        // Set all the values accordingly
        holder.tvText.setText(linkifyStatus(status));
        holder.tvText.setMovementMethod(LinkMovementMethod.getInstance());

        holder.tvScreenname.setText(screenName);
        holder.tvUsername.setText(user.getName());

        holder.btnFav.setText(favoriteCount);
        holder.btnRetweet.setText(retweetCount);

        // Crop and set profile avatar
        Picasso.get().load(imgUrl).into(holder.ivAvatar);
        holder.tvScreenname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: tvScreenName called");
                Bundle bundle = new Bundle();
                bundle.putString("name", user.getUserName());
                UserProfileFragment f = new UserProfileFragment();
                f.setArguments(bundle);
                fragment.getFragmentManager().beginTransaction().replace(R.id.activity_content, f).addToBackStack(null).commit();
            }
        });

        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", user.getUserName());
                Log.d(TAG, "onClick: value = " + user.getUserName());
                UserProfileFragment f = new UserProfileFragment();
                f.setArguments(bundle);
                fragment.getFragmentManager().beginTransaction().replace(R.id.activity_content, f).addToBackStack(null).commit();            }
        });
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    public SpannableString linkifyStatus(Status status){
        EntitiesHolder entitiesHolder = status.getEntities();

        String statusText = status.getText();

        SpannableString ss = new SpannableString(statusText);


        for (Hashtag hashtag: entitiesHolder.getHashtags()) {
            ss.setSpan(new HashtagClickableSpan(), hashtag.getStartIndex(), hashtag.getEndIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        for (UserMention mention: entitiesHolder.getUserMentions()) {
            ss.setSpan(new UserMentionClickableSpan(), mention.getStartIndex(), mention.getEndIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return ss;
    }




    class HashtagClickableSpan extends ClickableSpan {

        public void onClick(View textView) {
            TextView newView = (TextView) textView;
            Log.d(TAG, "MyClickableSpan, onClick: " + newView.getText().toString());
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
