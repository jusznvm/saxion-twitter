package com.example.justin.simpletwitter.fragment.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justin.simpletwitter.activity.ErrorActivity;
import com.example.justin.simpletwitter.utils.AppInfo;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.utils.TwitterAPI;
import com.example.justin.simpletwitter.adapter.StatusAdapter;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.utils.JSONParser;
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

/**
 *  Fragment that contains the view that displays a the mentions of the user
 */
public class MyMentionsTimeLine extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private static OAuth10aService service = AppInfo.getService();
    private static AppInfo appInfo = AppInfo.getInstance();

    private ArrayList<Status> statuses;

    public MyMentionsTimeLine() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_timeline, container, false);
        statuses = new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.recycler_user_timeline);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StatusAdapter(statuses, this);
        mRecyclerView.setAdapter(mAdapter);

        // Start the task
        GetMentionsTask task = new GetMentionsTask();
        task.execute();

        return view;
    }

    /**
     * Gets the mentions
     */
    public class GetMentionsTask extends AsyncTask<Void, Void, JSONArray> {

        /**
         * Gets the mentions of the current user.
         * @param voids
         * @return the users mentions.
         */
        @Override
        protected JSONArray doInBackground(Void... voids) {

            OAuthRequest request = new OAuthRequest(Verb.GET, TwitterAPI.STATUSES_USER_MENTIONS);
            OAuth1AccessToken token = appInfo.getAccessToken();
            service.signRequest(token, request);

            try {
                final Response response = service.execute(request);
                if(response.isSuccessful())
                    return new JSONArray(response.getBody());
                else {
                    Intent i = new Intent(getActivity(), ErrorActivity.class);
                    startActivity(i);
                }
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
