package com.safecheck.app.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.safecheck.app.R;
import com.safecheck.app.viewmodel.AddDefectViewModel;
import com.safecheck.app.viewmodel.SafetyCheckViewModel;

public class AddDefectActivity extends AppCompatActivity {

    private EditText etDefectDescription;
    private Spinner spinnerSeverity;

    private AddDefectViewModel addDefectViewModel;
    private SafetyCheckViewModel safetyCheckViewModel;

    private int checkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_defect);

        etDefectDescription = findViewById(R.id.etDefectDescription);
        spinnerSeverity = findViewById(R.id.spinnerSeverity);
        Button btnSaveDefect = findViewById(R.id.btnSaveDefect);

        checkId = getIntent().getIntExtra("checkId", -1);

        addDefectViewModel = new ViewModelProvider(this).get(AddDefectViewModel.class);
        safetyCheckViewModel = new ViewModelProvider(this).get(SafetyCheckViewModel.class);

        String[] severityOptions = {"Low", "High"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, severityOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSeverity.setAdapter(spinnerAdapter);

        etDefectDescription.setText(addDefectViewModel.getDefectText());

        if (addDefectViewModel.getSeverity().equals("High")) {
            spinnerSeverity.setSelection(1);
        } else {
            spinnerSeverity.setSelection(0);
        }

        etDefectDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addDefectViewModel.setDefectText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        btnSaveDefect.setOnClickListener(v -> saveDefect());
    }

    private void saveDefect() {
        String description = etDefectDescription.getText().toString().trim();
        String severity = spinnerSeverity.getSelectedItem().toString();

        addDefectViewModel.setSeverity(severity);

        if (description.isEmpty()) {
            Toast.makeText(this, "Please enter defect details", Toast.LENGTH_SHORT).show();
            return;
        }

        safetyCheckViewModel.addDefect(checkId, description, severity);
        Toast.makeText(this, "Defect saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}