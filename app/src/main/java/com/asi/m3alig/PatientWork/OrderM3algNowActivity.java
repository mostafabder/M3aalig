package com.asi.m3alig.PatientWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.asi.m3alig.Models.VisitOrderPatient;
import com.asi.m3alig.R;

public class OrderM3algNowActivity extends AppCompatActivity {
    TextView orderForMeTextView, orderForFamilyTextView, orderForOthersTextView,
            maleTextView, FemaleTextView,
            marriedTextView, singleTextView,
            canWalkTextView, sittingTextView, canNotMoveTextView,
            noDiseaseTextView, diabeticTextView, bloodPressureTextView, heartPatientTextView, otherDiseaseTextView;
    com.shawnlin.numberpicker.NumberPicker numberPicker;
    VisitOrderPatient order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_m3alg_now);
        orderForMeTextView = (TextView) findViewById(R.id.order_for_me_text_view);
        orderForFamilyTextView = (TextView) findViewById(R.id.order_for_family_text_view);
        orderForOthersTextView = (TextView) findViewById(R.id.order_for_other_text_view);

        maleTextView = (TextView) findViewById(R.id.male_text_view);
        FemaleTextView = (TextView) findViewById(R.id.female_text_view);

        numberPicker=(com.shawnlin.numberpicker.NumberPicker) findViewById(R.id.number_picker);
        marriedTextView = (TextView) findViewById(R.id.married_text_view);
        singleTextView = (TextView) findViewById(R.id.single_text_view);

        canWalkTextView = (TextView) findViewById(R.id.can_walk_text_view);
        sittingTextView = (TextView) findViewById(R.id.sitting_text_view);
        canNotMoveTextView = (TextView) findViewById(R.id.can_not_walk_text_view);

        noDiseaseTextView = (TextView) findViewById(R.id.no_disease_text_view);
        diabeticTextView = (TextView) findViewById(R.id.diabetic_text_view);
        bloodPressureTextView = (TextView) findViewById(R.id.blood_pressure_text_view);
        heartPatientTextView = (TextView) findViewById(R.id.heart_patient_text_view);
        otherDiseaseTextView = (TextView) findViewById(R.id.other_disease_text_view);

        orderForMeTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
        orderForFamilyTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
        orderForOthersTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
        maleTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
        FemaleTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
        marriedTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
        singleTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
        canWalkTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
        sittingTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
        canNotMoveTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
        noDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
        diabeticTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
        bloodPressureTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
        heartPatientTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
        otherDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));

        orderForMeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setWho_need_session(orderForMeTextView.getText().toString());
                orderForMeTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
                orderForFamilyTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                orderForOthersTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
            }
        });
        orderForFamilyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setWho_need_session(orderForFamilyTextView.getText().toString());
                orderForMeTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                orderForFamilyTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
                orderForOthersTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
            }
        });
        orderForOthersTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setWho_need_session(orderForOthersTextView.getText().toString());
                orderForMeTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                orderForFamilyTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                orderForOthersTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
            }
        });

        maleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setGender(maleTextView.getText().toString());
                maleTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
                FemaleTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
            }
        });
        FemaleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setGender(FemaleTextView.getText().toString());
                maleTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                FemaleTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
            }
        });

        marriedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setSocial_statue(marriedTextView.getText().toString());
                marriedTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
                singleTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
            }
        });
        singleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setSocial_statue(singleTextView.getText().toString());
                marriedTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                singleTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
            }
        });

        canWalkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setMove_level(canWalkTextView.getText().toString());
                canWalkTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
                sittingTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                canNotMoveTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));


            }
        });
        sittingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setMove_level(sittingTextView.getText().toString());
                canWalkTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                sittingTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
                canNotMoveTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));

            }
        });
        canNotMoveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setMove_level(canNotMoveTextView.getText().toString());
                canWalkTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                sittingTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                canNotMoveTextView.setBackgroundColor(getResources().getColor(R.color.yellow));

            }
        });

        noDiseaseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setHealth_problem(noDiseaseTextView.getText().toString());
                noDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
                diabeticTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                bloodPressureTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                heartPatientTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                otherDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
            }
        });
        diabeticTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setHealth_problem(diabeticTextView.getText().toString());
                noDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                diabeticTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
                bloodPressureTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                heartPatientTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                otherDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
            }
        });
        bloodPressureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setHealth_problem(bloodPressureTextView.getText().toString());
                noDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                diabeticTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                bloodPressureTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
                heartPatientTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                otherDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
            }
        });
        heartPatientTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setHealth_problem(heartPatientTextView.getText().toString());
                noDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                diabeticTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                bloodPressureTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                heartPatientTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
                otherDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
            }
        });
        otherDiseaseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setHealth_problem(otherDiseaseTextView.getText().toString());
                noDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                diabeticTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                bloodPressureTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                heartPatientTextView.setBackgroundColor(getResources().getColor(R.color.appcolor));
                otherDiseaseTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
            }
        });
        setInitialValues();
    }


    public void goBack(View view) {
        onBackPressed();
    }

    public void nextPage(View view) {
        order.setAge(String.valueOf(numberPicker.getValue()));
        Intent intent=new Intent(OrderM3algNowActivity.this, WhenPainStartActivity.class);
        intent.putExtra("order",order);
        startActivity(intent);
    }
    public void setInitialValues(){
        order=new VisitOrderPatient();
        order.setWho_need_session(orderForMeTextView.getText().toString());
        order.setGender(maleTextView.getText().toString());
        order.setSocial_statue(marriedTextView.getText().toString());
        order.setAge(String.valueOf(numberPicker.getValue()));
        order.setMove_level(canWalkTextView.getText().toString());
        order.setHealth_problem(noDiseaseTextView.getText().toString());
    }
}
