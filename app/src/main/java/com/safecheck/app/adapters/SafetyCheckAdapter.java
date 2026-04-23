package com.safecheck.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safecheck.app.R;
import com.safecheck.app.data.SafetyCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SafetyCheckAdapter extends RecyclerView.Adapter<SafetyCheckAdapter.SafetyCheckViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(SafetyCheck safetyCheck);
    }

    private List<SafetyCheck> safetyChecks = new ArrayList<>();
    private final Map<Integer, Integer> defectCounts;
    private final OnItemClickListener listener;

    public SafetyCheckAdapter(Map<Integer, Integer> defectCounts, OnItemClickListener listener) {
        this.defectCounts = defectCounts;
        this.listener = listener;
    }

    public void setSafetyChecks(List<SafetyCheck> safetyChecks) {
        this.safetyChecks = safetyChecks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SafetyCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_safety_check, parent, false);
        return new SafetyCheckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SafetyCheckViewHolder holder, int position) {
        SafetyCheck current = safetyChecks.get(position);
        holder.tvDate.setText(current.getDate());
        holder.tvVehicleReg.setText(current.getVehicleRegistration());

        int count = defectCounts.containsKey(current.getCheckId()) ? defectCounts.get(current.getCheckId()) : 0;
        holder.tvDefectCount.setText(count + " Defects");

        holder.itemView.setOnClickListener(v -> listener.onItemClick(current));
    }

    @Override
    public int getItemCount() {
        return safetyChecks.size();
    }

    static class SafetyCheckViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvVehicleReg, tvDefectCount;

        public SafetyCheckViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvVehicleReg = itemView.findViewById(R.id.tvVehicleReg);
            tvDefectCount = itemView.findViewById(R.id.tvDefectCount);
        }
    }
}