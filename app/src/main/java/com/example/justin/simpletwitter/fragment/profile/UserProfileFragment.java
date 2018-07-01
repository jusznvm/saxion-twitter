package com.example.justin.simpletwitter.fragment.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justin.simpletwitter.R;

/**
 * Fragment that holds a user profile, including the 2 tab fragments
 */
public class UserProfileFragment extends Fragment {

    public static String SCREENNAME = "";
    public static final String TAG = "UserProfileFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the view
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Initialize fragments
        ProfileFragment pf = new ProfileFragment();
        ProfileTabLayoutFragment pftbl = new ProfileTabLayoutFragment();

        SCREENNAME = getArguments().getString("name");
        Log.d(TAG, "onCreateView: screenname " + getArguments().getString("name"));
        Log.d(TAG, "onCreateView: SCREENNAME" + SCREENNAME);

        // Start the transaction
        getFragmentManager().beginTransaction().add(R.id.user_profile_profile_fragment_holder, pf).commit();
        getFragmentManager().beginTransaction().add(R.id.user_profile_tab_layout_fragment_holder, pftbl).commit();

        // Return the view
        return view;
    }
}
