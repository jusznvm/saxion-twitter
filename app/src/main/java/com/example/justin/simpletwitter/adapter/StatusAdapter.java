package com.example.justin.simpletwitter.adapter;

import android.graphics.Color;
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
import com.example.justin.simpletwitter.fragment.HomeTimelineFragment;
import com.example.justin.simpletwitter.model.Entity;
import com.example.justin.simpletwitter.model.Hashtag;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {

    private static final String TAG = "StatusAdapter";
    private ArrayList<Status> statuses;
    private Fragment fragment;

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

        //Used to match Hashtag Entity for Linkifying

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
        holder.tvText.setText(linkifyTweet(status));
        holder.tvText.setMovementMethod(LinkMovementMethod.getInstance());




        holder.tvScreenname.setText(screenName);
        holder.tvUsername.setText(user.getName());

        holder.btnFav.setText(favoriteCount);
        holder.btnRetweet.setText(retweetCount);

        // Crop and set profile avatar
        Picasso.get().load(imgUrl).into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }





    public SpannableString linkifyTweet(Status status){
        ArrayList<Entity> entitiesInStatus = status.getstatusEntities();

        String statusText = status.getText();

        SpannableString ss = new SpannableString(statusText);

        for (Entity entity: entitiesInStatus) {
            ss.setSpan(new MyClickableSpan(), entity.getStartIndex(), entity.getEndIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        return ss;
    }


    class MyClickableSpan extends ClickableSpan{


        public void onClick(View textView) {
            TextView newView = (TextView) textView;


            Log.d(TAG, "MyClickableSpan, onClick: " + newView.getText().toString());
            fragment.getFragmentManager().beginTransaction().replace(R.id.activity_content, new DirectMessageFragment()).addToBackStack(null).commit();

        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.GREEN);
            ds.setUnderlineText(false); // remove underline
        }
    }

}
