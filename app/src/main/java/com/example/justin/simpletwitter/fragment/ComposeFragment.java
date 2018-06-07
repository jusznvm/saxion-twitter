package com.example.justin.simpletwitter.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.fragment.home.TabLayoutFragment;
import com.example.justin.simpletwitter.utils.AppInfo;
import com.example.justin.simpletwitter.utils.TwitterAPI;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class ComposeFragment extends Fragment {

    private static OAuth10aService service = AppInfo.getService();
    private static AppInfo appInfo = AppInfo.getInstance();

    private Button btnPost;

    private EditText etContent;

    public ComposeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.compose_tweet, container, false);

        etContent = view.findViewById(R.id.et_compose_tweet);
        if(getArguments() != null) {
            String s = "@" + getArguments().getString("user_name");
            etContent.setText(s);
        }

        btnPost = view.findViewById(R.id.btn_post_tweet);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etContent.getText().toString();
                PostTweet post = new PostTweet();
                post.execute(content);
                TabLayoutFragment fragment = new TabLayoutFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, fragment).addToBackStack(null).commit();
            }
        });

        return view;
    }


    public class PostTweet extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                String encoded = URLEncoder.encode(strings[0], "UTF-8");
                String url = TwitterAPI.STATUSES_UPDATE + encoded;
                OAuthRequest request = new OAuthRequest(Verb.POST, url);
                service.signRequest(AppInfo.getAccessToken(), request);
                service.execute(request);
            } catch (InterruptedException | IOException | ExecutionException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
