package com.example.justin.simpletwitter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.activity.MainActivity;
import com.example.justin.simpletwitter.adapter.TweetAdapter;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.parser.JSONParser;

import java.util.ArrayList;

public class UserTimelineFragment extends Fragment {

    public UserTimelineFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_timeline, container, false);
        ListView lvTimline = view.findViewById(R.id.lv_user_timeline);

        ArrayList<Status> statusses = JSONParser.parseStatus(MainActivity.getJsonObject());

        TweetAdapter tweetAdapter = new TweetAdapter(getActivity(), R.layout.tweet, statusses);
        lvTimline.setAdapter(tweetAdapter);

        return view;
    }
}
