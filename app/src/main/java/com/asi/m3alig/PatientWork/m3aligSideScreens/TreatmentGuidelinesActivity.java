package com.asi.m3alig.PatientWork.m3aligSideScreens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.asi.m3alig.Adapters.Patient.PatientMedicinesAdapter;
import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.Models.TestModel;
import com.asi.m3alig.R;
import com.asi.m3alig.Tools.Connection.ServerTool;
import com.asi.m3alig.Utility.PreferenceUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;

public class TreatmentGuidelinesActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<TestModel> data;
    private RecyclerView.LayoutManager layoutManager;

    private ImageView ivBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(TreatmentGuidelinesActivity.this, PreferenceUtilities.getLanguage(TreatmentGuidelinesActivity.this));
        setContentView(R.layout.activity_treatment_guidelines);

        recyclerView = (RecyclerView) findViewById(R.id.medicines_recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<>();
        GetFAQ();

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

    private void GetFAQ() {
        ServerTool.getFAQ(this, new ServerTool.APICallBack<List<TestModel>>() {
            @Override
            public void onSuccess(final List<TestModel> response) {
                if (response != null) {
                    Log.d("aaaa", response.size() + "");

                    for (int i = 0; i < response.size(); i++) {

                        data.add(new TestModel(
                                response.get(i).getName(),
                                response.get(i).getPhone(),
                                response.get(i).getNumber(),
                                response.get(i).getDesc(),
                                response.get(i).getNote(),
                                response.get(i).getPrice()
                        ));


                    }
                    adapter = new PatientMedicinesAdapter(data);
                    recyclerView.setAdapter(adapter);

                } else {
//                    Toast.makeText(getActivity(), "Can't get Data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }
        });

    }

}
