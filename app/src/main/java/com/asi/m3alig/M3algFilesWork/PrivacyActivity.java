package com.asi.m3alig.M3algFilesWork;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.asi.m3alig.R;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_privacy);
    }
    public void goBack(View view) {
        onBackPressed();
    }
    public void privacy_click(View view) {
        Intent web_intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.m3alij.com/test-arabic/patient/terms"));
        startActivity(web_intent);
    }
    public void usage_click(View view) {
        Intent web_intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.m3alij.com/test-arabic/patient/terms"));
        startActivity(web_intent);
    }

}
