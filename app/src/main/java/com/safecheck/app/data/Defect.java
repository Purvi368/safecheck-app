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

    // IMPORTANT: UI expects getCheckId(), not checkOwnerId
    public int getCheckId() {
        return checkOwnerId;
    }

    public void setCheckId(int checkId) {
        this.checkOwnerId = checkId;
    }
}