package com.asi.m3alig.PatientWork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.R;
import com.asi.m3alig.Utility.PreferenceUtilities;

public class FinishSessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(FinishSessionActivity.this, PreferenceUtilities.getLanguage(FinishSessionActivity.this));
        setContentView(R.layout.activity_finish_session);



    }


    public void rateSession(View view) {
        startActivity(new Intent(FinishSessionActivity.this,RateTreatmentSessionActivity.class));
    }
}
