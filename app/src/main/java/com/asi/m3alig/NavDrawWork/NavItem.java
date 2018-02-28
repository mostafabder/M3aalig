package com.asi.m3alig.NavDrawWork;

/**
 * Created by ASI on 1/2/2018.
 */

public class NavItem {
    String mTitle;
    int mIcon;

    public NavItem(String title, int icon) {
        mTitle = title;
        mIcon = icon;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }



    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }
}
