package com.example.justin.simpletwitter.fragment.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.adapter.StatusAdapter;
import com.example.justin.simpletwitter.model.Status;
import com.example.justin.simpletwitter.utils.AppInfo;
import com.example.justin.simpletwitter.utils.JSONParser;
import com.example.justin.simpletwitter.utils.TwitterAPI;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by yunus on 6/20/2018.
 */

public class HashtagSearchFragment extends Fragment {

    public static final String TAG = "HashtagSearchFragment";

    private ArrayList<Status> statuses;

    private EditText etSearch;

    private static OAuth10aService service = AppInfo.getService();

    private static AppInfo appInfo = AppInfo.getInstance();

    private RecyclerView.Adapter statusAdapter;
    private RecyclerView.LayoutManager statusLayoutManager;
    private RecyclerView statusRecyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hashtag_search, container, false);

        statuses = new ArrayList<>();

        etSearch = view.findViewById(R.id.hashtag_et_search);

        SearchTweetTask task = new SearchTweetTask();
        task.execute(getArguments().getString("text"));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0){
                    SearchTweetTask task = new SearchTweetTask();
                    task.execute("#"+ editable.toString());
                }
            }
        });

        statusRecyclerView = view.findViewById(R.id.hashtag_rv_search_tweets);
        statusAdapter = new StatusAdapter(statuses, this);
        statusLayoutManager = new LinearLayoutManager(getActivity());
        statusRecyclerView.setLayoutManager(statusLayoutManager);
        statusRecyclerView.setAdapter(statusAdapter);



        return view;
    }


    class SearchTweetTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            statuses.clear();
        }

        @Override
        protected JSONArray doInBackground(String... strings) {

            try {
                String encoded = URLEncoder.encode(strings[0], "UTF-8");

                String url = TwitterAPI.SEARCH_TWEET + encoded;

                OAuthRequest request = new OAuthRequest(Verb.GET, url);

                OAuth1AccessToken token = appInfo.getAccessToken();
                service.signRequest(token, request);

                Response response = service.execute(request);
                if(response.isSuccessful()) {
                    JSONObject jsonObject = new JSONObject(response.getBody());
                    return jsonObject.getJSONArray("statuses");
                } else {
                    // TODO: nice way to show no results
                }
            } catch (InterruptedException | ExecutionException | IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            statuses.addAll(JSONParser.parseStatus(jsonArray));
            statusAdapter.notifyDataSetChanged();
        }
    }





}
