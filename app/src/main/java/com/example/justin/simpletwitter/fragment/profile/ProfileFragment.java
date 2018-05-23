package com.example.justin.simpletwitter.fragment.profile;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justin.simpletwitter.AppInfo;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.TwitterAPI;
import com.example.justin.simpletwitter.model.User;
import com.example.justin.simpletwitter.parser.JSONParser;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1Token;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ProfileFragment extends Fragment {

    private User user = null;

    private ImageView ivAvatar;
    private TextView tvDesc;

    private static OAuth10aService service = AppInfo.getService();

    private static AppInfo appInfo = AppInfo.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_profile, container, false);

        ivAvatar = view.findViewById(R.id.profile_image);
        tvDesc = view.findViewById(R.id.tv_user_profile_description);

        GetProfileDetails task = new GetProfileDetails();
        task.execute();
        return view;
    }

    class GetProfileDetails extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {
            String url = TwitterAPI.USER_SHOW + UserProfileFragment.SCREENNAME;
            OAuthRequest request = new OAuthRequest(Verb.GET, url);
            OAuth1AccessToken token = appInfo.getAccessToken();
            service.signRequest(token, request);
            try {
                Response response = service.execute(request);
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

            tvDesc.setText(user.getDescription());
            Picasso.get().load(imgUrl).into(ivAvatar);
        }
    }
}
