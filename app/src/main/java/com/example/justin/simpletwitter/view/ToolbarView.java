package com.example.justin.simpletwitter.view;


import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.example.justin.simpletwitter.R;

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
    }
}
