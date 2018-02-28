package com.asi.m3alig.M3algFilesWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.R;
import com.asi.m3alig.Utility.SQLiteHandler;
import com.asi.m3alig.Utility.SessionManager;

public class AccountSettingsActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_account_settings);
    }


    public void logout(View view){
        session = new SessionManager(AccountSettingsActivity.this);
        session.setLogin(false);
        Intent intent = new Intent(AccountSettingsActivity.this, BeforLoginActivity.class);
        new SQLiteHandler(getApplicationContext()).deleteUsers();
        startActivity(intent);
        finish();
    }

    public void goBack(View view) {
        onBackPressed();
    }
}
