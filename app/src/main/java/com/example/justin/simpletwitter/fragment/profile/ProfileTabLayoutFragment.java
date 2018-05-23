package com.example.justin.simpletwitter.fragment.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.adapter.MyPagerAdapter;
import com.example.justin.simpletwitter.fragment.HomeTimelineFragment;

public class ProfileTabLayoutFragment extends Fragment{

    private MyPagerAdapter adapter = null;
    private TabLayout tabLayout = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_tab_layout, container, false);
        ViewPager vp = view.findViewById(R.id.view_pager_profile);
        setupViewPager(vp);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_profile);
        tabLayout.setupWithViewPager(vp);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new TimelineFragment(), "TIMELINE");
        adapter.addFrag(new LikesFragment(), "LIKES");
        viewPager.setAdapter(adapter);
    }

}
