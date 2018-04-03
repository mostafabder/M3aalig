package com.asi.m3alig.PatientWork;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.m3alig.Models.VisitOrderPatient;
import com.asi.m3alig.R;

import java.util.Calendar;

import mehdi.sakout.fancybuttons.FancyButton;

public class WhenPainStartActivity extends AppCompatActivity {
    ImageView bodyImageView;
    FancyButton frontBodyButton, backBodyButton, deepPainButton, littlePainButton;
    private String Date;
    TextView whenPainStartTextView;
    VisitOrderPatient order;
     Calendar c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_when_pain_start);
        order=(VisitOrderPatient)getIntent().getSerializableExtra("order");
        c = Calendar.getInstance();
        bodyImageView = (ImageView) findViewById(R.id.body_image_view);
        frontBodyButton = (FancyButton) findViewById(R.id.front_body_button);
        backBodyButton = (FancyButton) findViewById(R.id.back_body_button);
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
                order.setPain_position("from front");
                backBodyButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this,R.color.green_highlighted));
                frontBodyButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this,R.color.appcolor));
                bodyImageView.setImageResource(R.mipmap.back_man_icon);
            }
        });


        frontBodyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setPain_position("from back");
                frontBodyButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this,R.color.green_highlighted));
                backBodyButton.setBackgroundColor(ContextCompat.getColor(WhenPainStartActivity.this,R.color.appcolor));
                bodyImageView.setImageResource(R.mipmap.front_man_icon);
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
        calendar.add(Calendar.DAY_OF_YEAR,1);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
        //
    }


}
