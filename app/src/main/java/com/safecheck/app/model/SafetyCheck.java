package com.safecheck.app.model;

public class SafetyCheck {
    private int checkId;
    private String date;
    private String vehicleRegistration;

    public SafetyCheck(int checkId, String date, String vehicleRegistration) {
        this.checkId = checkId;
        this.date = date;
        this.vehicleRegistration = vehicleRegistration;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public String getDate() {
        return date;
    }

    public String getVehicleRegistration() {
        return vehicleRegistration;
    }
}