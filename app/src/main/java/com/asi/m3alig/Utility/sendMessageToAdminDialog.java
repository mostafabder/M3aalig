package com.asi.m3alig.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.asi.m3alig.MainActivity;
import com.asi.m3alig.R;

import mehdi.sakout.fancybuttons.FancyButton;


/**
 * Created by ASI on 6/11/2017.
 */

public class sendMessageToAdminDialog {

    public void showDialog(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialogbox_admin_message);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        FancyButton fancyButton = (FancyButton) dialog.findViewById(R.id.send_to_admin);
        fancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent =new Intent(activity,MainActivity.class);
                intent.putExtra(Constants.USER_KEY,Constants.PATIENT_TYPE);
                activity.startActivity(intent);


            }
        });


        ImageView close = (ImageView) dialog.findViewById(R.id.close_image);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

//        ImageView ivTwitter= (ImageView) dialog.findViewById(R.id.ivTwitter);
//        ivTwitter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent .setType("text/plain");
//                intent .setPackage("com.twitter.android");
//                intent .putExtra(Intent.EXTRA_TEXT, "قم بتحميل البرنامج من الرابط التالي \n  " +
//                        "https://play.google.com/store/apps/details?id=com.Pharous.SouqALBenaa");
//                try {
//                    activity.startActivity(intent );
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toasty.error(activity,"Download google plus app first from google play",Toast.LENGTH_LONG,false).show();
//                }
//            }
//        });
//        ImageView ivFace= (ImageView) dialog.findViewById(R.id.ivFace);
//        ivFace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent .setType("text/plain");
//                intent .setPackage("com.facebook.katana");
//                intent .putExtra(Intent.EXTRA_TEXT, "قم بتحميل البرنامج من الرابط التالي  \n " +
//                        "https://play.google.com/store/apps/details?id=com.Pharous.SouqALBenaa");
//                try {
//                    activity.startActivity(intent );
//                } catch (android.content.ActivityNotFoundException ex) {
//                   Toasty.error(activity,"Download google plus app first from google play",Toast.LENGTH_LONG,false).show();
//                }
//            }
//        });
//
//        ImageView ivGooglePlus= (ImageView) dialog.findViewById(R.id.ivGooglePlus);
//        ivGooglePlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent .setType("text/plain");
//                intent .setPackage("com.google.android.apps.plus");
//                intent .putExtra(Intent.EXTRA_TEXT, "قم بتحميل البرنامج من الرابط التالي \n " +
//                        "https://play.google.com/store/apps/details?id=com.Pharous.SouqALBenaa");
//                try {
//                    activity.startActivity(intent );
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toasty.error(activity,"Download google plus app first from google play",Toast.LENGTH_LONG,false).show();
//                }
//            }
//        });
//        ImageView ivWhatsApp = (ImageView) dialog.findViewById(R.id.ivWhatsApp);
//        ivWhatsApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PackageManager pm=activity.getPackageManager();
//                try {
//
//                    Intent waIntent = new Intent(Intent.ACTION_SEND);
//                    waIntent.setType("text/plain");
//                    String text = "قم بتحميل البرنامج من الرابط التالي \n " +
//                            "https://play.google.com/store/apps/details?id=com.Pharous.SouqALBenaa";
//
//                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
//                    //Check if package exists or not. If not then code
//                    //in catch block will be called
//                    waIntent.setPackage("com.whatsapp");
//
//                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
//                    activity.startActivity(Intent.createChooser(waIntent, "Share with"));
//
//                } catch (PackageManager.NameNotFoundException e) {
//                    Toasty.error(activity,"Download whatsapp app first from google play",Toast.LENGTH_LONG,false).show();
//                }
//            }
//        });
        dialog.show();
    }
}
