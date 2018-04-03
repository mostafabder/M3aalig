package com.asi.m3alig.M3algFilesWork;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.asi.m3alig.R;

public class HelpCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_help_center);
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void whatsapp_click(View view) {
//        try {
//
//            String toNumber = "201060878610"; // Replace with mobile phone number without +Sign or leading zeros.
//            Intent sendIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + "" + toNumber + "?body=" + ""));
//            sendIntent.setPackage("com.whatsapp");
//            startActivity(sendIntent);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            Toast.makeText(HelpCenterActivity.this,"Download whatsapp app first from google play",Toast.LENGTH_LONG).show();
//
//        }
    }

    public void gmail_click(View view) {
//        try{
//            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//            sendIntent.setType("plain/text");
//            sendIntent.setData(Uri.parse("test@gmail.com"));
//            sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
//            sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "test@gmail.com" });
//            startActivity(sendIntent);
//        }
//        catch (Exception e){
//            Toast.makeText(HelpCenterActivity.this,"Download Gmail app first from google play",Toast.LENGTH_LONG).show();
//        }
    }

    public void facebook_click(View view) {
//        Intent webIntent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse("https://www.m3alij.com/test-arabic/"));
//        startActivity(webIntent);
    }

    public void twitter_click(View view) {
//        Intent webIntent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse("https://www.m3alij.com/test-arabic/"));
//        startActivity(webIntent);
    }
}
