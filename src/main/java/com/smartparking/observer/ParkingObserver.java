package com.smartparking.observer;

// ====================================================================================
// DESIGN PATTERN 4: OBSERVER PATTERN (Observer Interface)
// ====================================================================================
// Observer Pattern defines a one-to-many dependency between objects so that when one
// object (Subject / ParkingLotManager) changes state, all its dependents (Observers)
// are notified and updated automatically.
//
// Viva Explanation:
// "Whenever a vehicle enters or exits, or a parking slot changes status (AVAILABLE/OCCUPIED),
// all registered observers receive real-time updates."
// ====================================================================================

public interface ParkingObserver {
    /**
     * Called when the Subject (ParkingLotManager) emits a notification update.
     *
     * @param message Notification payload text
     */
    void update(String message);

    /**
     * Gets the observer's identity / role name.
     */
    String getObserverName();
}
