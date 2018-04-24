package com.asi.m3alig.PatientWork;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.Models.VisitOrderPatient;
import com.asi.m3alig.R;
import com.asi.m3alig.Utility.PreferenceUtilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;

public class WhenPainStartActivity extends AppCompatActivity {
    ImageView bodyImageView;
    FancyButton frontBodyButton, backBodyButton, deepPainButton, littlePainButton;
    private String Date;
    TextView whenPainStartTextView;
    VisitOrderPatient order;
     Calendar c;

    private ImageView ivBackArrow, ivMoreArrow, ivRow1, ivRow2;

    private RelativeLayout rl_master;
    private FancyButton bt_removePoints;
    private boolean front;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(WhenPainStartActivity.this, PreferenceUtilities.getLanguage(WhenPainStartActivity.this));
        setContentView(R.layout.activity_when_pain_start);
        order=(VisitOrderPatient)getIntent().getSerializableExtra("order");
        c = Calendar.getInstance();
        bodyImageView = (ImageView) findViewById(R.id.body_image_view);
        frontBodyButton = (FancyButton) findViewById(R.id.front_body_button);
        backBodyButton = (FancyButton) findViewById(R.id.back_body_button);

        bt_removePoints = (FancyButton) findViewById(R.id.bt_removePoints);
        rl_master = (RelativeLayout) findViewById(R.id.rl_master);
        front = true;

        bodyImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int x = (int) event.getX();
                int y = (int) event.getY();

                if(event.getAction() == MotionEvent.ACTION_UP){
                    ImageView point = new ImageView(WhenPainStartActivity.this);
                    point.setImageResource(R.mipmap.ic_point_points);
                    point.setScaleType(ImageView.ScaleType.FIT_XY);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            48,
                            48);
                    layoutParams.addRule(RelativeLayout.ALIGN_START);
                    layoutParams.addRule(RelativeLayout.ALIGN_TOP);
                    layoutParams.topMargin = y - 24;
                    layoutParams.leftMargin = x - 24;

                    rl_master.addView(point, layoutParams);

                    bt_removePoints.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

        bt_removePoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_master.removeAllViews();
                bt_removePoints.setVisibility(View.GONE);
            }
        });

//        deepPainButton = (FancyButton) findViewById(R.id.deep_pain_button);
//        littlePainButton = (FancyButton) findViewById(R.id.little_pain_button);
        whenPainStartTextView = (TextView) findViewById(R.id.when_pain_start);
        String date=c.get(Calendar.DAY_OF_MONTH) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR);
        Log.e("pain_date",date+"  ");
        order.setWhen_pain_start(date);
        order.setPain_position("from front");
        whenPainStartTextView.setText(date);
        backBodyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(front) {
                    order.setPain_position("from front");
                    backBodyButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this, R.color.green_highlighted));
                    frontBodyButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this, R.color.appcolor));
                    bodyImageView.setImageResource(R.mipmap.back_man_icon);
                    front = false;
                    rl_master.removeAllViews();
                }
            }
        });


        frontBodyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!front) {
                    order.setPain_position("from back");
                    frontBodyButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this, R.color.green_highlighted));
                    backBodyButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this, R.color.appcolor));
                    bodyImageView.setImageResource(R.mipmap.front_man_icon);
                    front = true;
                    rl_master.removeAllViews();
                }
            }
        });
//
//
//        deepPainButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                order.setPain_position("0");
//                deepPainButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this,R.color.green_highlighted));
//                littlePainButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this,R.color.appcolor));
//            }
//        });


//        littlePainButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                order.setPain_position("1");
//                littlePainButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this,R.color.green_highlighted));
//                deepPainButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this,R.color.appcolor));
//            }
//        });

        ivBackArrow = (ImageView) findViewById(R.id.ivBackArrow);
        ivMoreArrow = (ImageView) findViewById(R.id.ivMoreArrow);
        ivRow1 = (ImageView) findViewById(R.id.ivRow1);
        ivRow2 = (ImageView) findViewById(R.id.ivRow2);
        if(Locale.getDefault().getLanguage().equals("ar")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivMoreArrow.setImageResource(R.drawable.more_icon_en);
            ivRow1.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow2.setImageResource(R.drawable.main_screen_arrow_icon);
        } if(Locale.getDefault().getLanguage().equals("en")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon);
            ivMoreArrow.setImageResource(R.drawable.more_icon);
            ivRow1.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow2.setImageResource(R.drawable.main_screen_arrow_icon_en);
        }

    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void nextPage(View view) {
        Intent intent=new Intent(WhenPainStartActivity.this, ChoosePlaceActivity.class);
        intent.putExtra("order",order);
        Log.e("start,position",order.getWhen_pain_start()+","+order.getPain_position());
        startActivity(intent);
    }


    public void whenPainStart(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        whenPainStartTextView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        order.setWhen_pain_start(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,0);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
        //
    }

}
