package com.example.justin.simpletwitter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.adapter.TweetAdapter;
import com.example.justin.simpletwitter.activity.MainActivity;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.parser.JSONParser;

import org.json.JSONObject;

import java.util.ArrayList;
public class HomeTimelineFragment extends Fragment {


    private static final String TAG = "cancer";
    private ListView lvTweets;
    private TweetAdapter tweetAdapter;
    private ArrayList<Status> statuses;

    private String tabTitle;

    public static final String TITLE_KEY = "title";



    public HomeTimelineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_timeline, container, false);
        JSONObject json = MainActivity.getJsonObject();
        statuses = JSONParser.parseStatus(json);
        lvTweets = view.findViewById(R.id.lv_home_timeline);
        tweetAdapter = new TweetAdapter(getActivity(), R.layout.tweet, statuses);
        lvTweets.setAdapter(tweetAdapter);
        return view;
    }


}
