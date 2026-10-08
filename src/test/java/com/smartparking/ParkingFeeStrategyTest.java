package com.smartparking;

import com.smartparking.model.VehicleType;
import com.smartparking.strategy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// ====================================================================================
// UNIT TESTS FOR PATTERN 3: STRATEGY PATTERN
// ====================================================================================

public class ParkingFeeStrategyTest {

    @Test
    @DisplayName("TC11 - BikeFeeStrategy fee calculation (₹20 1st hr + ₹10 add)")
    public void testBikeFeeStrategy() {
        ParkingFeeStrategy strategy = new BikeFeeStrategy();
        assertEquals(20.0, strategy.calculateFee(1));
        assertEquals(30.0, strategy.calculateFee(2));
        assertEquals(40.0, strategy.calculateFee(3));
    }

    @Test
    @DisplayName("TC11 - CarFeeStrategy fee calculation (₹30 1st hr + ₹20 add)")
    public void testCarFeeStrategy() {
        ParkingFeeStrategy strategy = new CarFeeStrategy();
        assertEquals(30.0, strategy.calculateFee(1));
        assertEquals(50.0, strategy.calculateFee(2));
        assertEquals(70.0, strategy.calculateFee(3));
    }

    @Test
    @DisplayName("TC11 - TruckFeeStrategy fee calculation (₹50 1st hr + ₹30 add)")
    public void testTruckFeeStrategy() {
        ParkingFeeStrategy strategy = new TruckFeeStrategy();
        assertEquals(50.0, strategy.calculateFee(1));
        assertEquals(80.0, strategy.calculateFee(2));
        assertEquals(110.0, strategy.calculateFee(3));
    }

    @Test
    @DisplayName("TC - FeeStrategyContext resolves correct strategy per vehicle type")
    public void testStrategyContextResolution() {
        assertTrue(FeeStrategyContext.getStrategy(VehicleType.CAR) instanceof CarFeeStrategy);
        assertTrue(FeeStrategyContext.getStrategy(VehicleType.BIKE) instanceof BikeFeeStrategy);
        assertTrue(FeeStrategyContext.getStrategy(VehicleType.TRUCK) instanceof TruckFeeStrategy);
    }
}
