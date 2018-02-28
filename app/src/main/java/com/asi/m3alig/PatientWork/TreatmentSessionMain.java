package com.asi.m3alig.PatientWork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.asi.m3alig.R;

import com.asi.m3alig.Responses.NormalResponse;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Utility.sendMessageToAdminDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;

public class TreatmentSessionMain extends AppCompatActivity {
    String visit_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_treatment_session_main);
        visit_id =getIntent().getStringExtra("visit_id");
    }





    public void sendToAdmin(View view) {
        sendMessageToAdminDialog alert = new sendMessageToAdminDialog();
        alert.showDialog(TreatmentSessionMain.this, "OTP has been sent to your Mail ");
    }

    public void cancelSession(View view) {

        startActivity(new Intent(TreatmentSessionMain.this,FinishSessionActivity.class));
    }
    public void cancel_visit(){
        final ProgressDialog progressDialog=new ProgressDialog(TreatmentSessionMain.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("TOKEN,SECRET",getToken(TreatmentSessionMain.this)+"  "+getSecret(TreatmentSessionMain.this));
        Log.e("id",visit_id+"   ");

        Call<NormalResponse> call=apiService.cancelOrderPatient(getToken(TreatmentSessionMain.this),getSecret(TreatmentSessionMain.this), visit_id);
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
                        Toast.makeText(TreatmentSessionMain.this,getString(R.string.succes_added),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(TreatmentSessionMain.this,FinishSessionActivity.class);
                        startActivity(intent);
                    }
                    else  {
                        try{
                            Toast.makeText(TreatmentSessionMain.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(TreatmentSessionMain.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else Toast.makeText(TreatmentSessionMain.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<NormalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TreatmentSessionMain.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

}
