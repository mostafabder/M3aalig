package com.asi.m3alig.Adapters.Patient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asi.m3alig.Models.TestModel;
import com.asi.m3alig.R;

import java.util.ArrayList;


public class PatientMedicinesAdapter extends RecyclerView.Adapter<PatientMedicinesAdapter.MyViewHolder> {

    private ArrayList<TestModel> dataSet;

    public PatientMedicinesAdapter(ArrayList<TestModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicines, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {



    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View itemView) {
            super(itemView);



        }
    }
}
