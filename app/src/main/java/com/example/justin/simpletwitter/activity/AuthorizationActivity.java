package com.example.justin.simpletwitter.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.justin.simpletwitter.utils.AppInfo;
import com.example.justin.simpletwitter.R;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class AuthorizationActivity extends AppCompatActivity {

    private WebView webView;
    private AppInfo appInfo = AppInfo.getInstance();

    private String verifier;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authorization);
        sharedPreferences = getApplicationContext().getSharedPreferences("pref", 0);

        if(sharedPreferences.getString("token_key", null) == null) {

            webView = findViewById(R.id.webView);

            GetTokenTask task = new GetTokenTask();
            task.execute();

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    if (url.startsWith("https://www.google.com")) {
                        Uri uri = Uri.parse(url);
                        verifier = uri.toString().substring(79, uri.toString().length());
                        AuthTask authTask = new AuthTask();
                        authTask.execute();
                        return true;
                    }
                    return false;
                }
            });
        } else {
            String key = sharedPreferences.getString("token_key", null);
            String secret = sharedPreferences.getString("token_secret", null);
            OAuth1AccessToken accessToken1 = new OAuth1AccessToken(key, secret);
            appInfo.setAccessToken(accessToken1);
            Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
           startActivity(intent);
        }

    }

    public class GetTokenTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                OAuth1RequestToken token = appInfo.getService().getRequestToken();
                appInfo.setToken(token);
                return appInfo.getService().getAuthorizationUrl(appInfo.getToken());
            } catch (IOException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            webView.loadUrl(s);
        }
    }

    public class AuthTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... aVoid) {
            try {
                OAuth1RequestToken reqToken = appInfo.getToken();

                OAuth1AccessToken accessToken = appInfo.getService().getAccessToken(reqToken, verifier);

                String tokenString = accessToken.getToken();
                String secretString = accessToken.getTokenSecret();

                appInfo.setAccessToken(accessToken);

                editor = sharedPreferences.edit();
                editor.putString("token_key", tokenString);
                editor.putString("token_secret", secretString);

                editor.apply();

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
