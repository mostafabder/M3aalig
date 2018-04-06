package com.asi.m3alig.M3algFilesWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.MainActivity;
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

public class TreatmentSessionReportActivity extends AppCompatActivity {

    private EditText et_subjective, et_objective, et_assessment, et_plan, et_longObj, et_shortObj, et_other, et_numberOfVisits;
    private RadioGroup radioButton;
    private String subjective, objective, assessment, plan, number_session, need_follow, long_obj,
                   short_obj, other;
    String id, patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(TreatmentSessionReportActivity.this, PreferenceUtilities.getLanguage(TreatmentSessionReportActivity.this));
        setContentView(R.layout.activity_treatment_session_report);

        et_subjective = (EditText) findViewById(R.id.et_subjective);
        et_objective = (EditText) findViewById(R.id.et_objective);
        et_assessment = (EditText) findViewById(R.id.et_assessment);
        et_plan = (EditText) findViewById(R.id.et_plan);
        et_numberOfVisits = (EditText) findViewById(R.id.et_numberOfVisits);
        /*et_longObj = (EditText) findViewById(R.id.et_longObj);
        et_shortObj = (EditText) findViewById(R.id.et_shortObj);
        et_other = (EditText) findViewById(R.id.et_other);*/
        radioButton = (RadioGroup) findViewById(R.id.radioButton);

        id = getIntent().getExtras().getString("visit_id");
        patientId = getIntent().getExtras().getString("patient_id");

    }

    public void saveReport(View view){

        String ok = validation();
        if(ok.equals("ok")) {

            Log.i("svRP", "enter");

            long_obj = "";
            short_obj = "";
            other = "";

            if(need_follow.equals("no")){
                number_session = "0";
            }

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<StartingVisit> call = apiService.doctorReport(
                    getSecret(getApplicationContext()),
                    getToken(getApplicationContext()),
                    subjective,
                    objective,
                    long_obj,
                    short_obj,
                    plan,
                    assessment,
                    need_follow,
                    number_session,
                    other,
                    patientId,
                    id);
            call.enqueue(new Callback<StartingVisit>() {
                @Override
                public void onResponse(Call<StartingVisit> call, Response<StartingVisit> response) {
                    //here if task successful
                    //check the respond body is null or not
                    //if body not null
                    if (response.body() != null) {
                        Log.i("sv", "not null");
                        //check if response code is successful or not
                        //if code successful
                        if (response.body().getCode().equals(FLAGE_CODE_SUCCSESS)) {
                            Log.i("sv", "200");
                            Intent intent = new Intent(TreatmentSessionReportActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("sv", "403");
                            //here if code not successful
                            try {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), getString(R.string.something_error), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Log.i("sv", "is null");
                        //here if body null
                        Toast.makeText(getApplicationContext(), getString(R.string.contact_services), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<StartingVisit> call, Throwable t) {
                    //here if task failed
                    Toast.makeText(getApplicationContext(), getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                    Log.e("ERROR", t.getMessage() + "   ");
                    Log.i("sv", "out");
                }
            });

        }else {
            Toast.makeText(getApplicationContext(), ok, Toast.LENGTH_LONG).show();
        }

    }

    private String validation(){

        int radioId = radioButton.getCheckedRadioButtonId();
        switch (radioId){
            case R.id.radio1:
                need_follow = "yes";
                break;
            case R.id.radio2:
                need_follow = "no";
                break;
        }

        subjective = et_subjective.getText().toString().trim();
        objective = et_objective.getText().toString().trim();
        assessment = et_assessment.getText().toString().trim();
        plan = et_plan.getText().toString().trim();
        number_session = et_numberOfVisits.getText().toString().trim();
        /*long_obj = et_longObj.getText().toString().trim();
        short_obj = et_shortObj.getText().toString().trim();
        other = et_other.getText().toString().trim();*/

        if(subjective.equals(""))
            return getString(R.string.subjective_complete);
        else if(objective.equals(""))
            return getString(R.string.objective_complete);
        else if(assessment.equals(""))
            return getString(R.string.assessment_complete);
        else if(plan.equals(""))
            return getString(R.string.plan_complete);
        else if(need_follow.equals("yes") && number_session.equals(""))
            return getString(R.string.visits_complete);
        else if(need_follow.equals("yes") && Integer.parseInt(number_session) <= 0)
            return getString(R.string.visits_wrong_complete);
        /*else if(long_obj.equals(""))
            return "من فضلك اكمل البيانات الهدف البعيد";
        else if(short_obj.equals(""))
            return "من فضلك اكمل البيانات الهدف القريب";*/

        return "ok";
    }

}
