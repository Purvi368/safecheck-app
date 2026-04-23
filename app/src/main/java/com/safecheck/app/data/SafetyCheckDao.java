package com.safecheck.app.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface SafetyCheckDao {

    @Insert
    void insertSafetyCheck(SafetyCheck safetyCheck);

    @Insert
    void insertDefect(Defect defect);

    @Query("SELECT * FROM SafetyCheck")
    List<SafetyCheck> getAllSafetyChecks();

    @Query("SELECT * FROM SafetyCheck WHERE checkId = :checkId LIMIT 1")
    SafetyCheck getCheckById(int checkId);

    @Query("SELECT * FROM Defect WHERE checkOwnerId = :checkId")
    List<Defect> getDefectsForCheck(int checkId);

    @Query("SELECT COUNT(*) FROM Defect WHERE checkOwnerId = :checkId")
    int getDefectCountForCheck(int checkId);

    @Transaction
    @Query("SELECT * FROM SafetyCheck WHERE checkId = :checkId")
    SafetyCheckWithDefects getSafetyCheckWithDefects(int checkId);

    @Delete
    void deleteSafetyCheck(SafetyCheck safetyCheck);
}