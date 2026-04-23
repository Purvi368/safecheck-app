package com.safecheck.app.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safecheck.app.R;
import com.safecheck.app.adapters.SimpleDefectAdapter;
import com.safecheck.app.data.Defect;
import com.safecheck.app.data.SafetyCheck;
import com.safecheck.app.viewmodel.SafetyCheckViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView tvDetailVehicle;
    private TextView tvDetailDate;
    private RecyclerView recyclerViewDefects;
    private Button btnDeleteCheck;
    private Button btnAddDefect;
    private Button btnEmailReport;

    private SafetyCheckViewModel viewModel;
    private int checkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetailVehicle = findViewById(R.id.tvDetailVehicle);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        recyclerViewDefects = findViewById(R.id.recyclerViewDefects);
        btnDeleteCheck = findViewById(R.id.btnDeleteCheck);
        btnAddDefect = findViewById(R.id.btnAddDefect);
        btnEmailReport = findViewById(R.id.btnEmailReport);

        recyclerViewDefects.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(SafetyCheckViewModel.class);
        checkId = getIntent().getIntExtra("checkId", -1);

        if (checkId == -1) {
            Toast.makeText(this, "Invalid safety check", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadCheckDetails();
        loadDefects();

        btnAddDefect.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, AddDefectActivity.class);
            intent.putExtra("checkId", checkId);
            startActivity(intent);
        });

        btnDeleteCheck.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Check")
                    .setMessage("Are you sure you want to delete this safety check?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        viewModel.deleteSafetyCheck(checkId);
                        Toast.makeText(this, "Safety check deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        btnEmailReport.setOnClickListener(v -> sendEmailReport());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCheckDetails();
        loadDefects();
    }

    private void loadCheckDetails() {
        SafetyCheck safetyCheck = viewModel.getCheckById(checkId);

        if (safetyCheck != null) {
            tvDetailVehicle.setText("Vehicle: " + safetyCheck.getVehicleRegistration());
            tvDetailDate.setText("Date: " + safetyCheck.getDate());
        }
    }

    private void loadDefects() {
        List<Defect> defects = viewModel.getDefectsForCheck(checkId);
        List<String> defectLines = new ArrayList<>();

        if (defects != null) {
            for (Defect defect : defects) {
                String description = defect.getDescription() == null ? "" : defect.getDescription();
                String severity = defect.getSeverity() == null ? "" : defect.getSeverity();
                defectLines.add("• " + description + " (" + severity + ")");
            }
        }

        recyclerViewDefects.setAdapter(new SimpleDefectAdapter(defectLines));
    }

    private void sendEmailReport() {
        SafetyCheck safetyCheck = viewModel.getCheckById(checkId);
        List<Defect> defects = viewModel.getDefectsForCheck(checkId);

        String vehicle = "";
        String date = "";

        if (safetyCheck != null) {
            vehicle = safetyCheck.getVehicleRegistration();
            date = safetyCheck.getDate();
        }

        StringBuilder body = new StringBuilder();
        body.append("Safety Defect Report\n\n");
        body.append("Vehicle: ").append(vehicle).append("\n");
        body.append("Date: ").append(date).append("\n\n");
        body.append("Defects:\n");

        if (defects == null || defects.isEmpty()) {
            body.append("No defects recorded.");
        } else {
            for (Defect defect : defects) {
                String description = defect.getDescription() == null ? "" : defect.getDescription();
                String severity = defect.getSeverity() == null ? "" : defect.getSeverity();

                body.append("- ")
                        .append(description)
                        .append(" (")
                        .append(severity)
                        .append(")\n");
            }
        }

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:test@example.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Safety Defect Report: " + vehicle);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body.toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Email Report"));
        } catch (Exception e) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }
}

//testing//