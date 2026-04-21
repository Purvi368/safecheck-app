package com.safecheck.app.model;

public class Defect {
    private int defectId;
    private int checkId;
    private String description;
    private String severity;

    public Defect(int defectId, int checkId, String description, String severity) {
        this.defectId = defectId;
        this.checkId = checkId;
        this.description = description;
        this.severity = severity;
    }

    public int getDefectId() {
        return defectId;
    }

    public int getCheckId() {
        return checkId;
    }

    public String getDescription() {
        return description;
    }

    public String getSeverity() {
        return severity;
    }
}