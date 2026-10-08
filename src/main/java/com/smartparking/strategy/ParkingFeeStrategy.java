package com.smartparking.strategy;

// ====================================================================================
// DESIGN PATTERN 3: STRATEGY PATTERN (Strategy Interface)
// ====================================================================================
// Strategy Pattern defines a family of algorithms, encapsulates each one, and makes
// them interchangeable.
//
// In this application, ParkingFeeStrategy defines the algorithm contract for calculating
// parking fees based on duration and vehicle type.
//
// Viva Explanation:
// "Strategy Pattern allows different parking fee algorithms to be selected at runtime
// without modifying the core parking logic or controller code."
// ====================================================================================

public interface ParkingFeeStrategy {
    /**
     * Calculates parking fee based on duration in hours.
     *
     * @param durationHours Parking duration in hours (minimum 1 hour)
     * @return Total fee in INR (₹)
     */
    double calculateFee(long durationHours);

    /**
     * Gets description of rate structure.
     */
    String getRateDescription();
}
