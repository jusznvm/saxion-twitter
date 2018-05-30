package com.example.justin.simpletwitter.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justin.simpletwitter.utils.AppInfo;
import com.example.justin.simpletwitter.utils.EntitiesHelper;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.utils.TwitterAPI;
import com.example.justin.simpletwitter.fragment.profile.UserProfileFragment;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.model.User;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {

    private static final String TAG = "StatusAdapter";
    private ArrayList<Status> statuses;
    private Fragment fragment;

    private static OAuth10aService service = AppInfo.getService();

    private static AppInfo appInfo = AppInfo.getInstance();

    private Context context;

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

        Status status = statuses.get(holder.getAdapterPosition());
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
        holder.tvText.setText(new EntitiesHelper().linkifyStatus(status));
        holder.tvText.setMovementMethod(LinkMovementMethod.getInstance());

        holder.tvScreenname.setText(screenName);
        holder.tvUsername.setText(user.getName());


        /**
         * Basically handles everything
         * for the button and the icon
         * of the 'favorite' tweet.
         */
        holder.btnFav.setText(favoriteCount);
        if(status.isFavorited()) {
            holder.btnFav.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_1_red, 0, 0, 0);
        }
        else if (!(status.isFavorited())){
            holder.btnFav.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_1, 0, 0, 0);
        }
        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteTask task = new FavoriteTask();
                task.execute(status);
            }
        });

        /**
         * Basically handles everything
         * for the button and the icon
         * of the 'retweet' tweet.
         */
        holder.btnRetweet.setText(retweetCount);
        if(status.isRetweeted()) {
            holder.btnRetweet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.retweeted, 0, 0, 0);
        }
        else if (!(status.isRetweeted())){
            holder.btnRetweet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_retweet, 0, 0, 0);
        }
        holder.btnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetweetTask task = new RetweetTask();
                task.execute(status);
                }
        });

        // Crop and set profile avatar
        Picasso.get().load(imgUrl).into(holder.ivAvatar);

        // TODO: Change the listeners to something more generic
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

   /*
    AsyncTasks
    */
    class FavoriteTask extends AsyncTask<Status, Void, Void> {

        @Override
        protected Void doInBackground(com.example.justin.simpletwitter.model.Status... s) {
            // Clashes with AsyncTask Status ......
            com.example.justin.simpletwitter.model.Status status = s[0];
            // ^ Fk u.

            int tweetID = status.getTweetID();
            String url;
            int newCount = 0;
            Log.d(TAG, "doInBackground: favorited = " + status.isFavorited());

            if(status.isFavorited()) {
                Log.d(TAG, "FavTask: doInBackground: in IF");
                url = TwitterAPI.FAVORITE_STATUS_DESTROY + tweetID;
                Log.d(TAG, "doInBackground: url = " + url);

                newCount = status.getFavoriteCount() - 1;
                if (newCount < 0){
                    newCount = 0;
                }
                status.setFavoriteCount(newCount);
                status.setFavorited(false);
            } else {
                Log.d(TAG, "doInBackground: in ELSE");
                url = TwitterAPI.FAVORITE_STATUS_CREATE + tweetID;
                Log.d(TAG, "doInBackground: status = " + url);
                newCount = status.getFavoriteCount() + 1;
                status.setFavoriteCount(newCount);
                status.setFavorited(true);
            }
            Log.d(TAG, "doInBackground FavTask: url = " + url);
            OAuthRequest request = new OAuthRequest(Verb.POST, url);
            service.signRequest(AppInfo.getAccessToken(), request);

            try {
                service.execute(request);
            } catch (InterruptedException | ExecutionException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notifyDataSetChanged();
        }
    }

    class RetweetTask extends AsyncTask<Status, Void, Void> {

//        @Override
//        protected Void doInBackground(Integer... integers) {
//            // Clashes with AsyncTask Status ......
//            com.example.justin.simpletwitter.model.Status status = statuses.get(integers[0]);
//            // ^ Fk u.
//
//            int tweetID = status.getTweetID();
//            String url = "";
//
//            if(status.isRetweeted()) {
//                url = TwitterAPI.UNRETWEET_STATUS + tweetID + ".json";
//                Log.d(TAG, "doInBackground: status = " + status.isRetweeted());
//            } else {
//                url = TwitterAPI.RETWEET_STATUS + tweetID + ".json";
//                Log.d(TAG, "doInBackground: status = " + status.isRetweeted());
//            }
//            try {
//                OAuthRequest request = new OAuthRequest(Verb.POST, url);
//                service.signRequest(AppInfo.getAccessToken(), request);
//                service.execute(request);
//            } catch (InterruptedException | ExecutionException | IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }

        @Override
        protected Void doInBackground(com.example.justin.simpletwitter.model.Status... statuses) {
            // Clashes with AsyncTask Status ......
            com.example.justin.simpletwitter.model.Status status = statuses[0];
            // ^ Fk u.

            int tweetID = status.getTweetID();
            String url = "";
            int newCount;
            Log.d(TAG, "doInBackground: retweeted = " + status.isRetweeted());


            if(status.isRetweeted()) {
                Log.d(TAG, "RTTask: doInBackground: in IF");
                url = TwitterAPI.UNRETWEET_STATUS + tweetID + ".json";
                Log.d(TAG, "doInBackground: status = " + status.isRetweeted());

                newCount = status.getRetweetCount() - 1;
                if (newCount < 0){
                    newCount = 0;
                }

                status.setRetweetCount(newCount);
                status.setRetweeted(false);
            } else {
                url = TwitterAPI.RETWEET_STATUS + tweetID + ".json";
                Log.d(TAG, "doInBackground: status = " + status.isRetweeted());

                newCount = status.getRetweetCount() + 1;
                status.setRetweetCount(newCount);
                status.setRetweeted(true);
            }

            Log.d(TAG, "doInBackground RTTask: url: " + url);
            OAuthRequest request = new OAuthRequest(Verb.POST, url);
            service.signRequest(AppInfo.getAccessToken(), request);

            try {
                service.execute(request);
            } catch (InterruptedException | ExecutionException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notifyDataSetChanged();
        }
    }

}
