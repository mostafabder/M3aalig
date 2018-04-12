package com.asi.m3alig.PatientWork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.Models.Visit;
import com.asi.m3alig.Responses.FinishVisitResponse;
import com.asi.m3alig.Responses.NormalResponse;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Utility.Constants;
import com.asi.m3alig.MainActivity;
import com.asi.m3alig.R;
import com.asi.m3alig.Utility.PreferenceUtilities;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;

public class RateTreatmentSessionActivity extends AppCompatActivity {
FancyButton finishRate;
    Visit  currentVisit;
    RatingBar ratingBar;
    EditText reason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(RateTreatmentSessionActivity.this, PreferenceUtilities.getLanguage(RateTreatmentSessionActivity.this));
        setContentView(R.layout.activity_rate_treatment_session);
        currentVisit=(Visit)getIntent().getSerializableExtra("visit");
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        finishRate = (FancyButton) findViewById(R.id.finish_rate_button);
        reason=(EditText)findViewById(R.id.et_reason_rate);
        finishRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ratingBar.getRating()<3&&reason.getText().toString().trim().equals(""))
                {
                    Toast.makeText(RateTreatmentSessionActivity.this, getString(R.string.enter_reason_of_rate), Toast.LENGTH_SHORT).show();
                }
                else{
                    rateVisit(currentVisit.getId());
                }

            }
        });
    }
    private void rateVisit(String id) {
        final ProgressDialog progressDialog=new ProgressDialog(RateTreatmentSessionActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("TOKEN,SECRET",getToken(RateTreatmentSessionActivity.this)+"  "+getSecret(RateTreatmentSessionActivity.this));
        Log.e("id",currentVisit.getId()+"   ");

        Call<NormalResponse> call=apiService.rateVisit(getToken(RateTreatmentSessionActivity.this),getSecret(RateTreatmentSessionActivity.this)
                ,String.valueOf(ratingBar.getRating()),reason.getText().toString(), id);
        call.enqueue(new Callback<NormalResponse>() {
            @Override
            public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        Intent intent=new Intent(RateTreatmentSessionActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  {
                        try{
                            Toast.makeText(RateTreatmentSessionActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(RateTreatmentSessionActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else Toast.makeText(RateTreatmentSessionActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<NormalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RateTreatmentSessionActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });

    }

}
