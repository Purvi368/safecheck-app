package com.safecheck.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.safecheck.app.data.Defect;
import com.safecheck.app.data.SafetyCheck;
import com.safecheck.app.data.SafetyRepository;

import java.util.List;

public class SafetyCheckViewModel extends AndroidViewModel {

    private final SafetyRepository repository;

    public SafetyCheckViewModel(@NonNull Application application) {
        super(application);
        repository = SafetyRepository.getInstance(application);
    }

    public List<SafetyCheck> getAllChecks() {
        return repository.getAllChecks();
    }

    public SafetyCheck getCheckById(int checkId) {
        return repository.getCheckById(checkId);
    }

    public List<Defect> getDefectsForCheck(int checkId) {
        return repository.getDefectsForCheck(checkId);
    }

    public void addSafetyCheck(String date, String vehicleRegistration) {
        repository.addSafetyCheck(date, vehicleRegistration);
    }

    public void addDefect(int checkId, String description, String severity) {
        repository.addDefect(checkId, description, severity);
    }

    public void deleteSafetyCheck(int checkId) {
        SafetyCheck safetyCheck = repository.getCheckById(checkId);
        if (safetyCheck != null) {
            repository.deleteSafetyCheck(safetyCheck);
        }
    }

    public int getDefectCountForCheck(int checkId) {
        return repository.getDefectCountForCheck(checkId);
    }
}