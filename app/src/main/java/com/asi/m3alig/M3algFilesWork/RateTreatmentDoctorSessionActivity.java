package com.asi.m3alig.M3algFilesWork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.MainActivity;
import com.asi.m3alig.PatientWork.RateTreatmentSessionActivity;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.StartingVisit;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Utility.PreferenceUtilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;

public class RateTreatmentDoctorSessionActivity extends AppCompatActivity {

    private EditText rateReason;
    private RatingBar ratingBar;
    String id, patient_id;

    private LinearLayout ll_nextReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(RateTreatmentDoctorSessionActivity.this, PreferenceUtilities.getLanguage(RateTreatmentDoctorSessionActivity.this));
        setContentView(R.layout.activity_rate_treatment_doctor_session);

        ll_nextReport = (LinearLayout) findViewById(R.id.ll_nextReport);

        rateReason = (EditText) findViewById(R.id.et_rateReason);
        ratingBar =(RatingBar)findViewById(R.id.rating_bar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(ratingBar.getRating() >= 3){
                    rateReason.setVisibility(View.GONE);
                }else {
                    rateReason.setVisibility(View.VISIBLE);
                }

                ll_nextReport.setBackgroundColor(getResources().getColor(R.color.blue));
                ll_nextReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        makeSessionReport();
                    }
                });

            }
        });

        id = getIntent().getExtras().getString("visit_id");
        patient_id = getIntent().getExtras().getString("patient_id");

    }

    public void makeSessionReport() {
        float rate = ratingBar.getRating();
        String rateReasonMessage = rateReason.getText().toString().trim();
        if(rate <= 0.0){
            Toast.makeText(getApplicationContext(), R.string.please_rate_patient, Toast.LENGTH_LONG).show();
        }else if(rate < 3){
            if(!rateReasonMessage.equals("")){
                rateVisit(rateReasonMessage, rate);
                Intent intent = new Intent(RateTreatmentDoctorSessionActivity.this,TreatmentSessionReportActivity.class);
                intent.putExtra("visit_id", id);
                intent.putExtra("patient_id", patient_id);
                startActivity(intent);
            }else {
                rateReason.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), R.string.enter_reason_of_rate,Toast.LENGTH_SHORT).show();
            }
        }else {
            rateVisit("", rate);
            rateReason.setVisibility(View.GONE);
            Intent intent = new Intent(RateTreatmentDoctorSessionActivity.this,TreatmentSessionReportActivity.class);
            intent.putExtra("visit_id", id);
            intent.putExtra("patient_id", patient_id);
            startActivity(intent);
        }
    }

    private void rateVisit(String msg, float rate){

        Log.i("svRt", "enter");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RateTreatmentDoctorSessionActivity.this);


        String doctor_rate = rate+"";
        String doctor_reason = msg;

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<StartingVisit> call = apiService.doctorRateVisit(getSecret(getApplicationContext()), getToken(getApplicationContext()), doctor_rate, doctor_reason, id);
        call.enqueue(new Callback<StartingVisit>() {
            @Override
            public void onResponse(Call<StartingVisit> call, Response<StartingVisit> response) {
                //here if task successful
                //check the respond body is null or not
                //if body not null
                if(response.body() != null){
                    Log.i("sv", "not null");
                    //check if response code is successful or not
                    //if code successful
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS)){
                        Log.i("sv", "200");
                        Toast.makeText(getApplicationContext(), getString(R.string.rating_done), Toast.LENGTH_SHORT).show();
                    }else {
                        Log.i("sv", "403");
                        //here if code not successful
                        try{
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), getString(R.string.something_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Log.i("sv", "is null");
                    //here if body null
                    Toast.makeText(getApplicationContext(), getString(R.string.contact_services), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<StartingVisit> call, Throwable t) {
                //here if task failed
                Toast.makeText(getApplicationContext(), getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
                Log.i("sv", "out");
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
