package com.safecheck.app.data;

import android.content.Context;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafetyRepository {

    private SafetyCheckDao safetyCheckDao;
    private ExecutorService executorService;

    public SafetyRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "safecheck_db"
        ).build();

        safetyCheckDao = db.safetyCheckDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Insert SafetyCheck (background thread)
    public void insertSafetyCheck(SafetyCheck safetyCheck) {
        executorService.execute(() -> {
            safetyCheckDao.insertSafetyCheck(safetyCheck);
        });
    }

    // Insert Defect (background thread)
    public void insertDefect(Defect defect) {
        executorService.execute(() -> {
            safetyCheckDao.insertDefect(defect);
        });
    }

    // Delete SafetyCheck (background thread)
    public void deleteSafetyCheck(SafetyCheck safetyCheck) {
        executorService.execute(() -> {
            safetyCheckDao.deleteSafetyCheck(safetyCheck);
        });
    }

    // Get all checks (can be called from UI thread if needed)
    public List<SafetyCheck> getAllSafetyChecks() {
        return safetyCheckDao.getAllSafetyChecks();
    }

    // Get one check with defects
    public SafetyCheckWithDefects getSafetyCheckWithDefects(int id) {
        return safetyCheckDao.getSafetyCheckWithDefects(id);
    }
}