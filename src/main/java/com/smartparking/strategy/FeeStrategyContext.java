package com.smartparking.strategy;

import com.smartparking.model.VehicleType;

// ====================================================================================
// STRATEGY PATTERN - Strategy Context / Resolver
// ====================================================================================
// Selects and applies the appropriate ParkingFeeStrategy dynamically based on VehicleType.
// ====================================================================================

public class FeeStrategyContext {

    public static ParkingFeeStrategy getStrategy(VehicleType vehicleType) {
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null for fee strategy.");
        }

        switch (vehicleType) {
            case CAR:
                return new CarFeeStrategy();
            case BIKE:
                return new BikeFeeStrategy();
            case TRUCK:
                return new TruckFeeStrategy();
            default:
                throw new IllegalArgumentException("No fee strategy defined for vehicle type: " + vehicleType);
        }
    }

    public static double calculateFee(VehicleType vehicleType, long durationHours) {
        ParkingFeeStrategy strategy = getStrategy(vehicleType);
        return strategy.calculateFee(durationHours);
    }
}
