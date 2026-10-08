package com.smartparking.factory;

import com.smartparking.model.Bike;
import com.smartparking.model.Car;
import com.smartparking.model.Truck;
import com.smartparking.model.Vehicle;
import com.smartparking.model.VehicleType;

// ====================================================================================
// DESIGN PATTERN 2: FACTORY PATTERN
// ====================================================================================
// Factory Pattern encapsulates object creation and allows the application to create
// different vehicle types (Car, Bike, Truck) without directly depending on their concrete classes.
//
// Viva Explanation:
// "Instead of calling 'new Car()' or 'new Bike()' in controllers or services, the application
// calls VehicleFactory.createVehicle(). This decouples object instantiation logic."
// ====================================================================================

public class VehicleFactory {

    /**
     * Creates and returns a concrete Vehicle instance based on vehicle type.
     *
     * @param typeString    Vehicle type string ("CAR", "BIKE", "TRUCK")
     * @param vehicleNumber Vehicle registration number
     * @param ownerName     Owner name
     * @return Concrete Vehicle object (Car, Bike, or Truck)
     */
    public static Vehicle createVehicle(String typeString, String vehicleNumber, String ownerName) {
        if (typeString == null || typeString.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle type cannot be null or empty.");
        }

        VehicleType type;
        try {
            type = VehicleType.valueOf(typeString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid vehicle type: " + typeString + ". Allowed types: CAR, BIKE, TRUCK.");
        }

        return createVehicle(type, vehicleNumber, ownerName);
    }

    /**
     * Overloaded method taking VehicleType enum.
     */
    public static Vehicle createVehicle(VehicleType type, String vehicleNumber, String ownerName) {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle number cannot be empty.");
        }
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be empty.");
        }

        String formattedNumber = vehicleNumber.trim().toUpperCase();
        String formattedOwner = ownerName.trim();

        switch (type) {
            case CAR:
                return new Car(formattedNumber, formattedOwner);
            case BIKE:
                return new Bike(formattedNumber, formattedOwner);
            case TRUCK:
                return new Truck(formattedNumber, formattedOwner);
            default:
                throw new IllegalArgumentException("Unsupported vehicle type: " + type);
        }
    }
}
