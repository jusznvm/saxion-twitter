package com.example.justin.simpletwitter.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.justin.simpletwitter.AppInfo;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.TwitterAPI;
import com.example.justin.simpletwitter.adapter.MyPagerAdapter;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class TabLayoutFragment extends Fragment {

    private static AppInfo appInfo = AppInfo.getInstance();
    private static OAuth10aService service = AppInfo.getService();

    private MyPagerAdapter adapter = null;
    private TabLayout tabLayout = null;

    final private int[] tabIcons = {
            R.drawable.ic_favorite_black_24dp,
            R.drawable.ic_call_black_24dp,
            R.drawable.ic_contacts_black_24dp
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);
        ViewPager vp = view.findViewById(R.id.view_pager);
        setupViewPager(vp);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(vp);

        FloatingActionButton fab = view.findViewById(R.id.fab_compose);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostTweet post = new PostTweet();
                post.execute("xxxx");
            }
        });
        return view;
    }

    /**
     *  !!! Causes NullPointerException !!!
     */
    private void setupTabIcons() {
        // Create tabs
            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(R.id.tab2).setIcon(tabIcons[1]);
            tabLayout.getTabAt(R.id.tab3).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new HomeTimelineFragment(), "HOME");
        adapter.addFrag(new MentionsTimeLine(), "@");
        adapter.addFrag(new UserTimelineFragment(), "USER");
        adapter.addFrag(new DirectMessageFragment(), "DMS");
        viewPager.setAdapter(adapter);
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
