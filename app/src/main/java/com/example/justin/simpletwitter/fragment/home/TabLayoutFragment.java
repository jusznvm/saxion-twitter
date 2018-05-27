package com.example.justin.simpletwitter.fragment.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justin.simpletwitter.AppInfo;
import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.TwitterAPI;
import com.example.justin.simpletwitter.adapter.MyPagerAdapter;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class TabLayoutFragment extends Fragment {

    private static AppInfo appInfo = AppInfo.getInstance();
    private static OAuth10aService service = AppInfo.getService();

    private MyPagerAdapter adapter = null;
    private TabLayout tabLayout = null;

    final private int[] tabIcons = {
            R.drawable.house,
            R.drawable.alarm,
            R.drawable.search_1
    };

    final private String[] tabNames = {
            "HOME","NOTIFICATIONS","SEARCH"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);
        ViewPager vp = view.findViewById(R.id.view_pager);
        setupViewPager(vp);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(vp);

        //FloatingActionButton fab = view.findViewById(R.id.fab_compose);
        //fab.setImageDrawable(getResources().getDrawable(R.drawable.add));
        setupTabIcons(tabLayout);

//        //fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PostTweet post = new PostTweet();
//                post.execute("xxxx");
//            }
//        });
        return view;
    }

    private void setupTabIcons(TabLayout tabs) {
        TabLayout.Tab tab;
        for (int x=0; x<tabIcons.length; x++) {
            tab = tabs.getTabAt(x);
            if(tab != null){
                tab.setIcon(tabIcons[x]);
                tab.setText(tabNames[x]);
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new HomeTimelineFragment(), "HOME");
        adapter.addFrag(new MyMentionsTimeLine(), "@");
        adapter.addFrag(new SearchFragment(), "SEARCH");
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
