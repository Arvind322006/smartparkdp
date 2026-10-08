package com.smartparking.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// =====================================================
// FACTORY PATTERN - Concrete Product 2: Bike
// =====================================================

@Entity
@DiscriminatorValue("BIKE")
public class Bike extends Vehicle {

    public Bike() {
        super();
    }

    public Bike(String vehicleNumber, String ownerName) {
        super(vehicleNumber, ownerName, VehicleType.BIKE);
    }

    @Override
    public String getVehicleCategoryDescription() {
        return "Two Wheeler Motorcycle / Scooter";
    }
}
