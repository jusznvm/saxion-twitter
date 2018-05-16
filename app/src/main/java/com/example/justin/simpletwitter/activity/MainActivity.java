package com.example.justin.simpletwitter.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.justin.simpletwitter.view.ToolbarView;
import com.example.justin.simpletwitter.fragment.TabLayoutFragment;
import com.example.justin.simpletwitter.R;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends FragmentActivity {

    private static JSONObject jsonObject = null;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        jsonObject = readFile();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.activity_container);
        Button btnTest = findViewById(R.id.btn_test);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }

            }
        });

        TabLayoutFragment firstFragment = new TabLayoutFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_content, firstFragment).commit();


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
