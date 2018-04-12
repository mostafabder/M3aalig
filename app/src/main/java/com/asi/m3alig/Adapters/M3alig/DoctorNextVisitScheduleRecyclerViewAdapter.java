package com.asi.m3alig.Adapters.M3alig;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.m3alig.M3algFilesWork.DoctorTreatmentSessionsScheduleActivity;
import com.asi.m3alig.Models.DoctorSingleVisit;
import com.asi.m3alig.Models.OrderDetails;
import com.asi.m3alig.Models.SingleOrder;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.ChangeDateOrTime;
import com.asi.m3alig.Responses.DoctorVisits;
import com.asi.m3alig.Responses.NormalResponse;
import com.asi.m3alig.Responses.StartingVisit;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;

/**
 * Created by Zerir on 2/9/2018.
 */

public class DoctorNextVisitScheduleRecyclerViewAdapter extends RecyclerView.Adapter<DoctorNextVisitScheduleRecyclerViewAdapter.DoctorVisitsViewHolder>{

    private Context context;
    private ArrayList<SingleOrder> orders;
    //private ArrayList<OrderDetails> orderDetails;

    public DoctorNextVisitScheduleRecyclerViewAdapter(Context context, ArrayList<SingleOrder> orders/*, ArrayList<OrderDetails> orderDetails*/) {
        this.context = context;
        this.orders = orders;
        //this.orderDetails = orderDetails;
    }


    public class DoctorVisitsViewHolder extends RecyclerView.ViewHolder {

        TextView visitType, visitDate, visitTIme, patientName;
        ImageView edit;
        ConstraintLayout detailsLayout;
        ImageView iv_close, iv_done;
        Button bt_changeDay, bt_changeTime;
        LinearLayout newChanges;
        ImageView iv_cancelChanges, iv_saveChanges;
        EditText et_day, et_month, et_year;
        TextView tv_f1, tv_f2;
        boolean timeOrDay;
        String time, day;


        public DoctorVisitsViewHolder(View itemView) {
            super(itemView);
            visitType = (TextView) itemView.findViewById(R.id.tv_visitType);
            visitDate = (TextView) itemView.findViewById(R.id.tv_visitDate);
            visitTIme = (TextView) itemView.findViewById(R.id.tv_visitTime);
            patientName = (TextView) itemView.findViewById(R.id.tv_patientName);
            edit = (ImageView) itemView.findViewById(R.id.iv_edit);

            detailsLayout = (ConstraintLayout) itemView.findViewById(R.id.detailsLayout);
            iv_close = (ImageView) itemView.findViewById(R.id.iv_close);
            iv_done = (ImageView) itemView.findViewById(R.id.iv_done);
            bt_changeDay = (Button) itemView.findViewById(R.id.bt_changeDay);
            bt_changeTime = (Button) itemView.findViewById(R.id.bt_changeTime);

            newChanges = (LinearLayout) itemView.findViewById(R.id.newChanges);
            iv_cancelChanges = (ImageView) itemView.findViewById(R.id.iv_cancelChanges);
            iv_saveChanges = (ImageView) itemView.findViewById(R.id.iv_saveChanges);
            et_day = (EditText) itemView.findViewById(R.id.et_changes);
            et_month = (EditText) itemView.findViewById(R.id.et_changesM);
            et_year = (EditText) itemView.findViewById(R.id.et_changesY);
            tv_f1 = (TextView) itemView.findViewById(R.id.f1);
            tv_f2 = (TextView) itemView.findViewById(R.id.f2);
            timeOrDay = true; //true for change day
            time = "";
            day = "";

        }

    }

    @Override
    public DoctorVisitsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.doctor_visits_schedule_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new DoctorVisitsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DoctorVisitsViewHolder doctorVisitsViewHolder, final int position) {

        doctorVisitsViewHolder.visitType.setText(orders.get(position).getType());
        doctorVisitsViewHolder.visitDate.setText(orders.get(position).getDate());
        doctorVisitsViewHolder.visitTIme.setText(orders.get(position).getTime());
        doctorVisitsViewHolder.patientName.setText(orders.get(position).getPatientName());
        doctorVisitsViewHolder.iv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String visit_id = orders.get(position).getId();
                String oldDay = doctorVisitsViewHolder.visitDate.getText().toString().trim();
                String oldTime = doctorVisitsViewHolder.visitTIme.getText().toString().trim();
                String day = doctorVisitsViewHolder.day;
                String time = doctorVisitsViewHolder.time;
                Log.i("hereAgain", visit_id+" \n"+oldDay+" \n"+oldTime+" \n"+day+" \n"+time);
                apiValidation(visit_id, oldDay, oldTime, day, time, doctorVisitsViewHolder.newChanges);
            }
        });
        doctorVisitsViewHolder.iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorVisitsViewHolder.detailsLayout.setVisibility(View.GONE);
            }
        });
        doctorVisitsViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorVisitsViewHolder.detailsLayout.setVisibility(View.VISIBLE);
            }
        });
        doctorVisitsViewHolder.bt_changeDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorVisitsViewHolder.newChanges.setVisibility(View.VISIBLE);
                if(doctorVisitsViewHolder.timeOrDay) {
                    if (Locale.getDefault().getLanguage().equals("en")) {
                        doctorVisitsViewHolder.et_day.setHint("dd");
                        doctorVisitsViewHolder.tv_f1.setText("/");
                        doctorVisitsViewHolder.et_month.setHint("mm");
                        doctorVisitsViewHolder.tv_f2.setText("/");
                        doctorVisitsViewHolder.et_year.setHint("yyyy");
                    }else if(Locale.getDefault().getLanguage().equals("ar")){
                        doctorVisitsViewHolder.et_day.setHint("يوم");
                        doctorVisitsViewHolder.tv_f1.setText("/");
                        doctorVisitsViewHolder.et_month.setHint("شهر");
                        doctorVisitsViewHolder.tv_f2.setText("/");
                        doctorVisitsViewHolder.et_year.setHint("سنة");
                    }
                }else {
                    doctorVisitsViewHolder.timeOrDay = true;
                    if(Locale.getDefault().getLanguage().equals("en")){
                        doctorVisitsViewHolder.et_day.setHint("dd");
                        doctorVisitsViewHolder.et_day.setText("");
                        doctorVisitsViewHolder.tv_f1.setText("/");
                        doctorVisitsViewHolder.et_month.setHint("mm");
                        doctorVisitsViewHolder.et_month.setText("");
                        doctorVisitsViewHolder.tv_f2.setText("/");
                        doctorVisitsViewHolder.et_year.setHint("yyyy");
                        doctorVisitsViewHolder.et_year.setText("");
                    }else if(Locale.getDefault().getLanguage().equals("ar")){
                        doctorVisitsViewHolder.et_day.setHint("يوم");
                        doctorVisitsViewHolder.et_day.setText("");
                        doctorVisitsViewHolder.tv_f1.setText("/");
                        doctorVisitsViewHolder.et_month.setHint("شهر");
                        doctorVisitsViewHolder.et_month.setText("");
                        doctorVisitsViewHolder.tv_f2.setText("/");
                        doctorVisitsViewHolder.et_year.setHint("سنة");
                        doctorVisitsViewHolder.et_year.setText("");
                    }
                }
            }
        });
        doctorVisitsViewHolder.bt_changeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorVisitsViewHolder.newChanges.setVisibility(View.VISIBLE);
                if(doctorVisitsViewHolder.timeOrDay){
                    doctorVisitsViewHolder.timeOrDay = false;
                    if(Locale.getDefault().getLanguage().equals("en")){
                        doctorVisitsViewHolder.et_day.setHint("hh");
                        doctorVisitsViewHolder.et_day.setText("");
                        doctorVisitsViewHolder.tv_f1.setText(":");
                        doctorVisitsViewHolder.et_month.setHint("mm");
                        doctorVisitsViewHolder.et_month.setText("");
                        doctorVisitsViewHolder.tv_f2.setText(":");
                        doctorVisitsViewHolder.et_year.setHint("ss");
                        doctorVisitsViewHolder.et_year.setText("");
                    }else if(Locale.getDefault().getLanguage().equals("ar")){
                        doctorVisitsViewHolder.et_day.setHint("س");
                        doctorVisitsViewHolder.et_day.setText("");
                        doctorVisitsViewHolder.tv_f1.setText(":");
                        doctorVisitsViewHolder.et_month.setHint("د");
                        doctorVisitsViewHolder.et_month.setText("");
                        doctorVisitsViewHolder.tv_f2.setText(":");
                        doctorVisitsViewHolder.et_year.setHint("ث");
                        doctorVisitsViewHolder.et_year.setText("");
                    }
                }else {
                    if(Locale.getDefault().getLanguage().equals("en")){
                        doctorVisitsViewHolder.et_day.setHint("hh");
                        doctorVisitsViewHolder.tv_f1.setText(":");
                        doctorVisitsViewHolder.et_month.setHint("mm");
                        doctorVisitsViewHolder.tv_f2.setText(":");
                        doctorVisitsViewHolder.et_year.setHint("ss");
                    }else if(Locale.getDefault().getLanguage().equals("ar")){
                        doctorVisitsViewHolder.et_day.setHint("س");
                        doctorVisitsViewHolder.tv_f1.setText(":");
                        doctorVisitsViewHolder.et_month.setHint("د");
                        doctorVisitsViewHolder.tv_f2.setText(":");
                        doctorVisitsViewHolder.et_year.setHint("ث");
                    }
                }
            }
        });
        doctorVisitsViewHolder.iv_cancelChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorVisitsViewHolder.newChanges.setVisibility(View.GONE);
            }
        });
        doctorVisitsViewHolder.iv_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ok = validation(doctorVisitsViewHolder.et_day,
                        doctorVisitsViewHolder.et_month,
                        doctorVisitsViewHolder.et_year,
                        doctorVisitsViewHolder.timeOrDay);

                Log.i("OKAYBITCH", ok);
                if(ok.equals("ok")){
                    if(doctorVisitsViewHolder.timeOrDay) {
                        Toast.makeText(context, R.string.save_new_day_now, Toast.LENGTH_LONG).show();
                        doctorVisitsViewHolder.day =
                                doctorVisitsViewHolder.et_year.getText().toString().trim() + "-" +
                                        doctorVisitsViewHolder.et_month.getText().toString().trim() + "-" +
                                        doctorVisitsViewHolder.et_day.getText().toString().trim();
                        Log.i("day", doctorVisitsViewHolder.day);
                    }else {
                        Toast.makeText(context, R.string.save_new_hour_now, Toast.LENGTH_LONG).show();
                        doctorVisitsViewHolder.time =
                                doctorVisitsViewHolder.et_day.getText().toString().trim() + ":" +
                                        doctorVisitsViewHolder.et_month.getText().toString().trim() + ":" +
                                        doctorVisitsViewHolder.et_year.getText().toString().trim();
                        Log.i("time", doctorVisitsViewHolder.time);
                    }
                    doctorVisitsViewHolder.newChanges.setVisibility(View.GONE);
                    doctorVisitsViewHolder.et_day.setText("");
                    doctorVisitsViewHolder.et_month.setText("");
                    doctorVisitsViewHolder.et_year.setText("");
                }else{
                    Toast.makeText(context, ok, Toast.LENGTH_LONG).show();
                    /*doctorVisitsViewHolder.time =
                            doctorVisitsViewHolder.et_day.getText().toString().trim() + ":" +
                                    doctorVisitsViewHolder.et_month.getText().toString().trim() + ":" +
                                    doctorVisitsViewHolder.et_year.getText().toString().trim() + ":" ;*/
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orders == null) return 0;
        return orders.size();
    }

    private void changeDateOrTime(String id, String day, String time){

        Log.i("adpater", "enter");


        Log.i("adpater", id);
        Log.i("adpater", day);
        Log.i("adpater", time);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ChangeDateOrTime> call = apiService.doctorChangeDateOrTime(getSecret(context), getToken(context),time, day, id);
        call.enqueue(new Callback<ChangeDateOrTime>() {
            @Override
            public void onResponse(Call<ChangeDateOrTime> call, Response<ChangeDateOrTime> response) {
                //here if task successful
                //check the respond body is null or not
                //if body not null
                if(response.body() != null){
                    Log.i("adpater", "not null");
                    //check if response code is successful or not
                    //if code successful
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS)){
                        Log.i("adpater", "200");

                    }else {
                        Log.i("adpater", "403");
                        //here if code not successful
                        try{
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(context, context.getString(R.string.something_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Log.i("adpater", "is null");
                    //here if body null
                    Toast.makeText(context, context.getString(R.string.contact_services), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ChangeDateOrTime> call, Throwable t) {
                //here if task failed
                Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
                Log.i("adpater", "out");
            }
        });
    }

    private String validation(EditText day, EditText month, EditText year, boolean timeOrDay){
        String currentYear = ""+Calendar.getInstance().get(Calendar.YEAR);
        String currentMonth = ""+(1+Calendar.getInstance().get(Calendar.MONTH));
        String currentDay = ""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String currentMin = ""+Calendar.getInstance().get(Calendar.MINUTE);
        String currentHour = "";
        int amORpm = Calendar.getInstance().get(Calendar.AM_PM);
        switch (amORpm){
            case 0:
                currentHour = ""+Calendar.getInstance().get(Calendar.HOUR);
                break;
            case 1:
                currentHour = ""+(12+Calendar.getInstance().get(Calendar.HOUR));
                break;
        }
        Log.i("allDate", currentYear+" y "+currentMonth+" m "+currentDay+" d "+currentMin+" min "+currentHour+" h");
        String dateDay = day.getText().toString().trim();
        String dateMonth = month.getText().toString().trim();
        String dateYear = year.getText().toString().trim();
        if(timeOrDay) {
            if (dateDay.equals("")) {
                return context.getString(R.string.please_enter_day);
            }
            if (dateMonth.equals("")) {
                return context.getString(R.string.please_enter_month);
            }
            if (dateYear.equals("")) {
                return context.getString(R.string.please_enter_year);
            }

            if (!dateDay.equals("")) {
                if (dateDay.length() < 2) {
                    return context.getString(R.string.please_enter_zero);
                }
            }
            if (!dateMonth.equals("")){
                if (dateMonth.length() < 2) {
                    return context.getString(R.string.please_enter_zero);
                }
            }
            if(!dateYear.equals("")) {
                if (dateYear.length() < 2) {
                    return context.getString(R.string.please_enter_zero);
                }
            }

            if(Integer.parseInt(dateYear) < Integer.parseInt(currentYear)){
                return context.getString(R.string.cant_enter_low_year);
            } else {
                if (Integer.parseInt(dateMonth) < Integer.parseInt(currentMonth)) {
                    return context.getString(R.string.cant_enter_low_month);
                } else {
                    if (Integer.parseInt(dateDay) < Integer.parseInt(currentDay)) {
                        return context.getString(R.string.cant_enter_low_day);
                    }
                }

                if(!dateMonth.equals("")){
                    if (Integer.parseInt(dateMonth) > 12){
                        return context.getString(R.string.cant_enter_high_month);
                    }else {
                        if(dateMonth.equals("4")||dateMonth.equals("6")||
                                dateMonth.equals("9")||dateMonth.equals("11")){
                            if (Integer.parseInt(dateDay) > 31){
                                return context.getString(R.string.cant_enter_more30);
                            }
                        }else if(dateMonth.equals("2")){
                            if (Integer.parseInt(dateDay) > 29){
                                return context.getString(R.string.cant_enter_more29);
                            }
                        }
                    }
                }

                if(!dateDay.equals("")){
                    if (Integer.parseInt(dateDay) > 31){
                        return context.getString(R.string.cant_enter_high_day);
                    }
                }
            }
        }else {
            if(dateDay.equals("")){
                return context.getString(R.string.enter_hour);
            }
            if(dateMonth.equals("")){
                return context.getString(R.string.enter_min);
            }
            if(dateYear.equals("")){
                return context.getString(R.string.enter_second);
            }

            if(!dateYear.equals("")){
                if (Integer.parseInt(dateYear) >= 60 || dateYear.length() > 2){
                    return context.getString(R.string.cant_enter_high_seconds);
                }else {
                    if(dateYear.length() < 2){
                        return context.getString(R.string.please_enter_zero);
                    }
                }
            }

            if(!dateMonth.equals("")){
                if (Integer.parseInt(dateMonth) >= 60){
                    return context.getString(R.string.cant_enter_high_min);
                }else {
                    if(dateMonth.length() < 2){
                        return context.getString(R.string.please_enter_zero);
                    }
                }
            }

            if(!dateDay.equals("")){
                if (Integer.parseInt(dateDay) > 23){
                    return context.getString(R.string.cant_enter_high_hours);
                }else {
                    if(dateDay.length() < 2){
                        return context.getString(R.string.please_enter_zero);
                    }
                }
            }

        }
        return "ok";
    }

    private void apiValidation(String id, String oldDay, String oldTime, String day, String time, LinearLayout linearLayout){
        if(day.equals("")){
            if(time.equals("")){
                Toast.makeText(context, R.string.nothing_changes, Toast.LENGTH_LONG).show();
            }else {
                if(time.equals(oldTime)){
                    Toast.makeText(context, R.string.nothing_changes, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, R.string.just_hour, Toast.LENGTH_LONG).show();
                    changeDateOrTime(id, oldDay, time);
                    linearLayout.setVisibility(View.GONE);
                }
            }
        }else {
            if(day.equals(oldDay)){
                if(time.equals("")){
                    Toast.makeText(context, R.string.nothing_changes, Toast.LENGTH_LONG).show();
                }else {
                    if(time.equals(oldTime)){
                        Toast.makeText(context, R.string.nothing_changes, Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(context, R.string.just_hour, Toast.LENGTH_LONG).show();
                        //changeDateOrTime(id, oldDay, time);
                        //linearLayout.setVisibility(View.GONE);
                    }
                }
            }else{
                if(time.equals("")){
                    Toast.makeText(context, R.string.just_day, Toast.LENGTH_LONG).show();
                    //changeDateOrTime(id, day, oldTime);
                    //linearLayout.setVisibility(View.GONE);
                }else {
                    if(time.equals(oldTime)){
                        Toast.makeText(context, R.string.just_day, Toast.LENGTH_LONG).show();
                        //changeDateOrTime(id, day, oldTime);
                        //linearLayout.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(context, R.string.day_and_hour, Toast.LENGTH_LONG).show();
                        //changeDateOrTime(id, day, time);
                        //linearLayout.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

}
