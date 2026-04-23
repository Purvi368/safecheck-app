package com.safecheck.app.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SafetyCheck.class, Defect.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SafetyCheckDao safetyCheckDao();
}