package com.example.valdemar.daaduniva.Models;

import android.support.v4.app.Fragment;

public class ItemViewFragment {
    int position;
    String title;
    Fragment fragment;

    public ItemViewFragment(int position, String title, Fragment fragment) {
        this.position = position;
        this.title = title;
        this.fragment = fragment;
    }

    public ItemViewFragment(int fragmentDepartmen, String title) {
    }

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
