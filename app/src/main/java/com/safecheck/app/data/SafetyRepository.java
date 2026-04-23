package com.safecheck.app.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafetyRepository {

    private static SafetyRepository instance;

    private final SafetyCheckDao safetyCheckDao;
    private final ExecutorService executorService;

    private final MutableLiveData<List<SafetyCheck>> allChecksLiveData = new MutableLiveData<>();
    private final MutableLiveData<SafetyCheck> singleCheckLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Defect>> defectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> defectCountLiveData = new MutableLiveData<>();

    private SafetyRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                "safecheck_db"
        ).build();

        safetyCheckDao = db.safetyCheckDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public static synchronized SafetyRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SafetyRepository(context);
        }
        return instance;
    }

    public LiveData<List<SafetyCheck>> getAllChecks() {
        executorService.execute(() -> {
            List<SafetyCheck> checks = safetyCheckDao.getAllSafetyChecks();
            allChecksLiveData.postValue(checks);
        });
        return allChecksLiveData;
    }

    public LiveData<SafetyCheck> getCheckById(int checkId) {
        executorService.execute(() -> {
            SafetyCheck check = safetyCheckDao.getCheckById(checkId);
            singleCheckLiveData.postValue(check);
        });
        return singleCheckLiveData;
    }

    public LiveData<List<Defect>> getDefectsForCheck(int checkId) {
        executorService.execute(() -> {
            List<Defect> defects = safetyCheckDao.getDefectsForCheck(checkId);
            defectsLiveData.postValue(defects);
        });
        return defectsLiveData;
    }

    public LiveData<Integer> getDefectCountForCheck(int checkId) {
        executorService.execute(() -> {
            int count = safetyCheckDao.getDefectCountForCheck(checkId);
            defectCountLiveData.postValue(count);
        });
        return defectCountLiveData;
    }

    public LiveData<SafetyCheckWithDefects> getSafetyCheckWithDefects(int checkId) {
        MutableLiveData<SafetyCheckWithDefects> result = new MutableLiveData<>();
        executorService.execute(() -> {
            SafetyCheckWithDefects checkWithDefects = safetyCheckDao.getSafetyCheckWithDefects(checkId);
            result.postValue(checkWithDefects);
        });
        return result;
    }

    public void addSafetyCheck(String date, String vehicleRegistration) {
        executorService.execute(() -> {
            SafetyCheck safetyCheck = new SafetyCheck();
            safetyCheck.setDate(date);
            safetyCheck.setVehicleRegistration(vehicleRegistration);
            safetyCheck.setDriverName("Unknown Driver");
            safetyCheck.setOverallStatus("Pass");
            safetyCheckDao.insertSafetyCheck(safetyCheck);

            List<SafetyCheck> checks = safetyCheckDao.getAllSafetyChecks();
            allChecksLiveData.postValue(checks);
        });
    }

    public void insertSafetyCheck(SafetyCheck safetyCheck) {
        executorService.execute(() -> {
            safetyCheckDao.insertSafetyCheck(safetyCheck);
            List<SafetyCheck> checks = safetyCheckDao.getAllSafetyChecks();
            allChecksLiveData.postValue(checks);
        });
    }

    public void addDefect(int checkId, String description, String severity) {
        executorService.execute(() -> {
            Defect defect = new Defect();
            defect.setCheckId(checkId);
            defect.setDescription(description);
            defect.setSeverity(severity);
            safetyCheckDao.insertDefect(defect);
        });
    }

    public void insertDefect(Defect defect) {
        executorService.execute(() -> {
            safetyCheckDao.insertDefect(defect);
        });
    }

    public void deleteSafetyCheck(int checkId) {
        executorService.execute(() -> {
            SafetyCheck safetyCheck = safetyCheckDao.getCheckById(checkId);
            if (safetyCheck != null) {
                safetyCheckDao.deleteSafetyCheck(safetyCheck);
            }
            List<SafetyCheck> checks = safetyCheckDao.getAllSafetyChecks();
            allChecksLiveData.postValue(checks);
        });
    }

    public void deleteSafetyCheck(SafetyCheck safetyCheck) {
        executorService.execute(() -> {
            safetyCheckDao.deleteSafetyCheck(safetyCheck);
            List<SafetyCheck> checks = safetyCheckDao.getAllSafetyChecks();
            allChecksLiveData.postValue(checks);
        });
    }
}