package com.safecheck.app.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SafetyCheckWithDefects {

    @Embedded
    public SafetyCheck safetyCheck;

    @Relation(
            parentColumn = "checkId",
            entityColumn = "checkOwnerId"
    )
    public List<Defect> defects;
}