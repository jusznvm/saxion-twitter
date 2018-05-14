package com.example.justin.simpletwitter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Justin on 5/9/2018.
 */

public class TweetAdapter extends ArrayAdapter<Status> {

    private ArrayList<Status> statuses;

    public TweetAdapter(@NonNull Context context, int resource, @NonNull List<Status> objects) {
        super(context, resource, objects);
        statuses = (ArrayList<Status>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet, parent, false);
        }

        // Get status with the position & get the user of the status too.
        Status status = statuses.get(position);
        User user = status.getUser();

        // Declare and init all the UI Components
        TextView tvText = convertView.findViewById(R.id.tv_tweet_text);
        TextView tvUsername = convertView.findViewById(R.id.tv_user_name);
        TextView tvScreenname = convertView.findViewById(R.id.tv_user_screen_name);

        ImageView ivAvatar = convertView.findViewById(R.id.iv_user_avatar);

        Button btnTest = convertView.findViewById(R.id.btn_test);

        // Protect ya neck
            // For tweet
        String favoriteCount = String.valueOf(status.getFavoriteCount());

            // For user
        String screenName = "@" + user.getUserName();
        String imgUrl= user.getImgUrl();


        // Set all the values accordingly
        tvText.setText(status.getText());
        tvScreenname.setText(screenName);
        tvUsername.setText(user.getName());

        btnTest.setText(favoriteCount);

        // Crop and set profile avatar
        Picasso.get().load(imgUrl).resize(30,30).centerCrop().into(ivAvatar);


        return convertView;
    }
}
