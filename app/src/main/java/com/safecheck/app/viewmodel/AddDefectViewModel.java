package com.safecheck.app.viewmodel;

import androidx.lifecycle.ViewModel;

public class AddDefectViewModel extends ViewModel {

    private String defectText = "";
    private String severity = "Low";

    public String getDefectText() {
        return defectText;
    }

    public void setDefectText(String defectText) {
        this.defectText = defectText;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}