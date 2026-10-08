package com.smartparking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// =====================================================
// FACTORY PATTERN - Abstract Base Product
// =====================================================
// Vehicle represents the abstract product in the Factory Pattern.
// Subclasses (Car, Bike, Truck) implement specific vehicle types.
// =====================================================

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_category", discriminatorType = DiscriminatorType.STRING)
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    @Column(nullable = false)
    private String ownerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private String assignedSlot;

    public Vehicle() {
    }

    public Vehicle(String vehicleNumber, String ownerName, VehicleType vehicleType) {
        this.vehicleNumber = vehicleNumber;
        this.ownerName = ownerName;
        this.vehicleType = vehicleType;
        this.entryTime = LocalDateTime.now();
    }

    // Abstract method to get fee strategy multiplier or description if needed
    public abstract String getVehicleCategoryDescription();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public String getAssignedSlot() {
        return assignedSlot;
    }

    public void setAssignedSlot(String assignedSlot) {
        this.assignedSlot = assignedSlot;
    }
}
