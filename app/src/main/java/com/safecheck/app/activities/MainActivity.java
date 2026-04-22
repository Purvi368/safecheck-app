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
import com.safecheck.app.viewmodel.SafetyCheckViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewChecks;
    private Button btnAddCheck;
    private SafetyCheckAdapter adapter;
    private SafetyCheckViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewChecks = findViewById(R.id.recyclerViewChecks);
        btnAddCheck = findViewById(R.id.btnAddCheck);

        recyclerViewChecks.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SafetyCheckAdapter(safetyCheck -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("checkId", safetyCheck.getCheckId());
            startActivity(intent);
        });

        recyclerViewChecks.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SafetyCheckViewModel.class);

        loadChecks();

        btnAddCheck.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddSafetyCheckActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadChecks();
    }

    private void loadChecks() {
        adapter.setChecks(viewModel.getAllChecks());
    }
}