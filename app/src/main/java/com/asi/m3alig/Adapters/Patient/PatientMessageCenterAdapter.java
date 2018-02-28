package com.asi.m3alig.Adapters.Patient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.asi.m3alig.Models.TestModel;
import com.asi.m3alig.R;
import com.asi.m3alig.Tools.Utils;

import java.util.ArrayList;


public class PatientMessageCenterAdapter extends RecyclerView.Adapter<PatientMessageCenterAdapter.MyViewHolder> {

    private ArrayList<TestModel> dataSet;

    public PatientMessageCenterAdapter(ArrayList<TestModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_message_center, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        holder.headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.fotterLayout.getVisibility() == View.GONE){
                    Utils.expand(holder.fotterLayout);
//                    holder.answerTitleTextView.setHeight();
                   holder.headerLayout.setVisibility(View.GONE);
                }else {
                    Utils.collapse(holder.fotterLayout);

                }
            }
        });

        holder.fotterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.headerLayout.setVisibility(View.VISIBLE);
                Utils.collapse(holder.fotterLayout);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout headerLayout;
       LinearLayout fotterLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.headerLayout = (RelativeLayout) itemView.findViewById(R.id.patient_message_center_header);
            this.fotterLayout = (LinearLayout) itemView.findViewById(R.id.patient_message_center_footer);


        }
    }
}
