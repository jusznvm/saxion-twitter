package com.example.justin.simpletwitter.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.activity.AuthorizationActivity;
import com.example.justin.simpletwitter.adapter.StatusAdapter;
import com.example.justin.simpletwitter.fragment.home.HashtagSearchFragment;
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
 * Created by Yunus on 26-6-2018.
 */

public class URLFragment extends Fragment {


    public static final String TAG = "URLFragment";

    private WebView webView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.url_activity, container, false);

        webView = view.findViewById(R.id.url_wv);

        Log.d(TAG, "onCreateView: url: " + getArguments().getString("url"));
        webView.loadUrl(getArguments().getString("url"));


        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        return view;
    }
}
