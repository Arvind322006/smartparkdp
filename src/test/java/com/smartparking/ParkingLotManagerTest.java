package com.smartparking;

import com.smartparking.factory.VehicleFactory;
import com.smartparking.model.ParkingSlot;
import com.smartparking.model.SlotStatus;
import com.smartparking.model.Vehicle;
import com.smartparking.model.VehicleType;
import com.smartparking.singleton.ParkingLotManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// ====================================================================================
// UNIT TESTS FOR PATTERN 1: SINGLETON PATTERN
// ====================================================================================

public class ParkingLotManagerTest {

    @BeforeEach
    public void setUp() {
        ParkingLotManager.resetInstance();
    }

    @Test
    @DisplayName("TC07 - Singleton pattern returns exact same instance reference")
    public void testSingletonInstanceUniqueness() {
        ParkingLotManager manager1 = ParkingLotManager.getInstance();
        ParkingLotManager manager2 = ParkingLotManager.getInstance();

        assertNotNull(manager1);
        assertNotNull(manager2);
        assertSame(manager1, manager2, "manager1 and manager2 must refer to the exact same object in memory.");
    }

    @Test
    @DisplayName("TC08 - ParkingLotManager assigns and releases slots correctly")
    public void testSlotAssignmentAndRelease() {
        ParkingLotManager manager = ParkingLotManager.getInstance();
        ParkingSlot slotC01 = new ParkingSlot("C01", VehicleType.CAR);
        manager.addSlot(slotC01);

        Vehicle car = VehicleFactory.createVehicle("CAR", "TN58AB1234", "Arvind");
        Optional<ParkingSlot> foundSlot = manager.findAvailableSlot(VehicleType.CAR);

        assertTrue(foundSlot.isPresent());
        assertEquals("C01", foundSlot.get().getSlotId());

        boolean assigned = manager.assignSlot(slotC01, car);
        assertTrue(assigned);
        assertEquals(SlotStatus.OCCUPIED, slotC01.getStatus());
        assertEquals("TN58AB1234", slotC01.getCurrentVehicleNumber());

        boolean released = manager.releaseSlot(slotC01);
        assertTrue(released);
        assertEquals(SlotStatus.AVAILABLE, slotC01.getStatus());
        assertNull(slotC01.getCurrentVehicleNumber());
    }
}
