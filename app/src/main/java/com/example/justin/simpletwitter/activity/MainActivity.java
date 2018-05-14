package com.example.justin.simpletwitter.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.justin.simpletwitter.fragment.TabLayoutFragment;
import com.example.justin.simpletwitter.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends FragmentActivity {

    private static JSONObject jsonObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        jsonObject = readFile();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            // Create a new Fragment to be placed in the activity layout
            TabLayoutFragment firstFragment = new TabLayoutFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();

    }

    public JSONObject readFile() {

        InputStream is = getBaseContext().getResources().openRawResource(R.raw.test);
        JSONObject jsonObject = null;

        try {
            byte[] b = new byte[is.available()];
            is.read(b);
            String fileContent = new String(b);
            jsonObject = new JSONObject(fileContent);
            Log.d(" X", "readFile: " + jsonObject);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getJsonObject() {
        return jsonObject;
    }


}
