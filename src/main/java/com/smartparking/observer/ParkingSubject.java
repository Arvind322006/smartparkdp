package com.smartparking.observer;

// ====================================================================================
// OBSERVER PATTERN - Subject Interface
// ====================================================================================

public interface ParkingSubject {
    void registerObserver(ParkingObserver observer);
    void removeObserver(ParkingObserver observer);
    void notifyObservers(String message);
}
