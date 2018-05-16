package com.example.justin.simpletwitter.fragment;


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

import com.example.justin.simpletwitter.AppInfo;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.TwitterAPI;
import com.example.justin.simpletwitter.adapter.TweetAdapter;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.parser.JSONParser;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeTimelineFragment extends Fragment {

    private ListView lvTweets;
    private TweetAdapter tweetAdapter;
    private ArrayList<Status> statuses;

    private static OAuth10aService service = AppInfo.getService();

    public HomeTimelineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        statuses = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_home_timeline, container, false);
        lvTweets = view.findViewById(R.id.lv_home_timeline);
        tweetAdapter = new TweetAdapter(getActivity(), statuses);
        lvTweets.setAdapter(tweetAdapter);


        HomeTimeLineTask task = new HomeTimeLineTask();
        task.execute();

        return view;
    }

    class HomeTimeLineTask extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(Void... aVoid) {
            OAuthRequest request = new OAuthRequest(Verb.GET, TwitterAPI.STATUSES_HOME_TIMELINE);
            OAuth1AccessToken token = AppInfo.getInstance().getAccessToken();
            service.signRequest(token, request);

            try {
                final Response response = service.execute(request);
                return new JSONArray(response.getBody());
            } catch (InterruptedException | ExecutionException | IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            handleResult(jsonArray);
        }

        public void handleResult(JSONArray json) {
            statuses.addAll(JSONParser.parseStatus(json));
            tweetAdapter.notifyDataSetChanged();
        }
    }


}
