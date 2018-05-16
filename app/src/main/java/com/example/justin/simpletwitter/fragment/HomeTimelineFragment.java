package com.example.justin.simpletwitter.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justin.simpletwitter.AppInfo;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.TwitterAPI;
import com.example.justin.simpletwitter.adapter.StatusAdapter;
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

    private ArrayList<Status> statuses;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static OAuth10aService service = AppInfo.getService();

    private static AppInfo appInfo = AppInfo.getInstance();


    public HomeTimelineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        statuses = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_home_timeline, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_home_timeline);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StatusAdapter(statuses);
        mRecyclerView.setAdapter(mAdapter);

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
            OAuth1AccessToken token = appInfo.getAccessToken();
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
            mAdapter.notifyDataSetChanged();
        }
    }


}
