package com.asi.m3alig.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asi.m3alig.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {
    public static Dialog dialogNetwork = null;
    public static ArrayList<Integer> compareItemsIntegerList = new ArrayList<>();
    public static int compareCount;
     public static void addCompareItem(int id){
         compareItemsIntegerList.add(id);
         compareCount=compareItemsIntegerList.size();
     }



    public static void hideKeypad(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void openWebPage(Context context, String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            Uri webPage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            context.startActivity(intent);
        } catch (Exception ex) {
//            CustomToast.customToastView((Activity) context, context.getResources().getString(R.string.general_error));
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mConnectivity =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivity.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting();
    }

    /*
    public static void loadSimplePicWithError(String url, ImageView pic, @DrawableRes int resId) {
    Glide.with(BaseClass.getContext()).load(url).placeholder(R.drawable.ghost_person).error(resId)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .dontAnimate().into(pic);
}

     */
//    public static void loadSimplePicWithError(Context context, String url, ImageView pic) {
//        Glide.with(context).load(url).placeholder(R.drawable.header).error(R.drawable.header)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .dontAnimate().into(pic);
//    }

    public static boolean validString(String string) {
        if (string != null) {
            if (string.length() > 0) {
                return true;
            }
            return false;
        }
        return false;

    }

    public static boolean validObject(Object myObject) {
        if (myObject != null) {
            return true;
        }
        return false;

    }

    public static boolean validList(List list) {
        if (list != null) {
            if (list.size() > 0) {
                return true;
            }
            return false;
        }
        return false;

    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    //get image path
    public static String getRealPathFromURI(Context context, Uri uri) {
        String filePath = "";
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
//            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        }
        cursor.close();
        return filePath;
    }

    public static float getImageSize(String picturePath) {
        File file = new File(picturePath);
        // Get length of file in bytes
        long fileSizeInBytes = file.length();
        float fileSizeInKB = fileSizeInBytes / 1024;
        float fileSizeInMB = fileSizeInKB / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        return fileSizeInMB;
    }

    public static String encodeFileToBase64(String sourceFile) throws Exception {


        String base64EncodedData = Base64.encodeToString(loadFileAsBytesArray(sourceFile), Base64.DEFAULT);
        // Log.d("base 64 ", base64EncodedData + "");

        return base64EncodedData;
    }

    public static byte[] loadFileAsBytesArray(String fileName) throws Exception {
        File file = new File(fileName);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;

    }

    public static Bitmap decodeUri(Uri selectedImage, Context context) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                context.getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 200;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                context.getContentResolver().openInputStream(selectedImage), null, o2);
    }

    public static void colorStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        }
    }

    // to server
//    public static int getLocalization(Context context) {
////        Localization localization = new Localization();
//        UserData userData = new UserData();
//        return userData.getLocalization(context);
////        return localization.getCurrentLanguageID(context);
//
//    }


//    public static void logOut(Context context) {
//        //clear all data Except language
//        UserData userData = new UserData();
//        Localization localization = new Localization();
//        int language = userData.getLocalization(context);
//        userData.clearShared(context);
//        localization.setLanguage(context, language);
//        userData.setLanguageScreen(context, false);
//        context.startActivity(new Intent(context, LoginActivity.class));
//        ((Activity) context).finish();
//    }

    public static String formateDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy EEE");
        Date d = null;
        try {
            try {
                d = sdf.parse(date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        return formattedTime;
    }

    public static String formateNewDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        Date d = null;
        try {
            try {
                d = sdf.parse(date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        return formattedTime;
    }

    public static String formateTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            try {
                d = sdf.parse(date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        return formattedTime;
    }

//    public static void setFontTextView(Context context, TextViewNormal textViewNormal) {
//        textViewNormal.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GE_Dinar_Two_Medium.otf"), Typeface.BOLD);
//        textViewNormal.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//
//    }


    public static Animation expand(final View item) {
        if (item.getVisibility() == View.GONE) {
            item.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            final int targetHeight = item.getMeasuredHeight();
            // Older versions of android (pre API 21) cancel animations for views with aaa height of 0.
            item.getLayoutParams().height = 1;
            item.setVisibility(View.VISIBLE);
            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    item.getLayoutParams().height = interpolatedTime == 1
                            ? LinearLayout.LayoutParams.WRAP_CONTENT
                            : (int) (targetHeight * interpolatedTime);
                    item.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // 1dp/ms
            anim.setDuration((int) (targetHeight / item.getContext().getResources().getDisplayMetrics().density));
            item.startAnimation(anim);
            return anim;
        }
        return null;
    }

    public static Animation collapse(final View item) {
        if (item.getVisibility() == View.VISIBLE) {
            final int initialHeight = item.getMeasuredHeight();
            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        item.setVisibility(View.GONE);
                    } else {
                        item.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        item.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            // 1dp/ms
            anim.setDuration((int) (initialHeight / item.getContext().getResources().getDisplayMetrics().density));
            item.startAnimation(anim);
            return anim;
        }
        return null;
    }


    public static AlertDialog.Builder getBuilder(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }
    //dialog for anything without buttons
    /** use getBuilder to create this builder that parameter is need
     * --> @getBuilder
     * @param builder
     */
    public final static AlertDialog showDialog(
            AlertDialog.Builder builder,
            String title,
            String message,
            boolean cancelable,
            boolean autoDismiss) {
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancelable);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //to make dialog dismiss and hide automatically in five seconds
        if(autoDismiss) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss();
                    alertDialog.hide();
                }
            }, 5000);
        }
        return alertDialog;
    }


}
