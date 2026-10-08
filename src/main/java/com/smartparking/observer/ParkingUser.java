package com.smartparking.observer;

import java.util.ArrayList;
import java.util.List;

// ====================================================================================
// OBSERVER PATTERN - Concrete Observer 1: ParkingUser Observer
// ====================================================================================
// Represents end-user display / mobile notification subscriber.
// ====================================================================================

public class ParkingUser implements ParkingObserver {

    private final String userName;
    private final List<String> receivedNotifications = new ArrayList<>();

    public ParkingUser(String userName) {
        this.userName = userName;
    }

    @Override
    public void update(String message) {
        String formattedMsg = "[User Display - " + userName + "] " + message;
        receivedNotifications.add(formattedMsg);
        System.out.println(formattedMsg);
    }

    @Override
    public String getObserverName() {
        return "User Observer (" + userName + ")";
    }

    public List<String> getReceivedNotifications() {
        return receivedNotifications;
    }
}
