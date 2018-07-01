package com.example.justin.simpletwitter.fragment.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.justin.simpletwitter.adapter.StatusAdapter;
import com.example.justin.simpletwitter.adapter.UserAdapter;
import com.example.justin.simpletwitter.model.User;
import com.example.justin.simpletwitter.utils.AppInfo;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.model.Status;
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
 * Fragment that represents the search functionality.
 */
public class SearchFragment extends Fragment{

    public static final String TAG = "Dodging yunus' shit";

    private ArrayList<Status> statuses;
    private ArrayList<User> users;

    private EditText etSearch;

    private RecyclerView.LayoutManager userLayoutManager, statusLayoutManager;
    private RecyclerView.Adapter userAdapter, statusAdapter;
    private RecyclerView userRecyclerView, statusRecyclerView;

    private static OAuth10aService service = AppInfo.getService();

    private static AppInfo appInfo = AppInfo.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        statuses = new ArrayList<>();
        users = new ArrayList<>();

        // Listens for text input.
        etSearch = view.findViewById(R.id.et_search);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Log.d(TAG, "onEditorAction: Return button was pressed");
                String s = etSearch.getText().toString();
                Log.d(TAG, "Query = " + s);
                SearchTweetTask task = new SearchTweetTask();
                task.execute(s);
                SearchUserTask task1 = new SearchUserTask();
                task1.execute(s);
                Log.d(TAG, "onEditorAction: Task is Executed");
                return false;
            }
        });

        statusRecyclerView = view.findViewById(R.id.rv_search_tweets);
        statusAdapter = new StatusAdapter(statuses, this);
        statusLayoutManager = new LinearLayoutManager(getActivity());
        statusRecyclerView.setLayoutManager(statusLayoutManager);
        statusRecyclerView.setAdapter(statusAdapter);

        userRecyclerView = view.findViewById(R.id.rv_search_user);
        userAdapter = new UserAdapter(users, this);
        userLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        userRecyclerView.setLayoutManager(userLayoutManager);
        userRecyclerView.setAdapter(userAdapter);

        return view;
    }

    /**
     * Task for searching a specific status.
     */
    class SearchTweetTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            statuses.clear();
        }

        /**
         * Gets the statusses conform to the query.
         * @param strings
         * @return the corresponding statusses.
         */
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

        /**
         * Update the list and show results
         * @param jsonArray
         */
        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            statuses.addAll(JSONParser.parseStatus(jsonArray));
            statusAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Task that searches for users
     */
    class SearchUserTask extends AsyncTask<String, Void, JSONArray> {

        /**
         * Searches for users conform to the query
         * @param strings
         * @return users
         */
        @Override
        protected JSONArray doInBackground(String... strings) {

            try {
                String encoded = URLEncoder.encode(strings[0], "UTF-8");

                String url = TwitterAPI.SEARCH_USER + encoded;

                OAuthRequest request = new OAuthRequest(Verb.GET, url);

                OAuth1AccessToken token = appInfo.getAccessToken();
                service.signRequest(token, request);

                Response response = service.execute(request);
                Log.d(TAG, "Response = " + response.getBody());
                if(response.isSuccessful())
                    return new JSONArray(response.getBody());
                else {
                    // TODO: SOMETHING PRETTY FOR NO RESULTS
                }
            } catch (InterruptedException | ExecutionException | IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Update the list and show users.
         * @param jsonArray
         */
        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            users.addAll(JSONParser.parseUserList(jsonArray));
            userAdapter.notifyDataSetChanged();
        }
    }
}
