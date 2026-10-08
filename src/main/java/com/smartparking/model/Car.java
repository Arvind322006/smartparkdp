package com.smartparking.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// =====================================================
// FACTORY PATTERN - Concrete Product 1: Car
// =====================================================

@Entity
@DiscriminatorValue("CAR")
public class Car extends Vehicle {

    public Car() {
        super();
    }

    public Car(String vehicleNumber, String ownerName) {
        super(vehicleNumber, ownerName, VehicleType.CAR);
    }

    @Override
    public String getVehicleCategoryDescription() {
        return "Four Wheeler Passenger Car";
    }
}
