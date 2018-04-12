package com.asi.m3alig.M3algFilesWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.PatientWork.FinishSessionActivity;
import com.asi.m3alig.PatientWork.NotesActivity;
import com.asi.m3alig.PatientWork.TreatmentSessionMain;
import com.asi.m3alig.R;
import com.asi.m3alig.Utility.PreferenceUtilities;

public class ScheduleAppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(ScheduleAppointmentsActivity.this, PreferenceUtilities.getLanguage(ScheduleAppointmentsActivity.this));
        setContentView(R.layout.activity_schedule_appointments);


    }


    public void goBack(View view) {
        onBackPressed();
    }

    public void viewOrdersTable(View view) {
        startActivity(new Intent(ScheduleAppointmentsActivity.this,TreatmentDoctorSessionMain.class));
    }


}

