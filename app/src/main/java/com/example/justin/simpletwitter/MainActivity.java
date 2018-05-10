package com.example.justin.simpletwitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "cancer";
    private ListView lvTweets;
    private TweetAdapter tweetAdapter;
    private ArrayList<Status> statuses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statuses = JSONParser.parseStatus(readFile());
        lvTweets = findViewById(R.id.lv_tweets);
        tweetAdapter = new TweetAdapter(this, R.layout.tweet, statuses);
        lvTweets.setAdapter(tweetAdapter);
    }

    public JSONObject readFile() {

        InputStream is = getBaseContext().getResources().openRawResource(R.raw.test);
        JSONObject jsonObject = null;

        try {
            byte[] b = new byte[is.available()];
            is.read(b);
            String fileContent = new String(b);
            jsonObject = new JSONObject(fileContent);
            Log.d(TAG, "readFile: " + jsonObject);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
