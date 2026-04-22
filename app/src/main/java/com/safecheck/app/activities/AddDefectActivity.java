package com.safecheck.app.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.safecheck.app.R;
import com.safecheck.app.viewmodel.SafetyCheckViewModel;

public class AddDefectActivity extends AppCompatActivity {

    private EditText etDefectDescription;
    private EditText etDefectSeverity;
    private Button btnSaveDefect;
    private SafetyCheckViewModel viewModel;
    private int checkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_defect);

        etDefectDescription = findViewById(R.id.etDefectDescription);
        etDefectSeverity = findViewById(R.id.etDefectSeverity);
        btnSaveDefect = findViewById(R.id.btnSaveDefect);

        viewModel = new ViewModelProvider(this).get(SafetyCheckViewModel.class);
        checkId = getIntent().getIntExtra("checkId", -1);

        btnSaveDefect.setOnClickListener(v -> saveDefect());
    }

    private void saveDefect() {
        String description = etDefectDescription.getText().toString().trim();
        String severity = etDefectSeverity.getText().toString().trim();

        if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please enter a defect description", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(severity)) {
            Toast.makeText(this, "Please enter defect severity", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkId == -1) {
            Toast.makeText(this, "Invalid safety check", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.addDefect(checkId, description, severity);
        Toast.makeText(this, "Defect saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}