package com.asi.m3alig.Utility;

import android.content.Context;

/**
 * Created by ASI on 1/3/2018.
 */

public class Constants {

    public  static final String M3ALG_TYPE="1";
    public  static final String PATIENT_TYPE ="2";
    public  static final String USER_KEY ="M";
    public final static String FLAGE_CODE_SUCCSESS="200";
    public static String BASE_URL = "http://api.m3alig.com/";

    public final static String EGYPT_PHONE_CODE = "02";
    public final static String FLAG_CODE_SUCCESS_205 ="205";
    public final static String FLAG_CODE_SUCCESS_210 ="210";
    public final static String FLAG_CODE_SUCCESS_215 ="215";
    public final static String FLAG_CODE_SUCCESS_400 ="400";
    public final static String FLAG_CODE_SUCCESS_512 ="512";


    public static String getToken(Context context)
    {
        SQLiteHandler sqLiteHandler=new SQLiteHandler(context);
        return sqLiteHandler.getUserDetails().get("token");
    }

    public static String getSecret(Context context)
    {
        SQLiteHandler sqLiteHandler=new SQLiteHandler(context);
        return sqLiteHandler.getUserDetails().get("secret");
    }
    public static String getType(Context context)
    {
        SQLiteHandler sqLiteHandler=new SQLiteHandler(context);
        return sqLiteHandler.getUserDetails().get("user_type");
    }
}
