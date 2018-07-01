package com.example.justin.simpletwitter.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.fragment.home.SearchFragment;
import com.example.justin.simpletwitter.fragment.profile.UserProfileFragment;
import com.example.justin.simpletwitter.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 *  RecyclerView for displaying users
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private ArrayList<User> users;
    private Fragment fragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAvatar;
        private TextView tvName, tvScreenName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvScreenName = itemView.findViewById(R.id.tv_search_result_user_name);
            tvName = itemView.findViewById(R.id.tv_search_result_name);
            ivAvatar = itemView.findViewById(R.id.iv_search_result_avatar);
        }
    }

    public UserAdapter(ArrayList<User> users, Fragment fragment) {
        this.users = users;
        this.fragment = fragment;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User user = users.get(holder.getAdapterPosition());

        String name = user.getName();
        String screenName = "@" + user.getUserName();
        holder.tvScreenName.setText(screenName);
        holder.tvName.setText(name);
        Picasso.get().load(user.getImgUrl()).into(holder.ivAvatar);

        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", user.getUserName());
                UserProfileFragment f = new UserProfileFragment();
                f.setArguments(bundle);
                fragment.getFragmentManager().beginTransaction().replace(R.id.activity_content, f).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
