package com.example.justin.simpletwitter.view;


import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.justin.simpletwitter.R;

/**
 * The toolbar on top of the app as a custom view
 */
public class ToolbarView extends DrawerLayout {

    private LayoutInflater inflater;

    public ToolbarView(Context context) {
        super(context);
        init();
    }

    public ToolbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToolbarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.toolbar, this);

        Button btnTest = findViewById(R.id.btn_drawer_layout);

        DrawerLayout drawerLayout = findViewById(R.id.activity_container);

        btnTest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                        closeDrawer(GravityCompat.START);
                    } else {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }

            }
        });
    }
}
