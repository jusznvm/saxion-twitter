package com.example.justin.simpletwitter.activity;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justin.simpletwitter.utils.AppInfo;
import com.example.justin.simpletwitter.utils.TwitterAPI;
import com.example.justin.simpletwitter.fragment.home.TabLayoutFragment;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.fragment.menu.DirectMessageFragment;
import com.example.justin.simpletwitter.fragment.profile.UserProfileFragment;
import com.example.justin.simpletwitter.model.User;
import com.example.justin.simpletwitter.utils.JSONParser;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;

    public static final String TAG = "MainActivity";

    private String name;

    private static OAuth10aService service = AppInfo.getService();
    private static AppInfo appInfo = AppInfo.getInstance();

    private TextView tvName, tvScreenName, tvFollowerCount, tvFollowingCount;
    private ImageView ivProfileImg;

    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {

                    case R.id.menu_dms: {
                        Log.d(TAG, "onNavigationItemSelected: clicked");
                        DirectMessageFragment dmFragment = new DirectMessageFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, dmFragment).addToBackStack(null).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case R.id.menu_profile: {
                        UserProfileFragment userProfileFragment = new UserProfileFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("name", user.getUserName());
                        userProfileFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, userProfileFragment).addToBackStack(null).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                }
                return false;
            }
        });

        tvName = hView.findViewById(R.id.tv_my_profile_name);
        tvScreenName = hView.findViewById(R.id.tv_my_profile_screen_name);
        tvFollowerCount = hView.findViewById(R.id.tv_my_profile_followers);
        tvFollowingCount = hView.findViewById(R.id.tv_my_profile_following);
        ivProfileImg = hView.findViewById(R.id.my_profile_image);


        CredentialsTask task = new CredentialsTask();
        task.execute();

        TabLayoutFragment firstFragment = new TabLayoutFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_content, firstFragment).commit();
      }

      class CredentialsTask extends AsyncTask<Void, Void, JSONObject> {

          @Override
          protected JSONObject doInBackground(Void... voids) {
              OAuthRequest request = new OAuthRequest(Verb.GET, TwitterAPI.ACCOUNT_CREDENTIALS);
              OAuth1AccessToken token = appInfo.getAccessToken();
              service.signRequest(token, request);
              try {
                  Response response = service.execute(request);
                  Log.d(TAG, "doInBackground: jsonObject" + response.getBody());
                  return new JSONObject(response.getBody());
              } catch (InterruptedException | IOException | ExecutionException | JSONException e) {
                  e.printStackTrace();
              }
              return null;
          }

          @Override
          protected void onPostExecute(JSONObject jsonObject) {
              super.onPostExecute(jsonObject);

              user = JSONParser.parseUser(jsonObject);

              String userName = "@" + user.getUserName();
              name = userName;
              String following = user.getFollowingCount() + " following";
              String followers = user.getFollowersCount() + " followers";

              tvName.setText(user.getName());
              tvScreenName.setText(userName);
              tvFollowingCount.setText(following);
              tvFollowerCount.setText(followers);

              String imgUrl = user.getImgUrl();
              Picasso.get().load(imgUrl).into(ivProfileImg);
          }
      }
}
