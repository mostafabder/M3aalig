package com.asi.m3alig.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

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
        Resources resources = context.getResources();
        Configuration configuration = new Configuration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            configuration.setLocale(locale);
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context = context.createConfigurationContext(configuration);
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1 && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {

            configuration.setLocale(locale);
            context = context.createConfigurationContext(configuration);
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        } else {
            configuration.locale = locale;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }

        //context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        setLanguage(context, language);

//        Resources res = context.getResources();
//// Change locale settings in the app.
//        DisplayMetrics dm = res.getDisplayMetrics();
//        android.content.res.Configuration conf = res.getConfiguration();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            conf.setLocale(new Locale(language));
//        }// API 17+ only.
//        else {
//            conf.locale = new Locale(language);
//        }
//        res.updateConfiguration(conf, dm);

    }

}
