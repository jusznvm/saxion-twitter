package com.example.justin.simpletwitter.fragment;

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

public class TabLayoutFragment extends Fragment {

    MyPagerAdapter adapter = null;
    TabLayout tabLayout = null;
    private int[] tabIcons = {
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
        //setupTabIcons();

        return view;
    }

    /**
     *  !!! Causes NullPointerException !!!
     */
    private void setupTabIcons() {
        tabLayout.getTabAt(R.id.tab1).setIcon(tabIcons[0]);
        tabLayout.getTabAt(R.id.tab2).setIcon(tabIcons[1]);
        tabLayout.getTabAt(R.id.tab3).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new HomeTimelineFragment(), "HOME");
        adapter.addFrag(new TestFragment(), "TEST");
        adapter.addFrag(new UserTimelineFragment(), "USER");
        viewPager.setAdapter(adapter);
    }
}
