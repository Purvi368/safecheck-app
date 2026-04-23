package com.safecheck.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safecheck.app.R;
import com.safecheck.app.data.Defect;

import java.util.ArrayList;
import java.util.List;

public class DefectAdapter extends RecyclerView.Adapter<DefectAdapter.DefectViewHolder> {

    private List<Defect> defects = new ArrayList<>();

    public void setDefects(List<Defect> defects) {
        this.defects = defects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DefectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_defect, parent, false);
        return new DefectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefectViewHolder holder, int position) {
        Defect defect = defects.get(position);
        holder.tvDescription.setText(defect.getDescription());
        holder.tvSeverity.setText("Severity: " + defect.getSeverity());
    }

    @Override
    public int getItemCount() {
        return defects.size();
    }

    static class DefectViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvSeverity;

        public DefectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDefectDescription);
            tvSeverity = itemView.findViewById(R.id.tvDefectSeverity);
        }
    }
}