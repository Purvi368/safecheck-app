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

public class AddSafetyCheckActivity extends AppCompatActivity {

    private EditText etDate;
    private EditText etVehicleRegistration;
    private Button btnSaveCheck;
    private SafetyCheckViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_safety_check);

        etDate = findViewById(R.id.etDate);
        etVehicleRegistration = findViewById(R.id.etVehicleRegistration);
        btnSaveCheck = findViewById(R.id.btnSaveCheck);

        viewModel = new ViewModelProvider(this).get(SafetyCheckViewModel.class);

        btnSaveCheck.setOnClickListener(v -> saveCheck());
    }

    private void saveCheck() {
        String date = etDate.getText().toString().trim();
        String vehicleReg = etVehicleRegistration.getText().toString().trim();

        if (TextUtils.isEmpty(vehicleReg)) {
            Toast.makeText(this, "Please enter vehicle details", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(date)) {
            date = "Not entered";
        }

        viewModel.addSafetyCheck(date, vehicleReg);
        Toast.makeText(this, "Safety check saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}