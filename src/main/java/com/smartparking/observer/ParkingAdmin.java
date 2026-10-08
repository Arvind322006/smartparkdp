package com.smartparking.observer;

import java.util.ArrayList;
import java.util.List;

// ====================================================================================
// OBSERVER PATTERN - Concrete Observer 2: ParkingAdmin Observer
// ====================================================================================
// Represents operator dashboard / audit log observer.
// ====================================================================================

public class ParkingAdmin implements ParkingObserver {

    private final String adminName;
    private final List<String> adminLogs = new ArrayList<>();

    public ParkingAdmin(String adminName) {
        this.adminName = adminName;
    }

    @Override
    public void update(String message) {
        String formattedLog = "[Admin Audit - " + adminName + "] " + message;
        adminLogs.add(formattedLog);
        System.out.println(formattedLog);
    }

    @Override
    public String getObserverName() {
        return "Admin Observer (" + adminName + ")";
    }

    public List<String> getAdminLogs() {
        return adminLogs;
    }
}
