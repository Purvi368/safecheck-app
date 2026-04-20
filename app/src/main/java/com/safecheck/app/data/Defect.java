package com.safecheck.app.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = SafetyCheck.class,
                parentColumns = "checkId",
                childColumns = "checkOwnerId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Defect {

    @PrimaryKey(autoGenerate = true)
    public int defectId;

    public String description;
    public String severity;

    public int checkOwnerId;
}