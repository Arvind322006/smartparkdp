package com.smartparking.singleton;

import com.smartparking.model.ParkingSlot;
import com.smartparking.model.SlotStatus;
import com.smartparking.model.Vehicle;
import com.smartparking.model.VehicleType;
import com.smartparking.observer.ParkingObserver;
import com.smartparking.observer.ParkingSubject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// ====================================================================================
// DESIGN PATTERN 1: SINGLETON PATTERN & OBSERVER SUBJECT
// ====================================================================================
// Singleton Pattern ensures there is only ONE central parking lot manager controlling
// the parking slots throughout the entire lifecycle of the application.
//
// Key Features:
// 1. Private static instance variable
// 2. Private constructor to prevent direct instantiation
// 3. Public static getInstance() method for global point of access
//
// Viva Explanation:
// "ParkingLotManager manager1 = ParkingLotManager.getInstance();
//  ParkingLotManager manager2 = ParkingLotManager.getInstance();
//  manager1 and manager2 refer to the EXACT same object memory reference."
// ====================================================================================

public class ParkingLotManager implements ParkingSubject {

    // 1. Private static instance variable for Singleton Pattern
    private static volatile ParkingLotManager instance;

    // In-memory slot management cache synchronized with DB
    private final Map<String, ParkingSlot> slotsMap = new ConcurrentHashMap<>();

    // Observer Pattern list of subscribers
    private final List<ParkingObserver> observers = new ArrayList<>();

    // 2. Private constructor prevents instantiation outside this class
    private ParkingLotManager() {
        // Initialization code
    }

    // 3. Public static synchronized getInstance() method
    public static ParkingLotManager getInstance() {
        if (instance == null) {
            synchronized (ParkingLotManager.class) {
                if (instance == null) {
                    instance = new ParkingLotManager();
                }
            }
        }
        return instance;
    }

    // Method to reset instance (useful for unit testing)
    public static void resetInstance() {
        synchronized (ParkingLotManager.class) {
            instance = null;
        }
    }

    // ----------------------------------------------------
    // PARKING LOT MANAGEMENT RESPONSIBILITIES
    // ----------------------------------------------------

    public void initializeSlots(List<ParkingSlot> slots) {
        slotsMap.clear();
        for (ParkingSlot slot : slots) {
            slotsMap.put(slot.getSlotId(), slot);
        }
    }

    public void addSlot(ParkingSlot slot) {
        slotsMap.put(slot.getSlotId(), slot);
    }

    public List<ParkingSlot> getAllSlots() {
        return new ArrayList<>(slotsMap.values());
    }

    /**
     * Finds the first available slot for a given vehicle type.
     */
    public Optional<ParkingSlot> findAvailableSlot(VehicleType vehicleType) {
        return slotsMap.values().stream()
                .filter(slot -> slot.getVehicleTypeAllowed() == vehicleType && slot.getStatus() == SlotStatus.AVAILABLE)
                .sorted((s1, s2) -> s1.getSlotId().compareTo(s2.getSlotId()))
                .findFirst();
    }

    /**
     * Assigns a slot to a vehicle.
     */
    public boolean assignSlot(ParkingSlot slot, Vehicle vehicle) {
        if (slot.getStatus() == SlotStatus.OCCUPIED) {
            return false;
        }
        slot.setStatus(SlotStatus.OCCUPIED);
        slot.setCurrentVehicleNumber(vehicle.getVehicleNumber());
        vehicle.setAssignedSlot(slot.getSlotId());

        String message = "Notification: Slot " + slot.getSlotId() + " has been occupied by vehicle " + vehicle.getVehicleNumber() + " (" + vehicle.getOwnerName() + ").";
        notifyObservers(message);
        return true;
    }

    /**
     * Releases an occupied slot.
     */
    public boolean releaseSlot(ParkingSlot slot) {
        if (slot.getStatus() == SlotStatus.AVAILABLE) {
            return false;
        }
        String freedVehicleNumber = slot.getCurrentVehicleNumber();
        slot.setStatus(SlotStatus.AVAILABLE);
        slot.setCurrentVehicleNumber(null);

        String message = "Notification: Slot " + slot.getSlotId() + " is now available (Freed from " + freedVehicleNumber + ").";
        notifyObservers(message);
        return true;
    }

    // ----------------------------------------------------
    // OBSERVER PATTERN IMPLEMENTATION (Subject Methods)
    // ----------------------------------------------------

    @Override
    public void registerObserver(ParkingObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(ParkingObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (ParkingObserver observer : observers) {
            observer.update(message);
        }
    }

    public List<ParkingObserver> getObservers() {
        return new ArrayList<>(observers);
    }
}
