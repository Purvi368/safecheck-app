package com.safecheck.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.safecheck.app.model.Defect;
import com.safecheck.app.model.SafetyCheck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SafetyRepository {

    private static SafetyRepository instance;

    private final MutableLiveData<List<SafetyCheck>> allChecks = new MutableLiveData<>();
    private final List<SafetyCheck> safetyCheckList = new ArrayList<>();
    private final List<Defect> defectList = new ArrayList<>();

    private int nextCheckId = 1;
    private int nextDefectId = 1;

    private SafetyRepository() {
        // Sample data so UI shows something
        safetyCheckList.add(new SafetyCheck(nextCheckId++, "20/04/2026", "AB12 XYZ"));
        safetyCheckList.add(new SafetyCheck(nextCheckId++, "21/04/2026", "CD34 LMN"));
        allChecks.setValue(new ArrayList<>(safetyCheckList));

        defectList.add(new Defect(nextDefectId++, 1, "Broken headlight", "High"));
        defectList.add(new Defect(nextDefectId++, 1, "Low tyre pressure", "Low"));
        defectList.add(new Defect(nextDefectId++, 2, "Cracked mirror", "High"));
    }

    public static SafetyRepository getInstance() {
        if (instance == null) {
            instance = new SafetyRepository();
        }
        return instance;
    }

    public LiveData<List<SafetyCheck>> getAllChecks() {
        return allChecks;
    }

    public SafetyCheck getCheckById(int checkId) {
        for (SafetyCheck check : safetyCheckList) {
            if (check.getCheckId() == checkId) {
                return check;
            }
        }
        return null;
    }

    public List<Defect> getDefectsForCheck(int checkId) {
        List<Defect> result = new ArrayList<>();
        for (Defect defect : defectList) {
            if (defect.getCheckId() == checkId) {
                result.add(defect);
            }
        }
        return result;
    }

    public void addSafetyCheck(String date, String vehicleRegistration) {
        safetyCheckList.add(new SafetyCheck(nextCheckId++, date, vehicleRegistration));
        allChecks.setValue(new ArrayList<>(safetyCheckList));
    }

    public void addDefect(int checkId, String description, String severity) {
        defectList.add(new Defect(nextDefectId++, checkId, description, severity));
    }

    public void deleteSafetyCheck(int checkId) {
        Iterator<SafetyCheck> checkIterator = safetyCheckList.iterator();
        while (checkIterator.hasNext()) {
            if (checkIterator.next().getCheckId() == checkId) {
                checkIterator.remove();
                break;
            }
        }

        Iterator<Defect> defectIterator = defectList.iterator();
        while (defectIterator.hasNext()) {
            if (defectIterator.next().getCheckId() == checkId) {
                defectIterator.remove();
            }
        }

        allChecks.setValue(new ArrayList<>(safetyCheckList));
    }

    public int getDefectCountForCheck(int checkId) {
        int count = 0;
        for (Defect defect : defectList) {
            if (defect.getCheckId() == checkId) {
                count++;
            }
        }
        return count;
    }
}