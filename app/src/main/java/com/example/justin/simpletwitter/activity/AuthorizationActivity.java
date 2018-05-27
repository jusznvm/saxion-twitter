package com.example.justin.simpletwitter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.justin.simpletwitter.AppInfo;
import com.example.justin.simpletwitter.R;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class AuthorizationActivity extends AppCompatActivity {

    private WebView webView;
    private AppInfo appInfo = AppInfo.getInstance();

    private String verifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        webView = findViewById(R.id.webView);


        GetTokenTask task = new GetTokenTask();
        task.execute();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(url.startsWith("https://www.google.com")) {
                    Uri uri = Uri.parse(url);
                    verifier = uri.toString().substring(79, uri.toString().length());
                    AuthTask authTask = new AuthTask();
                    authTask.execute();
                }
                return false;
            }
        });
    }

    /**
     * getAuthorizationUrl netwerkverkeer?
     * loadUrl netwerkverkeer?
     */
    public class GetTokenTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                OAuth1RequestToken token = appInfo.getService().getRequestToken();
                appInfo.setToken(token);
            } catch (IOException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            webView.loadUrl(appInfo.getService().getAuthorizationUrl(appInfo.getToken()));
        }

    }

    public class AuthTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... aVoid) {
            try {
                OAuth1RequestToken reqToken = appInfo.getToken();
                OAuth1AccessToken accessToken = appInfo.getService().getAccessToken(reqToken, verifier);
                appInfo.setAccessToken(accessToken);
            } catch (IOException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
