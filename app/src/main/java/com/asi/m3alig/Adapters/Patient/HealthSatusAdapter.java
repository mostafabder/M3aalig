package com.asi.m3alig.Adapters.Patient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asi.m3alig.Models.HealthStatusModel;
import com.asi.m3alig.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luffy on 3/8/2018.
 */

public class HealthSatusAdapter extends RecyclerView.Adapter<HealthSatusAdapter.MyViewHolder> {
    ArrayList<HealthStatusModel> list;
    Context context;

    public HealthSatusAdapter(ArrayList<HealthStatusModel> list,Context context) {
        this.list = list;
        this.context=context;
    }


    @Override
    public HealthSatusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_health_status, parent, false);


        HealthSatusAdapter.MyViewHolder myViewHolder = new HealthSatusAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final HealthSatusAdapter.MyViewHolder holder,final int position) {
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(position!=0&&list.get(0).getChecked())
                    list.get(position).setChecked(false);
                if(holder.checkBox.isChecked())
                list.get(position).setChecked(true);
                else list.get(position).setChecked(false);
            }
        });
        holder.tv_status.setText(list.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        CheckBox checkBox;
        TextView tv_status;
        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox=(CheckBox)itemView.findViewById(R.id.checkbox_status);
            tv_status=(TextView)itemView.findViewById(R.id.tv_status);
        }
    }
}
