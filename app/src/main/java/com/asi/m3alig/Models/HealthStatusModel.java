package com.asi.m3alig.Models;

/**
 * Created by Luffy on 3/8/2018.
 */

public class HealthStatusModel {
    Boolean checked;
    String status;

    public HealthStatusModel(Boolean checked, String status) {
        this.checked = checked;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
