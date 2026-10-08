package com.smartparking.strategy;

// ====================================================================================
// STRATEGY PATTERN - Concrete Strategy 3: Truck Fee Strategy
// ====================================================================================
// Pricing Algorithm for Trucks:
// ₹50 for first hour
// ₹30 for every additional hour
// ====================================================================================

public class TruckFeeStrategy implements ParkingFeeStrategy {

    private static final double FIRST_HOUR_RATE = 50.0;
    private static final double ADDITIONAL_HOUR_RATE = 30.0;

    @Override
    public double calculateFee(long durationHours) {
        if (durationHours <= 0) {
            durationHours = 1; // Minimum 1 hour charge
        }

        if (durationHours == 1) {
            return FIRST_HOUR_RATE;
        } else {
            return FIRST_HOUR_RATE + ((durationHours - 1) * ADDITIONAL_HOUR_RATE);
        }
    }

    @Override
    public String getRateDescription() {
        return "Truck Fee: ₹50 for 1st hour + ₹30 per additional hour";
    }
}
