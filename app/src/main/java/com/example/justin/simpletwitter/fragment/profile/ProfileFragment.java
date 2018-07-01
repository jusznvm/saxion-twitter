package com.example.justin.simpletwitter.fragment.profile;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justin.simpletwitter.utils.AppInfo;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.utils.TwitterAPI;
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

/**
 * Fragment that holds a user profile
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    private User user = null;

    private ImageView ivAvatar, ivBanner;
    private TextView tvDesc, tvUserName, tvScreenName;
    private Button btnFollow;

    private static OAuth10aService service = AppInfo.getService();

    private static AppInfo appInfo = AppInfo.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_profile, container, false);

        ivAvatar = view.findViewById(R.id.profile_image);

        tvDesc = view.findViewById(R.id.tv_user_profile_description);
        tvScreenName = view.findViewById(R.id.tv_screen_name);
        tvUserName = view.findViewById(R.id.tv_user_name);

        btnFollow = view.findViewById(R.id.btn_follow);

        GetProfileDetails task = new GetProfileDetails();
        task.execute();

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FollowTask task = new FollowTask();
                task.execute();
            }
        });

        return view;
    }

    /**
     * Gets the profile of a user
     */
    class GetProfileDetails extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {
            String url = TwitterAPI.USER_SHOW + UserProfileFragment.SCREENNAME;
            OAuthRequest request = new OAuthRequest(Verb.GET, url);
            OAuth1AccessToken token = appInfo.getAccessToken();
            service.signRequest(token, request);
            try {
                Response response = service.execute(request);
                Log.d(TAG, "doInBackground: " + response.getBody());
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
            handleResult(user);
        }

        public void handleResult(User user) {
            String imgUrl = user.getImgUrl();
            String screenName = "@" + user.getUserName();
            String userName = user.getName();

            tvDesc.setText(user.getDescription());
            tvScreenName.setText(screenName);
            tvUserName.setText(userName);
            Picasso.get().load(imgUrl).into(ivAvatar);
        }
    }

    class FollowTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String url;
            if(user.isFollowing()) {
                url = TwitterAPI.FOLLOW_USER + user.getUserName();
            } else {
                url = TwitterAPI.UNFOLLOW_USER + user.getUserName();
            }
            OAuthRequest request = new OAuthRequest(Verb.POST, url);
            OAuth1AccessToken token = appInfo.getAccessToken();
            service.signRequest(token, request);
            return null;
        }
    }
}
