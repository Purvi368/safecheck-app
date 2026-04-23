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
import com.safecheck.app.data.SafetyCheck;
import com.safecheck.app.viewmodel.SafetyCheckViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SafetyCheckViewModel viewModel;
    private SafetyCheckAdapter adapter;

    private final Map<Integer, Integer> defectCountMap = new HashMap<>();

    public static final String EXTRA_CHECK_ID = "checkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewChecks);
        Button btnAddCheck = findViewById(R.id.btnAddCheck);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SafetyCheckAdapter(defectCountMap, safetyCheck -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(EXTRA_CHECK_ID, safetyCheck.getCheckId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SafetyCheckViewModel.class);

        viewModel.getAllChecks().observe(this, this::updateRecyclerData);

        btnAddCheck.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddSafetyCheckActivity.class);
            startActivity(intent);
        });
    }

    private void updateRecyclerData(List<SafetyCheck> safetyChecks) {
        defectCountMap.clear();

        if (safetyChecks == null) {
            adapter.setSafetyChecks(List.of());
            return;
        }

        adapter.setSafetyChecks(safetyChecks);

        for (SafetyCheck check : safetyChecks) {
            viewModel.getDefectCountForCheck(check.getCheckId()).observe(this, count -> {
                if (count != null) {
                    defectCountMap.put(check.getCheckId(), count);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getAllChecks().observe(this, this::updateRecyclerData);
    }
}