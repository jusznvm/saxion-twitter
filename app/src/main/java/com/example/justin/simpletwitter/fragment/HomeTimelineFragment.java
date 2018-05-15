package com.example.justin.simpletwitter.fragment;


import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.justin.simpletwitter.InfoApi;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.adapter.TweetAdapter;
import com.example.justin.simpletwitter.activity.MainActivity;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.parser.JSONParser;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeTimelineFragment extends Fragment {

    private ListView lvTweets;
    private TweetAdapter tweetAdapter;
    private ArrayList<Status> statuses;
    private TextView tvTest;

    private OAuth10aService service = InfoApi.getService();
    public static final String URL = "http://goedkopeserver.vanruud.nl/tweets.json";

    public HomeTimelineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        statuses = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_home_timeline, container, false);
        tvTest = view.findViewById(R.id.tv_test);
        lvTweets = view.findViewById(R.id.lv_home_timeline);
        tweetAdapter = new TweetAdapter(getActivity(), R.layout.tweet, statuses);
        lvTweets.setAdapter(tweetAdapter);

        HomeTimeLineTask task = new HomeTimeLineTask();
        task.execute();

        return view;
    }

    class HomeTimeLineTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Void... aVoid) {
            OAuthRequest request = new OAuthRequest(Verb.GET, URL);

            try {
                final Response response = service.execute(request);
                return new JSONObject(response.getBody());
            } catch (InterruptedException | ExecutionException | IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            handleResult(jsonObject);
        }

        public void handleResult(JSONObject json) {
            statuses.addAll(JSONParser.parseStatus(json));
            tweetAdapter.notifyDataSetChanged();
        }
    }


}
