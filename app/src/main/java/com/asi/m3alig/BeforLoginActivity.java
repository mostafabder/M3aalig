package com.asi.m3alig;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.asi.m3alig.Utility.Constants;

public class BeforLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_befor_login);
    }

    public void goToLogin(View view) {
        Intent intentt =new Intent(BeforLoginActivity.this,LoginActivity.class);
        intentt.putExtra(Constants.USER_KEY,Constants.PATIENT_TYPE);
        startActivity(intentt);    }

    public void loginAsMo3alg(View view) {
        Intent intent =new Intent(BeforLoginActivity.this,LoginActivity.class);
        intent.putExtra(Constants.USER_KEY,Constants.M3ALG_TYPE);
        startActivity(intent);
    }
}
