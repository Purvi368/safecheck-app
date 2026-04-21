package com.safecheck.app.data;

import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafetyRepository {

    private static SafetyRepository instance;
    private final SafetyCheckDao safetyCheckDao;
    private final ExecutorService executorService;

    private SafetyRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        safetyCheckDao = db.safetyCheckDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public static synchronized SafetyRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SafetyRepository(context);
        }
        return instance;
    }

    public void insertSafetyCheck(SafetyCheck safetyCheck) {
        executorService.execute(() -> safetyCheckDao.insertSafetyCheck(safetyCheck));
    }

    public void insertDefect(Defect defect) {
        executorService.execute(() -> safetyCheckDao.insertDefect(defect));
    }

    public void deleteSafetyCheck(SafetyCheck safetyCheck) {
        executorService.execute(() -> safetyCheckDao.deleteSafetyCheck(safetyCheck));
    }

    public List<SafetyCheck> getAllSafetyChecks() {
        return safetyCheckDao.getAllSafetyChecks();
    }

    public SafetyCheckWithDefects getSafetyCheckWithDefects(int id) {
        return safetyCheckDao.getSafetyCheckWithDefects(id);
    }

    // Extra methods for old ViewModel compatibility
    public List<SafetyCheck> getAllChecks() {
        return safetyCheckDao.getAllSafetyChecks();
    }

    public SafetyCheck getCheckById(int id) {
        List<SafetyCheck> checks = safetyCheckDao.getAllSafetyChecks();
        for (SafetyCheck check : checks) {
            if (check.getCheckId() == id) {
                return check;
            }
        }
        return null;
    }

    public List<Defect> getDefectsForCheck(int id) {
        SafetyCheckWithDefects result = safetyCheckDao.getSafetyCheckWithDefects(id);
        return result != null ? result.defects : null;
    }

    public void addSafetyCheck(String date, String vehicleRegistration) {
        SafetyCheck safetyCheck = new SafetyCheck();
        safetyCheck.setDate(date);
        safetyCheck.setVehicleRegistration(vehicleRegistration);
        safetyCheck.setDriverName("");
        safetyCheck.setOverallStatus("Pass");
        insertSafetyCheck(safetyCheck);
    }

    public void addDefect(int checkId, String description, String severity) {
        Defect defect = new Defect();
        defect.setCheckOwnerId(checkId);
        defect.setDescription(description);
        defect.setSeverity(severity);
        insertDefect(defect);
    }

    public int getDefectCountForCheck(int id) {
        List<Defect> defects = getDefectsForCheck(id);
        return defects == null ? 0 : defects.size();
    }
}