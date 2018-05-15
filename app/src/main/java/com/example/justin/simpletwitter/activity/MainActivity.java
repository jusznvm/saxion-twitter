package com.example.justin.simpletwitter.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    public static String MY_API_KEY = "swthavxIcVgN8Eghi5ZtdwVH1";
    public static String MY_API_SECRET = "ddQLDes9DgGa9gixh4t31h0la3rfSAOKNG1tCfbNWnXw4tLaWh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        jsonObject = readFile();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.activity_container);

        ToolbarView toolbarView = findViewById(R.id.view_toolbar);
        Toolbar toolbar = toolbarView.findViewById(R.id.toolbar);

        TabLayoutFragment firstFragment = new TabLayoutFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();

      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_button:
                Log.d("MainActivity", "onOptionsItemSelected: something happened");
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
