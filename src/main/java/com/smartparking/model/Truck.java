package com.smartparking.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// =====================================================
// FACTORY PATTERN - Concrete Product 3: Truck
// =====================================================

@Entity
@DiscriminatorValue("TRUCK")
public class Truck extends Vehicle {

    public Truck() {
        super();
    }

    public Truck(String vehicleNumber, String ownerName) {
        super(vehicleNumber, ownerName, VehicleType.TRUCK);
    }

    @Override
    public String getVehicleCategoryDescription() {
        return "Heavy Commercial Vehicle / Truck";
    }
}
