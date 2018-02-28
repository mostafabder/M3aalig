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
import android.widget.TextView;
import android.widget.Toast;

import com.asi.m3alig.Adapters.Patient.PatientHealthSummryAdapter;
import com.asi.m3alig.Models.Visit;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.VisitResponse;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;

public class MyHealthSummaryActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Visit> data;
    private RecyclerView.LayoutManager layoutManager;
    private TextView summary;
    int max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_health_summary);
        summary=(TextView)findViewById(R.id.health_sum_last_desc);
        recyclerView = (RecyclerView) findViewById(R.id.health_sum_recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getPrevVisists();

    }
    public void goBack(View view) {
        onBackPressed();
    }

    private void getPrevVisists(){
        final ProgressDialog progressDialog=new ProgressDialog(MyHealthSummaryActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("TOKEN,SECRET",getToken(MyHealthSummaryActivity.this)+"  "+getSecret(MyHealthSummaryActivity.this));


        Call<VisitResponse> call=apiService.prevVisitPatient(getToken(MyHealthSummaryActivity.this),getSecret(MyHealthSummaryActivity.this));
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
                        Log.e("Size",String.valueOf(response.body().getData().size())+"    ");
                        data=new ArrayList<Visit>(response.body().getData());
                        adapter = new PatientHealthSummryAdapter(data);
                        recyclerView.setAdapter(adapter);
                        if(response.body().getData().size()>0)
                            max= Integer.valueOf(response.body().getData().get(0).getId());
                        for(int i =0;i<response.body().getData().size();i++)
                            max=Math.max(max,Integer.valueOf(response.body().getData().get(i).getId()));
                        for(int i=0;i<response.body().getData().size();i++)
                        {
                            if(response.body().getData().get(i).getId().equals(String.valueOf(max)))
                            {
                                if(response.body().getData().get(i).getReport()!=null)
                                    summary.setText(response.body().getData().get(i).getReport().getSummary());
                                break;
                            }
                        }
                    }
                    else  {
                        try{
                            Toast.makeText(MyHealthSummaryActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(MyHealthSummaryActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(MyHealthSummaryActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<VisitResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MyHealthSummaryActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

}
