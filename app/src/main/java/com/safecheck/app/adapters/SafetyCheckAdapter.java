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

public class SafetyCheckAdapter extends RecyclerView.Adapter<SafetyCheckAdapter.ViewHolder> {

    private final List<SafetyCheck> safetyChecks = new ArrayList<>();
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(SafetyCheck safetyCheck);
    }

    public SafetyCheckAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setChecks(List<SafetyCheck> checks) {
        safetyChecks.clear();
        if (checks != null) {
            safetyChecks.addAll(checks);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_safety_check, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SafetyCheck safetyCheck = safetyChecks.get(position);
        holder.bind(safetyCheck, listener);
    }

    @Override
    public int getItemCount() {
        return safetyChecks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvVehicleReg;
        private final TextView tvDate;
        private final TextView tvDefectCount;
        private final TextView tvViewDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVehicleReg = itemView.findViewById(R.id.tvVehicleReg);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDefectCount = itemView.findViewById(R.id.tvDefectCount);
            tvViewDetails = itemView.findViewById(R.id.tvViewDetails);
        }

        public void bind(SafetyCheck safetyCheck, OnItemClickListener listener) {
            tvVehicleReg.setText("Vehicle: " + safeText(safetyCheck.getVehicleRegistration()));
            tvDate.setText("Date: " + safeText(safetyCheck.getDate()));

            String status = safetyCheck.getOverallStatus();
            if (status == null || status.trim().isEmpty()) {
                status = "Pending";
            }

            tvDefectCount.setText("Status: " + status);

            if (status.equalsIgnoreCase("Pass")) {
                tvDefectCount.setTextColor(itemView.getContext().getColor(R.color.statusPassText));
                tvViewDetails.setText("Tap to view defects →");
            } else if (status.equalsIgnoreCase("Fail")) {
                tvDefectCount.setTextColor(itemView.getContext().getColor(R.color.statusFailText));
                tvViewDetails.setText("Tap to view defects →");
            } else {
                tvDefectCount.setTextColor(itemView.getContext().getColor(R.color.textPrimary));
                tvViewDetails.setText("Tap to view inspection details →");
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(safetyCheck);
                }
            });
        }

        private String safeText(String value) {
            return value == null || value.trim().isEmpty() ? "N/A" : value;
        }
    }
}