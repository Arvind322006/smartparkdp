package com.smartparking.strategy;

// ====================================================================================
// STRATEGY PATTERN - Concrete Strategy 1: Car Fee Strategy
// ====================================================================================
// Pricing Algorithm for Cars:
// ₹30 for first hour
// ₹20 for every additional hour
// ====================================================================================

public class CarFeeStrategy implements ParkingFeeStrategy {

    private static final double FIRST_HOUR_RATE = 30.0;
    private static final double ADDITIONAL_HOUR_RATE = 20.0;

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
        return "Car Fee: ₹30 for 1st hour + ₹20 per additional hour";
    }
}
