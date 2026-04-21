package com.safecheck.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.safecheck.app.model.Defect;
import com.safecheck.app.model.SafetyCheck;
import com.safecheck.app.repository.SafetyRepository;

import java.util.List;

public class SafetyCheckViewModel extends ViewModel {

    private final SafetyRepository repository = SafetyRepository.getInstance();

    public LiveData<List<SafetyCheck>> getAllChecks() {
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
        repository.deleteSafetyCheck(checkId);
    }

    public int getDefectCountForCheck(int checkId) {
        return repository.getDefectCountForCheck(checkId);
    }
}