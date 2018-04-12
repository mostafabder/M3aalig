package com.asi.m3alig.PatientWork.m3aligSideScreens;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.asi.m3alig.Adapters.Patient.PatientTreatmentSessionsScheduleAdapter;
import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.Models.TestModel;
import com.asi.m3alig.Models.Visit;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.VisitResponse;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Tools.Connection.ServerTool;
import com.asi.m3alig.Utility.PreferenceUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;

public class TreatmentSessionsScheduleActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Visit> data;
    private RecyclerView.LayoutManager layoutManager;

    private ImageView ivBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(TreatmentSessionsScheduleActivity.this, PreferenceUtilities.getLanguage(TreatmentSessionsScheduleActivity.this));
        setContentView(R.layout.activity_treatment_sessions_schedule);
        recyclerView = (RecyclerView) findViewById(R.id.next_treatment_sessions_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getNextVisits();

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


                    adapter = new PatientTreatmentSessionsScheduleAdapter(data,TreatmentSessionsScheduleActivity.this);
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

    public void getNextVisits(){
        final ProgressDialog progressDialog=new ProgressDialog(TreatmentSessionsScheduleActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("TOKEN,SECRET",getToken(TreatmentSessionsScheduleActivity.this)+"  "+getSecret(TreatmentSessionsScheduleActivity.this));

        Call<VisitResponse> call=apiService.nextVisitPatient(getToken(TreatmentSessionsScheduleActivity.this),getSecret(TreatmentSessionsScheduleActivity.this));
        call.enqueue(new Callback<VisitResponse>() {
            @Override
            public void onResponse(Call<VisitResponse> call, Response<VisitResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {

                        data=new ArrayList<Visit>(response.body().getData());
                        adapter = new PatientTreatmentSessionsScheduleAdapter(data,TreatmentSessionsScheduleActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                    else  {
                        try{
                            Toast.makeText(TreatmentSessionsScheduleActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(TreatmentSessionsScheduleActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(TreatmentSessionsScheduleActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<VisitResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TreatmentSessionsScheduleActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }
}
