package com.example.justin.simpletwitter.fragment.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.justin.simpletwitter.AppInfo;
import com.example.justin.simpletwitter.R;
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
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class SearchFragment extends Fragment{

    private ArrayList<Status> statuses;

    private EditText etSearch;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    private static OAuth10aService service = AppInfo.getService();

    private static AppInfo appInfo = AppInfo.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        statuses = new ArrayList<>();

        etSearch = view.findViewById(R.id.et_search);

        mRecyclerView = view.findViewById(R.id.rv_search_fragment);
        mAdapter = new StatusAdapter(statuses, this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    class SearchTask extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... voids) {

            String url = "";

            OAuthRequest request = new OAuthRequest(Verb.GET, url);

            OAuth1AccessToken token = appInfo.getAccessToken();
            service.signRequest(token, request);

            try {
                Response response = service.execute(request);
                JSONObject jsonObject = new JSONObject(response.getBody());
                return new JSONArray(jsonObject.getJSONArray("statuses"));
            } catch (InterruptedException | ExecutionException | IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            statuses.addAll(JSONParser.parseStatus(jsonArray));
            mAdapter.notifyDataSetChanged();
        }
    }
}
