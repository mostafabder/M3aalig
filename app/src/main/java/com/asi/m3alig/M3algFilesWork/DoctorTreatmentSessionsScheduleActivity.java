package com.asi.m3alig.M3algFilesWork;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.m3alig.Adapters.M3alig.DoctorNextVisitScheduleRecyclerViewAdapter;
import com.asi.m3alig.Adapters.M3alig.DoctorOrdersRecyclerViewAdapter;
import com.asi.m3alig.MainActivity;
import com.asi.m3alig.Models.DoctorSingleVisit;
import com.asi.m3alig.Models.OrderDetails;
import com.asi.m3alig.Models.Orders;
import com.asi.m3alig.Models.SingleOrder;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.DoctorVisits;
import com.asi.m3alig.Responses.DoctorVisitsOrder;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;

public class DoctorTreatmentSessionsScheduleActivity extends AppCompatActivity {

    private ArrayList<SingleOrder> mOrders;
    //private ArrayList<OrderDetails> mOrderDetails;
    private RecyclerView mDoctorOrdersRV;
    private DoctorNextVisitScheduleRecyclerViewAdapter mDoctorVisitsAdapter;
    private LinearLayoutManager layoutManager;
    private TextView tv_emptyOrdersText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_treatment_sessions_schedule);

        mOrders = new ArrayList<>();
        //mOrderDetails = new ArrayList<>();
        mDoctorOrdersRV = (RecyclerView) findViewById(R.id.rv_doctorNextVisitSchedule);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDoctorOrdersRV.setLayoutManager(layoutManager);

        tv_emptyOrdersText = (TextView) findViewById(R.id.tv_emptyOrdersText);

        prepareListData();

    }

    private void prepareListData(){

        Log.i("schedule", "enter");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DoctorVisits> call = apiService.doctorNextVisit(getSecret(getApplicationContext()), getToken(getApplicationContext()));
        call.enqueue(new Callback<DoctorVisits>() {
            @Override
            public void onResponse(Call<DoctorVisits> call, Response<DoctorVisits> response) {
                //here if task successful
                //check the respond body is null or not
                //if body not null
                if(response.body() != null){
                    Log.i("schedule", "not null");
                    //check if response code is successful or not
                    //if code successful
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS)){
                        Log.i("schedule", "200");
                        //set up arrayList
                        ArrayList<DoctorSingleVisit> orders = (ArrayList<DoctorSingleVisit>) response.body().getData();
                        Log.i("schedule", "200 "+orders.size());
                        for(int i=0; i<orders.size(); i++) {
                            String visitType = orders.get(i).getReason();
                            String visitDate = orders.get(i).getDate();
                            String visitTime = orders.get(i).getTime();
                            String patientName = orders.get(i).getPatient().getName();
                            String id = orders.get(i).getId();

                            Log.i("All", visitType+"\n"+visitDate+"\n"+visitTime+"\n"+patientName+"\n"+id);

                            //OrderDetails orderDetails = new OrderDetails(
                            //doctorName, doctorJob, reason, prescription, farObject, nearObject, plan, medicine);
                            SingleOrder singleOrder = new SingleOrder(visitType, visitDate, visitTime, patientName, id);
                            mOrders.add(singleOrder);
                            //mOrderDetails.add(orderDetails);
                        }
                        Log.i("schedule", mOrders.size()+"final correct");
                        //set up adapter
                        mDoctorVisitsAdapter = new DoctorNextVisitScheduleRecyclerViewAdapter(
                                DoctorTreatmentSessionsScheduleActivity.this, mOrders);
                        mDoctorOrdersRV.setAdapter(mDoctorVisitsAdapter);
                        Log.i("size", mOrders.size()+"");
                        if(mOrders.size() > 0){
                            tv_emptyOrdersText.setVisibility(View.GONE);
                        }
                    }else {
                        Log.i("schedule", "403");
                        //here if code not successful
                        try{
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), getString(R.string.something_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Log.i("schedule", "is null");
                    //here if body null
                    Toast.makeText(getApplicationContext(), getString(R.string.contact_services), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DoctorVisits> call, Throwable t) {
                //here if task failed
                Toast.makeText(getApplicationContext(), getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
                Log.i("schedule", "out");
            }
        });
    }

}
