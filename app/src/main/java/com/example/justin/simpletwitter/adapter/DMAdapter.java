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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.model.DirectMessage;
import com.example.justin.simpletwitter.model.entity.Entity;
import com.example.justin.simpletwitter.model.entity.Hashtag;
import com.example.justin.simpletwitter.model.entity.UserMention;
import com.example.justin.simpletwitter.utils.EntitiesHelper;

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

    public DMAdapter(ArrayList<DirectMessage> dms, Fragment fragment) {
        this.dms = dms;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.direct_message_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DirectMessage dm = dms.get(position);
        holder.tvContent.setText(new EntitiesHelper().linkifyDM(dm, fragment));
        holder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public int getItemCount() {
        Log.d("DEBUG", "getItemCount: " + dms.size());
        for(DirectMessage dm : dms) {
            Log.d("DEBUG", "Item Text : " + dm.getText());
        }
        return dms.size();
    }
}
