package com.asi.m3alig.Adapters.Patient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asi.m3alig.Models.TestModel;
import com.asi.m3alig.Models.Visit;
import com.asi.m3alig.R;
import com.asi.m3alig.Tools.Utils;

import java.util.ArrayList;


public class PatientHealthSummryAdapter extends RecyclerView.Adapter<PatientHealthSummryAdapter.MyViewHolder> {

    private ArrayList<Visit> dataSet;

    public PatientHealthSummryAdapter(ArrayList<Visit> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_old_health_session, parent, false);


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

                }else {
                    Utils.collapse(holder.fotterLayout);

                }
            }
        });
        init(dataSet.get(listPosition),holder);

    }
    public void init(Visit visit, MyViewHolder holder)
    {
        holder.name_visit.setText(visit.getReason());
        holder.date_visit.setText(visit.getDate());
        if(visit.getDoctor()!=null)
            holder.dr_name_visit.setText(visit.getDoctor().getName());
        if(visit.getReport()!=null)
        {
            holder.gried_visit.setText(visit.getReport().getMedical_desc());
            holder.near_goal_visit.setText(visit.getReport().getShort_obj());
            holder.far_goal_visit.setText(visit.getReport().getLong_obj());
            holder.treatment_desc.setText(visit.getReport().getTreatment_desc());
            holder.treatment_goal.setText(visit.getReport().getTreatment_plan());
        }

    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout headerLayout;
        LinearLayout fotterLayout;
        TextView name_visit,date_visit,dr_name_visit,gried_visit,near_goal_visit,far_goal_visit,treatment_goal,treatment_desc;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.headerLayout = (RelativeLayout) itemView.findViewById(R.id.health_sum_header);
            this.fotterLayout = (LinearLayout) itemView.findViewById(R.id.health_sum_footer);
            name_visit=(TextView)itemView.findViewById(R.id.name_prev_visit);
            date_visit=(TextView)itemView.findViewById(R.id.date_prev_visit);
            dr_name_visit=(TextView)itemView.findViewById(R.id.dr_name_prev_visit);
            gried_visit=(TextView)itemView.findViewById(R.id.gried_prev_visit);
            near_goal_visit=(TextView)itemView.findViewById(R.id.neargoal_prev_visit);
            far_goal_visit=(TextView)itemView.findViewById(R.id.fargoal_prev_visit);
            treatment_goal=(TextView)itemView.findViewById(R.id.prev_visit_treat_goal);
            treatment_desc=(TextView)itemView.findViewById(R.id.prev_visit_treat_desc);
        }
    }
}
