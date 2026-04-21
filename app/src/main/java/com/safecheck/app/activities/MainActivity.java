package com.safecheck.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safecheck.app.R;
import com.safecheck.app.adapters.SafetyCheckAdapter;
import com.safecheck.app.model.SafetyCheck;
import com.safecheck.app.viewmodel.SafetyCheckViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SafetyCheckViewModel viewModel;
    private SafetyCheckAdapter adapter;

    // stores defect count for each check
    private final Map<Integer, Integer> defectCountMap = new HashMap<>();

    public static final String EXTRA_CHECK_ID = "checkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get views
        RecyclerView recyclerView = findViewById(R.id.recyclerViewChecks);
        Button btnAddCheck = findViewById(R.id.btnAddCheck);

        // setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SafetyCheckAdapter(defectCountMap, safetyCheck -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(EXTRA_CHECK_ID, safetyCheck.getCheckId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        // ViewModel
        viewModel = new ViewModelProvider(this).get(SafetyCheckViewModel.class);

        // observe data
        viewModel.getAllChecks().observe(this, this::updateRecyclerData);

        // Add Check button
        btnAddCheck.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddSafetyCheckActivity.class);
            startActivity(intent);
        });
    }

    private void updateRecyclerData(List<SafetyCheck> safetyChecks) {
        defectCountMap.clear();

        for (SafetyCheck check : safetyChecks) {
            int count = viewModel.getDefectCountForCheck(check.getCheckId());
            defectCountMap.put(check.getCheckId(), count);
        }

        adapter.setSafetyChecks(safetyChecks);
    }
}