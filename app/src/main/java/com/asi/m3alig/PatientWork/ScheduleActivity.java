package com.asi.m3alig.PatientWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.Models.VisitOrderPatient;
import com.asi.m3alig.R;
import com.asi.m3alig.Utility.PreferenceUtilities;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;

public class ScheduleActivity extends AppCompatActivity {
    LinearLayout urgentOrderLayout, bookOrderLayout;
    FancyButton urgentOrderButton, bookOrderButton;
    SingleDateAndTimePicker singleDateAndTimePicker;
    VisitOrderPatient order;
    String date,time;
    Calendar calendar;

    private ImageView ivBackArrow, ivMoreArrow, ivRow1, ivRow2, ivRow3, ivRow4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(ScheduleActivity.this, PreferenceUtilities.getLanguage(ScheduleActivity.this));
        setContentView(R.layout.activity_schedule);
        singleDateAndTimePicker=(SingleDateAndTimePicker)findViewById(R.id.singledateandtimepicker);
        order=(VisitOrderPatient)getIntent().getSerializableExtra("order");
        order.setType("normal");
        getDateTime();
        calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        singleDateAndTimePicker.setMinDate(calendar.getTime());
        urgentOrderLayout = (LinearLayout) findViewById(R.id.urgent_order_layout);
        bookOrderLayout = (LinearLayout) findViewById(R.id.book_date_layout);
        urgentOrderButton = (FancyButton) findViewById(R.id.urgent_order_button);
        bookOrderButton = (FancyButton) findViewById(R.id.book_date_button);
        bookOrderButton.setBackgroundColor(getResources().getColor(R.color.green_highlighted));
        urgentOrderButton.setBackgroundColor(getResources().getColor(R.color.appcolor));
        bookOrderLayout.setVisibility(View.VISIBLE);
        urgentOrderLayout.setVisibility(View.GONE);

        bookOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setType("normal");
                bookOrderButton.setBackgroundColor(getResources().getColor(R.color.green_highlighted));
                urgentOrderButton.setBackgroundColor(getResources().getColor(R.color.appcolor));
                bookOrderLayout.setVisibility(View.VISIBLE);
                urgentOrderLayout.setVisibility(View.GONE);
            }
        });

        urgentOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setType("argent");
                Calendar c=Calendar.getInstance();
                String date = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
                String time=new SimpleDateFormat("hh:mm").format(c.getTime());
                order.setTime(time);
                order.setDate(date);
                urgentOrderButton.setBackgroundColor(getResources().getColor(R.color.green_highlighted));
                bookOrderButton.setBackgroundColor(getResources().getColor(R.color.appcolor));
                bookOrderLayout.setVisibility(View.GONE);
                urgentOrderLayout.setVisibility(View.VISIBLE);
            }
        });

        ivBackArrow = (ImageView) findViewById(R.id.ivBackArrow);
        ivMoreArrow = (ImageView) findViewById(R.id.ivMoreArrow);
        ivRow1 = (ImageView) findViewById(R.id.ivRow1);
        ivRow2 = (ImageView) findViewById(R.id.ivRow2);
        ivRow3 = (ImageView) findViewById(R.id.ivRow3);
        ivRow4 = (ImageView) findViewById(R.id.ivRow4);
        if(Locale.getDefault().getLanguage().equals("ar")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivMoreArrow.setImageResource(R.drawable.more_icon_en);
            ivRow1.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow2.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow3.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow4.setImageResource(R.drawable.main_screen_arrow_icon);
        } if(Locale.getDefault().getLanguage().equals("en")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon);
            ivMoreArrow.setImageResource(R.drawable.more_icon);
            ivRow1.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow2.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow3.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow4.setImageResource(R.drawable.main_screen_arrow_icon_en);
        }

    }
    public void goBack(View view) {
        onBackPressed();
    }

    public void nextPage(View view) {
        getDateTime();
        Intent intent=new Intent(ScheduleActivity.this, NotesActivity.class);
        intent.putExtra("order",order);
        Log.e("type,date,time",order.getType()+","+order.getDate()+","+order.getTime());
        startActivity(intent);
    }
    public void getDateTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("hh:mm");
        date=simpleDateFormat.format(singleDateAndTimePicker.getDate());
        time=simpleDateFormat1.format(singleDateAndTimePicker.getDate());
        order.setDate(date);
        order.setTime(time);
    }
}
