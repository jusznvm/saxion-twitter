package com.example.justin.simpletwitter.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.fragment.home.SearchFragment;
import com.example.justin.simpletwitter.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private ArrayList<User> users;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAvatar;
        private TextView tvName, tvScreenName;

        public ViewHolder(View itemView) {
            super(itemView);
           // ivAvatar = itemView.findViewById(R.id.iv_avatar_user_search_result);
           // tvName = itemView.findViewById(R.id.tv_name_user_search_result);
            tvScreenName = itemView.findViewById(R.id.list_item_user_name);
        }
    }

    public UserAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search_result_user, parent);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User user = users.get(holder.getAdapterPosition());

        //String name = user.getName();
        String screenName = "@" + user.getUserName();
        holder.tvScreenName.setText(screenName);
        //holder.tvName.setText(name);

        //Picasso.get().load(user.getImgUrl()).into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
