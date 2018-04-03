package com.asi.m3alig.M3algFilesWork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.R;
import com.asi.m3alig.Utility.PreferenceUtilities;

import java.util.Locale;

public class PrivacyActivity extends AppCompatActivity {

    private ImageView ivBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(PrivacyActivity.this, PreferenceUtilities.getLanguage(PrivacyActivity.this));
        setContentView(R.layout.activity_privacy);

        ivBackArrow = (ImageView) findViewById(R.id.ivBackArrow);
        if(Locale.getDefault().getLanguage().equals("ar")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon_en);
        } if(Locale.getDefault().getLanguage().equals("en")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon);
        }

    }

    public void goBack(View view) {
        onBackPressed();
    }
}
