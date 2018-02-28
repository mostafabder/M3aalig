package com.asi.m3alig;

import android.app.Application;

import com.asi.m3alig.Utility.TypefaceUtil;

/**
 * Created by ASI on 1/2/2018.
 */

public class ApplicationController extends Application {


    ApplicationController applicationController;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationController=this;
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Changa-Light.ttf");

    }


}
