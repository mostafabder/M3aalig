package com.asi.m3alig.M3algFilesWork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.R;
import com.asi.m3alig.Utility.PreferenceUtilities;

public class OfferHelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(OfferHelpActivity.this, PreferenceUtilities.getLanguage(OfferHelpActivity.this));
        setContentView(R.layout.activity_offer_help);
    }

    public void goBack(View view) {
        onBackPressed();
    }
}
