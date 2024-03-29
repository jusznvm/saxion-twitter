package com.example.justin.simpletwitter.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    public static final String TAG = "AuthorizationActivity";

    /** Webview to catch the callback url */
    private WebView webView;
    private AppInfo appInfo = AppInfo.getInstance();

    private String verifier;

    /** SharedPreferences is used here to save and check the users key locally. */
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authorization);
        sharedPreferences = getApplicationContext().getSharedPreferences("pref", 0);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        } else {
            startActivityContent();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityContent();
                } else {
                    Intent i = new Intent(AuthorizationActivity.this, ErrorActivity.class);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void startActivityContent() {

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

    /**
     * This class is used to request a request token from the API
     * and retrieving an authorisation url.
     */
    public class GetTokenTask extends AsyncTask<Void, Void, String> {

        /**
         * The doInBackground method for this class which handles the network traffic.
         *
         * @param voids
         * @return the authorisation url.
         */
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

        /**
         *
         * @param s
         */
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
