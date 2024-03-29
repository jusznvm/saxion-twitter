package com.example.justin.simpletwitter.fragment.menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justin.simpletwitter.utils.AppInfo;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.utils.TwitterAPI;
import com.example.justin.simpletwitter.adapter.DMAdapter;
import com.example.justin.simpletwitter.model.DirectMessage;
import com.example.justin.simpletwitter.utils.JSONParser;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Fragment that contains direct messages
 */
public class DirectMessageFragment extends Fragment {

    public static final String TAG = "DirectMessageFragment";

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    private static OAuth10aService service = AppInfo.getService();
    private static AppInfo appInfo = AppInfo.getInstance();

    private ArrayList<DirectMessage> dms;

    public DirectMessageFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_direct_messages, container, false);
        dms = new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.rv_direct_message_list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DMAdapter(dms, this);
        mRecyclerView.setAdapter(mAdapter);

        GetDirectMessagesTask task = new GetDirectMessagesTask();
        task.execute();
        return view;
    }

    /**
     * Gets the direct messages and loads the into the list
     */
    public class GetDirectMessagesTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {
            //TODO: Not sure if show onProgressUpdated or handle non successfull response code
            OAuthRequest request = new OAuthRequest(Verb.GET, TwitterAPI.DMS_EVENTS_LIST);
            OAuth1AccessToken token = appInfo.getAccessToken();
            service.signRequest(token, request);

            try {
                final Response response = service.execute(request);
                Log.d(TAG, "doInBackground: " + response.getBody());
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
            dms.addAll(JSONParser.parseDMs(json));
            Log.d("DEBUG", "handleResult: dms length = " + dms.size());
            mAdapter.notifyDataSetChanged();
        }
    }
}
