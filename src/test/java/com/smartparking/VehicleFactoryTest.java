package com.smartparking;

import com.smartparking.factory.VehicleFactory;
import com.smartparking.model.Bike;
import com.smartparking.model.Car;
import com.smartparking.model.Truck;
import com.smartparking.model.Vehicle;
import com.smartparking.model.VehicleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// ====================================================================================
// UNIT TESTS FOR PATTERN 2: FACTORY PATTERN
// ====================================================================================

public class VehicleFactoryTest {

    @Test
    @DisplayName("TC06 - VehicleFactory creates Car instance correctly")
    public void testFactoryCreatesCar() {
        Vehicle vehicle = VehicleFactory.createVehicle("CAR", "TN58AB1234", "Arvind");
        assertNotNull(vehicle);
        assertTrue(vehicle instanceof Car);
        assertEquals(VehicleType.CAR, vehicle.getVehicleType());
        assertEquals("TN58AB1234", vehicle.getVehicleNumber());
        assertEquals("Arvind", vehicle.getOwnerName());
    }

    @Test
    @DisplayName("TC04 - VehicleFactory creates Bike instance correctly")
    public void testFactoryCreatesBike() {
        Vehicle vehicle = VehicleFactory.createVehicle("BIKE", "TN58XY4567", "Hariharan");
        assertNotNull(vehicle);
        assertTrue(vehicle instanceof Bike);
        assertEquals(VehicleType.BIKE, vehicle.getVehicleType());
    }

    @Test
    @DisplayName("TC05 - VehicleFactory creates Truck instance correctly")
    public void testFactoryCreatesTruck() {
        Vehicle vehicle = VehicleFactory.createVehicle("TRUCK", "TN58ZZ9999", "Kirubakaran");
        assertNotNull(vehicle);
        assertTrue(vehicle instanceof Truck);
        assertEquals(VehicleType.TRUCK, vehicle.getVehicleType());
    }

    @Test
    @DisplayName("TC - VehicleFactory throws exception for invalid type")
    public void testFactoryInvalidTypeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            VehicleFactory.createVehicle("BICYCLE", "TN58AB1234", "Arvind");
        });
    }
}
