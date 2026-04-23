package com.safecheck.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.safecheck.app.data.Defect;
import com.safecheck.app.data.SafetyCheck;
import com.safecheck.app.data.SafetyCheckWithDefects;
import com.safecheck.app.data.SafetyRepository;

import java.util.List;

public class SafetyCheckViewModel extends AndroidViewModel {

    private final SafetyRepository repository;

    public SafetyCheckViewModel(@NonNull Application application) {
        super(application);
        repository = SafetyRepository.getInstance(application);
    }

    public LiveData<List<SafetyCheck>> getAllChecks() {
        return repository.getAllChecks();
    }

    public LiveData<SafetyCheck> getCheckById(int checkId) {
        return repository.getCheckById(checkId);
    }

    public LiveData<List<Defect>> getDefectsForCheck(int checkId) {
        return repository.getDefectsForCheck(checkId);
    }

    public LiveData<Integer> getDefectCountForCheck(int checkId) {
        return repository.getDefectCountForCheck(checkId);
    }

    public LiveData<SafetyCheckWithDefects> getSafetyCheckWithDefects(int checkId) {
        return repository.getSafetyCheckWithDefects(checkId);
    }

    public void addSafetyCheck(String date, String vehicleRegistration) {
        repository.addSafetyCheck(date, vehicleRegistration);
    }

    public void addDefect(int checkId, String description, String severity) {
        repository.addDefect(checkId, description, severity);
    }

    public void deleteSafetyCheck(int checkId) {
        repository.deleteSafetyCheck(checkId);
    }
}