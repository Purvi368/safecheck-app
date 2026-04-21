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

    @Transaction
    @Query("SELECT * FROM SafetyCheck WHERE checkId = :id")
    SafetyCheckWithDefects getSafetyCheckWithDefects(int id);

    @Delete
    void deleteSafetyCheck(SafetyCheck safetyCheck);
}