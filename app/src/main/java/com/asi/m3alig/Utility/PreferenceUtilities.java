package com.asi.m3alig.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by Zerir on 3/12/2018.
 */

public class PreferenceUtilities {

    //APP_LANGUAGE
    public static final String APP_LANGUAGE = "default-language";
    //ARABIC_LANGUAGE
    public static final String ARABIC_LANGUAGE = "ar";
    //ENGLISH_LANGUAGE
    public static final String ENGLISH_LANGUAGE = "en";

    synchronized public static void setLanguage(Context context, String language) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(APP_LANGUAGE, language);
        editor.apply();
    }

    public static String getLanguage(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String language = prefs.getString(APP_LANGUAGE, ARABIC_LANGUAGE);
        return language;
    }

    synchronized public static void setLocale(Context context, String language){
        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        setLanguage(context, language);
    }

}
