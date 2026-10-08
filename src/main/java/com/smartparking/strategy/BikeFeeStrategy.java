package com.smartparking.strategy;

// ====================================================================================
// STRATEGY PATTERN - Concrete Strategy 2: Bike Fee Strategy
// ====================================================================================
// Pricing Algorithm for Bikes:
// ₹20 for first hour
// ₹10 for every additional hour
// ====================================================================================

public class BikeFeeStrategy implements ParkingFeeStrategy {

    private static final double FIRST_HOUR_RATE = 20.0;
    private static final double ADDITIONAL_HOUR_RATE = 10.0;

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
        return "Bike Fee: ₹20 for 1st hour + ₹10 per additional hour";
    }
}
