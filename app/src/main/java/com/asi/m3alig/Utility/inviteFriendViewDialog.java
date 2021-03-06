package com.asi.m3alig.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.m3alig.R;


/**
 * Created by ASI on 6/11/2017.
 */

public class inviteFriendViewDialog {

    public void showDialog(final Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialogbox_otp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.txt_file_path);
        text.setText(msg);



        ImageView close = (ImageView) dialog.findViewById(R.id.ivClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        ImageView ivTwitter= (ImageView) dialog.findViewById(R.id.ivTwitter);
        ivTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent .setType("text/plain");
                intent .setPackage("com.twitter.android");
                intent .putExtra(Intent.EXTRA_TEXT, "قم بتحميل البرنامج من الرابط التالي \n  " +
                        "https://www.m3alij.com/test-arabic/");
                try {
                    activity.startActivity(intent );
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, R.string.download_twitter,Toast.LENGTH_LONG).show();
                }
            }
        });
        ImageView ivFace= (ImageView) dialog.findViewById(R.id.ivFace);
        ivFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent .setType("text/plain");
                intent .setPackage("com.facebook.katana");
                intent .putExtra(Intent.EXTRA_TEXT, "قم بتحميل البرنامج من الرابط التالي  \n " +
                        "https://www.m3alij.com/test-arabic/");
                try {
                    activity.startActivity(intent );
                } catch (android.content.ActivityNotFoundException ex) {
                   Toast.makeText(activity, R.string.fb_download,Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageView ivGooglePlus= (ImageView) dialog.findViewById(R.id.ivGooglePlus);
        ivGooglePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent .setType("text/plain");
                intent .setPackage("com.google.android.apps.plus");
                intent .putExtra(Intent.EXTRA_TEXT, "قم بتحميل البرنامج من الرابط التالي \n " +
                        "https://www.m3alij.com/test-arabic/");
                try {
                    activity.startActivity(intent );
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, R.string.gplus_download,Toast.LENGTH_LONG).show();
                }
            }
        });
        ImageView ivWhatsApp = (ImageView) dialog.findViewById(R.id.ivWhatsApp);
        ivWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm=activity.getPackageManager();
                try {

                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "قم بتحميل البرنامج من الرابط التالي \n " +
                            "https://www.m3alij.com/test-arabic/";

                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    //Check if package exists or not. If not then code
                    //in catch block will be called
                    waIntent.setPackage("com.whatsapp");

                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    activity.startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(activity, R.string.whatsapp_download,Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }
}
