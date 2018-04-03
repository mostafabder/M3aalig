package com.asi.m3alig;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.asi.m3alig.Utility.PreferenceUtilities;
import com.asi.m3alig.Utility.SQLiteHandler;
import com.asi.m3alig.Utility.SessionManager;

import java.util.Locale;

import static com.asi.m3alig.Utility.Constants.M3ALG_TYPE;
import static com.asi.m3alig.Utility.Constants.PATIENT_TYPE;
import static com.asi.m3alig.Utility.Constants.USER_KEY;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;
import static com.asi.m3alig.Utility.Constants.getType;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    private ImageView mo3alij;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PreferenceUtilities.setLocale(SplashActivity.this, PreferenceUtilities.getLanguage(SplashActivity.this));

        mo3alij = (ImageView) findViewById(R.id.mo3alij);
        if (Locale.getDefault().getLanguage().equals("en")) {
            mo3alij.setImageResource(R.drawable.asset_129xhdpi);
        }else if(Locale.getDefault().getLanguage().equals("ar")){
            mo3alij.setImageResource(R.drawable.maleg);
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                /* Create an Intent that will start the Menu-Activity. */
//                Log.e("Secret",getSecret(SplashActivity.this)+"         ");
//                Log.e("Token",getToken(SplashActivity.this)+"         ");
//                Log.e("type",getType(SplashActivity.this)+"         ");
//                new SQLiteHandler(SplashActivity.this).deleteUsers();
//                new SessionManager(SplashActivity.this).setLogin(false);


                if (new SessionManager(SplashActivity.this).isLoggedIn())
                {
                    Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                    if(getType(SplashActivity.this).equals(PATIENT_TYPE))
                    {
                        mainIntent.putExtra(USER_KEY,PATIENT_TYPE);
                        Log.e("Token",getToken(SplashActivity.this));
                        Log.e("Secret",getSecret(SplashActivity.this));
                    }
                    else mainIntent.putExtra(USER_KEY,M3ALG_TYPE);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }else {
                    Intent mainIntent = new Intent(SplashActivity.this,BeforLoginActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
