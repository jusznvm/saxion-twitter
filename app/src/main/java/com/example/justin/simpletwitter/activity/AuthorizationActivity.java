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
                    Log.d("XOXO", " uri = " + uri);

                    String token = uri.toString();
                    Log.d("URI  ", "Length = " + token.length()); // 111
                    String x = "https://www.google.com/?oauth_token=shXIgAAAAAAA6BCQAAABY2mmd98&oauth_verifier=";
                    Log.d("VERIFIER", "shouldOverrideUrlLoading: " + x.length());


                    verifier = uri.toString().substring(79, uri.toString().length());
                    Log.d("VERIFIER ",  " X = " + verifier);
                    AuthTask authTask = new AuthTask();
                    authTask.execute();
                }
                return false;
            }
        });

    }

    public class GetTokenTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                OAuth1RequestToken token = appInfo.getService().getRequestToken();
                appInfo.setToken(token);
                Log.d("TING", "doInBackground: ");

            } catch (IOException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String ting = appInfo.getService().getAuthorizationUrl(appInfo.getToken());
            webView.loadUrl(ting);
        }

    }

    public class AuthTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... aVoid) {
            try {
                    OAuth1RequestToken reqToken = appInfo.getToken();
                Log.d("YUNUS IS EEN EZEL", "REQTOKEN: " + reqToken.toString());
                Log.d("YUNUS IS EEN EZEL", "VERIFIER: " + verifier);


                OAuth1AccessToken accessToken = appInfo.getService().getAccessToken(reqToken, verifier);
                    appInfo.setAccessToken(accessToken);
                    String x = accessToken.toString();
                    if(appInfo.getInstance().getAccessToken() != null) {
                        Log.d("YUNUS IS EEN EZEL", "doInBackground: " + x);
                    }
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
