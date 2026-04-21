package com.safecheck.app.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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
    private int defectId;

    private String description;
    private String severity;
    private int checkOwnerId;

    public Defect() {
    }

    public Defect(String description, String severity, int checkOwnerId) {
        this.description = description;
        this.severity = severity;
        this.checkOwnerId = checkOwnerId;
    }

    public int getDefectId() {
        return defectId;
    }

    public void setDefectId(int defectId) {
        this.defectId = defectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public int getCheckOwnerId() {
        return checkOwnerId;
    }

    public void setCheckOwnerId(int checkOwnerId) {
        this.checkOwnerId = checkOwnerId;
    }


    public int getCheckId() {
        return checkOwnerId;
    }
}