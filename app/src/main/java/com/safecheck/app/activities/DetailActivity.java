package com.safecheck.app.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safecheck.app.R;
import com.safecheck.app.adapters.DefectAdapter;
import com.safecheck.app.model.Defect;
import com.safecheck.app.model.SafetyCheck;
import com.safecheck.app.viewmodel.SafetyCheckViewModel;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private int checkId;
    private SafetyCheckViewModel viewModel;
    private SafetyCheck currentCheck;
    private List<Defect> currentDefects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvDetailDate = findViewById(R.id.tvDetailDate);
        TextView tvDetailVehicle = findViewById(R.id.tvDetailVehicle);
        RecyclerView recyclerViewDefects = findViewById(R.id.recyclerViewDefects);
        Button btnEmailReport = findViewById(R.id.btnEmailReport);
        Button btnDeleteCheck = findViewById(R.id.btnDeleteCheck);
        Button btnAddDefect = findViewById(R.id.btnAddDefect);

        checkId = getIntent().getIntExtra("checkId", -1);

        if (checkId == -1) {
            Toast.makeText(this, "Invalid check selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(SafetyCheckViewModel.class);

        currentCheck = viewModel.getCheckById(checkId);

        if (currentCheck == null) {
            Toast.makeText(this, "Safety check not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvDetailDate.setText("Date: " + currentCheck.getDate());
        tvDetailVehicle.setText("Vehicle: " + currentCheck.getVehicleRegistration());

        recyclerViewDefects.setLayoutManager(new LinearLayoutManager(this));
        DefectAdapter defectAdapter = new DefectAdapter();
        recyclerViewDefects.setAdapter(defectAdapter);

        currentDefects = viewModel.getDefectsForCheck(checkId);
        defectAdapter.setDefects(currentDefects);

        btnEmailReport.setOnClickListener(v -> sendEmailReport());

        btnDeleteCheck.setOnClickListener(v -> {
            viewModel.deleteSafetyCheck(checkId);
            Toast.makeText(this, "Safety check deleted", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnAddDefect.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, AddDefectActivity.class);
            intent.putExtra("checkId", checkId);
            startActivity(intent);
        });
    }

    private void sendEmailReport() {
        String subject = "Safety Defect Report: " + currentCheck.getVehicleRegistration();

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Vehicle Registration: ").append(currentCheck.getVehicleRegistration()).append("\n");
        bodyBuilder.append("Date: ").append(currentCheck.getDate()).append("\n\n");
        bodyBuilder.append("Defects:\n");

        if (currentDefects == null || currentDefects.isEmpty()) {
            bodyBuilder.append("- No defects recorded");
        } else {
            for (Defect defect : currentDefects) {
                bodyBuilder.append("- ")
                        .append(defect.getDescription())
                        .append(" (")
                        .append(defect.getSeverity())
                        .append(")\n");
            }
        }

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, bodyBuilder.toString());

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }
}