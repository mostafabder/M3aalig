package com.asi.m3alig.Adapters.Patient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.asi.m3alig.Models.Visit;
import com.asi.m3alig.PatientWork.m3aligSideScreens.MyHealthSummaryActivity;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.NormalResponse;
import com.asi.m3alig.Responses.VisitResponse;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Tools.Utils;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;


public class PatientTreatmentSessionsScheduleAdapter extends RecyclerView.Adapter<PatientTreatmentSessionsScheduleAdapter.MyViewHolder> {

    private ArrayList<Visit> dataSet;
    Activity activity;

    public PatientTreatmentSessionsScheduleAdapter(ArrayList<Visit> data, Activity activity) {
        this.dataSet = data;
        this.activity = activity;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_treatment_session, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.headerLayout.getVisibility() == View.GONE) {
                    Utils.expand(holder.headerLayout);
                    holder.Date=dataSet.get(listPosition).getDate();
                    holder.Time=dataSet.get(listPosition).getTime();
                } else {
                    Utils.collapse(holder.headerLayout);

                }
            }
        });
        holder.changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();


                DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                holder.Date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                holder.checked=true;

                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                //
            }

        });
        holder.changeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                holder.Time = hourOfDay + ":" + minute;
                                holder.checked=true;
                            }
                        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });
        holder.confirmChangeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.dateTextView.setText(holder.Date);
                holder.timeTextView.setText(holder.Time);
                if (holder.headerLayout.getVisibility() == View.VISIBLE) {
                    Utils.collapse(holder.headerLayout);
                }
                if(holder.checked){
                    updateApi(holder.Date,holder.Time,dataSet.get(listPosition));
                    holder.checked=false;
                }

            }
        });
        holder.cancelChangeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.headerLayout.getVisibility() == View.VISIBLE) {
                    Utils.collapse(holder.headerLayout);

                }
            }
        });
        init(holder,dataSet.get(listPosition));
    }
   private void init(MyViewHolder holder, Visit visit)
    {
        holder.typeTextView.setText(visit.getReason());
        if(visit.getDoctor()!=null)
        holder.nameTextView.setText(visit.getDoctor().getName());
        holder.dateTextView.setText(visit.getDate());
        holder.timeTextView.setText(visit.getTime());
        holder.Date=visit.getDate();
        holder.Time=visit.getTime();
    }
   private void updateApi(String date,String time,Visit visit){
        if(date==null)
            date=visit.getDate();
        if(time==null)
            date=visit.getTime();
        final ProgressDialog progressDialog=new ProgressDialog(activity);
        progressDialog.setMessage(activity.getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("TOKEN,SECRET",getToken(activity)+"  "+getSecret(activity));
        Log.e("date,time",date+" "+time);
        Log.e("visit_id",visit.getId()+"  ");

        Call<NormalResponse> call=apiService.updateNextVisit(getToken(activity),getSecret(activity),date,time,visit.getId());
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
                        Log.e("Status","Updated successfully");
                    }
                    else  {
                        try{
                            Toast.makeText(activity,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(activity,activity.getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(activity,activity.getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<NormalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity,activity.getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout headerLayout, changeTime, changeDate;
        ImageView editImageView, cancelChangeImageView, confirmChangeImageView;
        TextView timeTextView, dateTextView,typeTextView,nameTextView;
        String Date;
        String Time;
        Boolean checked=false;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.headerLayout = (LinearLayout) itemView.findViewById(R.id.next_treatment_sessions_edit_layout);
            this.editImageView = (ImageView) itemView.findViewById(R.id.edit_image_click);

            this.changeTime = (LinearLayout) itemView.findViewById(R.id.change_time_button);
            this.changeDate = (LinearLayout) itemView.findViewById(R.id.change_date_button);
            this.cancelChangeImageView = (ImageView) itemView.findViewById(R.id.cancel_change_button);
            this.confirmChangeImageView = (ImageView) itemView.findViewById(R.id.confirm_change_button);

            this.timeTextView = (TextView) itemView.findViewById(R.id.time_next_visit);
            this.dateTextView = (TextView) itemView.findViewById(R.id.date_next_visit);
            typeTextView=(TextView)itemView.findViewById(R.id.next_visit_type);
            nameTextView=(TextView)itemView.findViewById(R.id.next_visit_name);
        }
    }
}
