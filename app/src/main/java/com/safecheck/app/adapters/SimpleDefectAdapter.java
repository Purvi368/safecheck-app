package com.safecheck.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safecheck.app.R;

import java.util.List;

public class SimpleDefectAdapter extends RecyclerView.Adapter<SimpleDefectAdapter.DefectViewHolder> {

    private final List<String> defectItems;

    public SimpleDefectAdapter(List<String> defectItems) {
        this.defectItems = defectItems;
    }

    @NonNull
    @Override
    public DefectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_defect, parent, false);
        return new DefectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefectViewHolder holder, int position) {
        holder.tvDefectItem.setText(defectItems.get(position));
    }

    @Override
    public int getItemCount() {
        return defectItems.size();
    }

    static class DefectViewHolder extends RecyclerView.ViewHolder {

        TextView tvDefectItem;

        public DefectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDefectItem = itemView.findViewById(R.id.tvDefectItem);
        }
    }
}