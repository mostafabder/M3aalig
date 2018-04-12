package com.asi.m3alig.Adapters.M3alig;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.m3alig.LoginActivity;
import com.asi.m3alig.MainActivity;
import com.asi.m3alig.Models.OrderDetails;
import com.asi.m3alig.Models.SingleOrder;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.DoctorVisitsOrder;
import com.asi.m3alig.Responses.LoginResponse;
import com.asi.m3alig.Responses.NormalResponse;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Utility.Constants;
import com.asi.m3alig.Utility.SQLiteHandler;
import com.asi.m3alig.Utility.SessionManager;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.M3ALG_TYPE;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;

/**
 * Created by Zerir on 2/8/2018.
 */

public class DoctorOrdersRecyclerViewAdapter extends RecyclerView.Adapter<DoctorOrdersRecyclerViewAdapter.DoctorOrdersViewHolder>{

    private Context context;
    private ArrayList<SingleOrder> orders;
    private ArrayList<OrderDetails> orderDetails;

    public DoctorOrdersRecyclerViewAdapter(Context context, ArrayList<SingleOrder> orders, ArrayList<OrderDetails> orderDetails) {
        this.context = context;
        this.orders = orders;
        this.orderDetails = orderDetails;
    }

    public class DoctorOrdersViewHolder extends RecyclerView.ViewHolder {

        TextView orderId, orderDate, orderTime;
        ImageView rowDown;
        ConstraintLayout detailsLayout;
        TextView tv_whenPainStart, tv_painPlace, tv_patientAddress, tv_street, tv_city, tv_patientName, tv_patientNumber;
        ImageView iv_rowUp, iv_rowLeft1, iv_rowLeft2, iv_rowLeft3, iv_rowLeft4, iv_rowLeft6,iv_rowLeft7,iv_rowLeft5;
        Button bt_choseOrder;

        public DoctorOrdersViewHolder(View itemView) {
            super(itemView);
            orderId = (TextView) itemView.findViewById(R.id.tv_orderId);
            orderDate = (TextView) itemView.findViewById(R.id.tv_orderDate);
            orderTime = (TextView) itemView.findViewById(R.id.tv_orderTime);
            rowDown = (ImageView) itemView.findViewById(R.id.iv_rowDown);
            detailsLayout = (ConstraintLayout) itemView.findViewById(R.id.details_layout);
            tv_whenPainStart = (TextView) itemView.findViewById(R.id.tv_whenPainStart);
            tv_painPlace = (TextView) itemView.findViewById(R.id.tv_painPlace);
            tv_street = (TextView) itemView.findViewById(R.id.tv_street);
            tv_city = (TextView) itemView.findViewById(R.id.tv_city);
            tv_patientAddress = (TextView) itemView.findViewById(R.id.tv_patientAddress);
            tv_patientName = (TextView) itemView.findViewById(R.id.tv_patientName);
            tv_patientNumber = (TextView) itemView.findViewById(R.id.tv_patientNumber);
            iv_rowUp = (ImageView) itemView.findViewById(R.id.iv_rowUp);
            iv_rowLeft1 = (ImageView) itemView.findViewById(R.id.iv_rowLeft1);
            iv_rowLeft2 = (ImageView) itemView.findViewById(R.id.iv_rowLeft2);
            iv_rowLeft3 = (ImageView) itemView.findViewById(R.id.iv_rowLeft3);
            iv_rowLeft4 = (ImageView) itemView.findViewById(R.id.iv_rowLeft4);
            iv_rowLeft5 = (ImageView) itemView.findViewById(R.id.iv_rowLeft5);
            iv_rowLeft7 = (ImageView) itemView.findViewById(R.id.iv_rowLeft7);
            iv_rowLeft6 = (ImageView) itemView.findViewById(R.id.iv_rowLeft6);
            bt_choseOrder = (Button) itemView.findViewById(R.id.bt_choseOrder);
        }

    }

    @Override
    public DoctorOrdersViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.doctor_orders_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new DoctorOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DoctorOrdersViewHolder doctorOrdersViewHolder, final int position) {
        if(Locale.getDefault().getLanguage().equals("ar")){
            doctorOrdersViewHolder.iv_rowLeft1.setImageResource(R.drawable.main_screen_arrow_icon);
            doctorOrdersViewHolder.iv_rowLeft2.setImageResource(R.drawable.main_screen_arrow_icon);
            doctorOrdersViewHolder.iv_rowLeft3.setImageResource(R.drawable.main_screen_arrow_icon);
            doctorOrdersViewHolder.iv_rowLeft4.setImageResource(R.drawable.main_screen_arrow_icon);
            doctorOrdersViewHolder.iv_rowLeft6.setImageResource(R.drawable.main_screen_arrow_icon);
            doctorOrdersViewHolder.iv_rowLeft5.setImageResource(R.drawable.main_screen_arrow_icon);
            doctorOrdersViewHolder.iv_rowLeft7.setImageResource(R.drawable.main_screen_arrow_icon);
        } if(Locale.getDefault().getLanguage().equals("en")){
            doctorOrdersViewHolder.iv_rowLeft1.setImageResource(R.drawable.main_screen_arrow_icon_en);
            doctorOrdersViewHolder.iv_rowLeft2.setImageResource(R.drawable.main_screen_arrow_icon_en);
            doctorOrdersViewHolder.iv_rowLeft3.setImageResource(R.drawable.main_screen_arrow_icon_en);
            doctorOrdersViewHolder.iv_rowLeft4.setImageResource(R.drawable.main_screen_arrow_icon_en);
            doctorOrdersViewHolder.iv_rowLeft6.setImageResource(R.drawable.main_screen_arrow_icon_en);
            doctorOrdersViewHolder.iv_rowLeft5.setImageResource(R.drawable.main_screen_arrow_icon_en);
            doctorOrdersViewHolder.iv_rowLeft7.setImageResource(R.drawable.main_screen_arrow_icon_en);
        }
        doctorOrdersViewHolder.orderId.setText(context.getString(R.string.oder_number) +" "+orders.get(position).getId());
        doctorOrdersViewHolder.orderDate.setText(orders.get(position).getDate());
        doctorOrdersViewHolder.orderTime.setText(orders.get(position).getTime());
        doctorOrdersViewHolder.tv_whenPainStart.setText(context.getString(R.string.pain_start_sence)  + " "+orderDetails.get(position).getWhenPainStart());
        doctorOrdersViewHolder.tv_painPlace.setText(context.getString(R.string.pain_place) +" "+orderDetails.get(position).getPainPlace());
        doctorOrdersViewHolder.tv_patientAddress.setText(context.getString(R.string.the_address)+" "+orderDetails.get(position).getAddress());
        doctorOrdersViewHolder.tv_street.setText(context.getString(R.string.street)+" "+orderDetails.get(position).getStreet());
        doctorOrdersViewHolder.tv_city.setText(context.getString(R.string.city) +" "+orderDetails.get(position).getCity());
        doctorOrdersViewHolder.tv_patientName.setText(context.getString(R.string.the_patientName)+" "+ orderDetails.get(position).getPatient().getName());
        doctorOrdersViewHolder.tv_patientNumber.setText(context.getString(R.string.the_patientNumber)+" "+ orderDetails.get(position).getPatient().getPhone());
        doctorOrdersViewHolder.rowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorOrdersViewHolder.detailsLayout.setVisibility(View.VISIBLE);
            }
        });
        doctorOrdersViewHolder.iv_rowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorOrdersViewHolder.detailsLayout.setVisibility(View.GONE);
            }
        });
        doctorOrdersViewHolder.bt_choseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show waiting progress
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage(context.getString(R.string.please_wait));
                progressDialog.setCancelable(false);
                progressDialog.show();

                String id = orderDetails.get(position).getId();
                Log.e("id",id);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<NormalResponse> call = apiService.doctorSingleOrder(getSecret(context), getToken(context), id);
                //send data and receive response
                call.enqueue(new Callback<NormalResponse>() {
                    @Override
                    public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {
                        //here if task successful
                        //dismiss the waiting progress
                        progressDialog.dismiss();
                        //check the respond body is null or not
                        //if body not null
                        if (response.body() != null) {
                            //check if response code is successful or not
                            //if code successful
                            if (response.body().getCode().equals(FLAGE_CODE_SUCCSESS)) {
                                doctorOrdersViewHolder.bt_choseOrder.setText(R.string.order_choosen);
                                doctorOrdersViewHolder.bt_choseOrder.setBackgroundColor(ContextCompat.getColor(context, R.color.green_highlighted));
                                doctorOrdersViewHolder.bt_choseOrder.setClickable(false);
                            } else {
                                //here if code not successful
                                try {
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(context, context.getString(R.string.something_error), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            //here if body null
                            Toast.makeText(context, context.getString(R.string.contact_services), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResponse> call, Throwable t) {
                        //here if task failed
                        //dismiss waiting dialog
                        progressDialog.dismiss();
                        Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                        Log.e("ERROR", t.getMessage() + "   ");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orders == null) return 0;
        return orders.size();
    }

}
