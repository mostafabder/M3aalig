package com.asi.m3alig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.asi.m3alig.Utility.Constants;
import com.asi.m3alig.Utility.PreferenceUtilities;

import java.util.Locale;

import static com.asi.m3alig.Utility.PreferenceUtilities.ARABIC_LANGUAGE;
import static com.asi.m3alig.Utility.PreferenceUtilities.ENGLISH_LANGUAGE;

public class BeforLoginActivity extends AppCompatActivity {

    private ImageView mo3alij;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_befor_login);
        PreferenceUtilities.setLocale(BeforLoginActivity.this, PreferenceUtilities.getLanguage(BeforLoginActivity.this));

        mo3alij = (ImageView) findViewById(R.id.mo3alij);
        if (Locale.getDefault().getLanguage().equals("en")) {
            mo3alij.setImageResource(R.drawable.asset_129xhdpi);
        }else if(Locale.getDefault().getLanguage().equals("ar")){
            mo3alij.setImageResource(R.drawable.maleg);
        }

    }

    public void goToLogin(View view) {
        Intent intent =new Intent(BeforLoginActivity.this,LoginActivity.class);
        intent.putExtra(Constants.USER_KEY,Constants.PATIENT_TYPE);
        startActivity(intent);
    }

    public void loginAsMo3alg(View view) {
        Intent intent =new Intent(BeforLoginActivity.this,LoginActivity.class);
        intent.putExtra(Constants.USER_KEY,Constants.M3ALG_TYPE);
        startActivity(intent);
    }

    public void changeLanguage(View view){
        String language = PreferenceUtilities.getLanguage(this);
        if(language.equals(ARABIC_LANGUAGE)){
            PreferenceUtilities.setLocale(this, ENGLISH_LANGUAGE);
            recreate();
        }
        if(language.equals(ENGLISH_LANGUAGE)){
            PreferenceUtilities.setLocale(this, ARABIC_LANGUAGE);
            recreate();
        }
    }

}
