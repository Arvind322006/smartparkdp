package com.smartparking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_slots")
public class ParkingSlot {

    @Id
    private String slotId; // e.g. B01, C01, T01

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleTypeAllowed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status;

    private String currentVehicleNumber;

    public ParkingSlot() {
    }

    public ParkingSlot(String slotId, VehicleType vehicleTypeAllowed) {
        this.slotId = slotId;
        this.vehicleTypeAllowed = vehicleTypeAllowed;
        this.status = SlotStatus.AVAILABLE;
        this.currentVehicleNumber = null;
    }

    public ParkingSlot(String slotId, VehicleType vehicleTypeAllowed, SlotStatus status, String currentVehicleNumber) {
        this.slotId = slotId;
        this.vehicleTypeAllowed = vehicleTypeAllowed;
        this.status = status;
        this.currentVehicleNumber = currentVehicleNumber;
    }

    // Getters and Setters
    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public VehicleType getVehicleTypeAllowed() {
        return vehicleTypeAllowed;
    }

    public void setVehicleTypeAllowed(VehicleType vehicleTypeAllowed) {
        this.vehicleTypeAllowed = vehicleTypeAllowed;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }

    public String getCurrentVehicleNumber() {
        return currentVehicleNumber;
    }

    public void setCurrentVehicleNumber(String currentVehicleNumber) {
        this.currentVehicleNumber = currentVehicleNumber;
    }
}
