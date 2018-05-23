package com.example.justin.simpletwitter.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
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
import com.example.justin.simpletwitter.model.DirectMessage;
import com.example.justin.simpletwitter.model.EntitiesHolder;
import com.example.justin.simpletwitter.model.Hashtag;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.model.User;
import com.example.justin.simpletwitter.model.UserMention;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DMAdapter extends RecyclerView.Adapter<DMAdapter.ViewHolder> {

    private static final String TAG = "DMAdapted";
    private Fragment fragment;
    private ArrayList<DirectMessage> dms;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername, tvName, tvContent, tvDate;
        private ImageView ivAvatar;


        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_dm_list_username);
            tvName = itemView.findViewById(R.id.tv_dm_list_name);
            tvContent = itemView.findViewById(R.id.tv_dm_list_content);
            ivAvatar = itemView.findViewById(R.id.iv_dm_list_avatar);
            //TODO: Fix date in parser and model class
        }
    }

    public DMAdapter(ArrayList<DirectMessage> dms) {
        this.dms = dms;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.direct_message_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DirectMessage dm = dms.get(position);
        holder.tvContent.setText(linkifyDM(dm));
    }

    @Override
    public int getItemCount() {
        Log.d("DEBUG", "getItemCount: " + dms.size());
        for(DirectMessage dm : dms) {
            Log.d("DEBUG", "Item Text : " + dm.getText());
        }
        return dms.size();
    }

    public SpannableString linkifyDM(DirectMessage dm){
        EntitiesHolder entitiesHolder = dm.getEntitiesHolder();

        String statusText = dm.getText();

        SpannableString ss = new SpannableString(statusText);

        for (Hashtag hashtag: entitiesHolder.getHashtags()) {
            ss.setSpan(new HashtagClickableSpan(), hashtag.getStartIndex(), hashtag.getEndIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        for (UserMention mention: entitiesHolder.getUserMentions()) {
            ss.setSpan(new UserMentionClickableSpan(), mention.getStartIndex(), mention.getStartIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        return ss;
    }

    class HashtagClickableSpan extends ClickableSpan {


        public void onClick(View textView) {
            TextView newView = (TextView) textView;


            Log.d(TAG, "MyClickableSpan, onClick: " + newView.getText().toString());
            //fragment.getFragmentManager().beginTransaction().replace(R.id.activity_content, new HomeTimelineFragment()).addToBackStack(null).commit();

        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.GREEN);
            ds.setUnderlineText(false); // remove underline
        }
    }

    class UserMentionClickableSpan extends ClickableSpan{
        public void onClick(View textView) {
            TextView newView = (TextView) textView;
            Log.d(TAG, "MyClickableSpan, onClick: " + newView.getText().toString());
            //fragment.getFragmentManager().beginTransaction().replace(R.id.activity_content, new DirectMessageFragment()).addToBackStack(null).commit();

        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.RED);
            ds.setUnderlineText(false); // remove underline
        }



    }


}
