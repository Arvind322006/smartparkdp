package com.smartparking;

import com.smartparking.factory.VehicleFactory;
import com.smartparking.model.ParkingSlot;
import com.smartparking.model.Vehicle;
import com.smartparking.model.VehicleType;
import com.smartparking.observer.ParkingAdmin;
import com.smartparking.observer.ParkingUser;
import com.smartparking.singleton.ParkingLotManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// ====================================================================================
// UNIT TESTS FOR PATTERN 4: OBSERVER PATTERN
// ====================================================================================

public class ObserverPatternTest {

    @BeforeEach
    public void setUp() {
        ParkingLotManager.resetInstance();
    }

    @Test
    @DisplayName("TC14 - Observers receive notifications on slot occupancy change")
    public void testObserverNotification() {
        ParkingLotManager manager = ParkingLotManager.getInstance();

        ParkingUser userObserver = new ParkingUser("Gate Display");
        ParkingAdmin adminObserver = new ParkingAdmin("Control Room");

        manager.registerObserver(userObserver);
        manager.registerObserver(adminObserver);

        ParkingSlot slot = new ParkingSlot("C01", VehicleType.CAR);
        manager.addSlot(slot);

        Vehicle vehicle = VehicleFactory.createVehicle("CAR", "TN58AB1234", "Arvind");
        manager.assignSlot(slot, vehicle);

        assertEquals(1, userObserver.getReceivedNotifications().size());
        assertTrue(userObserver.getReceivedNotifications().get(0).contains("C01 has been occupied"));

        assertEquals(1, adminObserver.getAdminLogs().size());
        assertTrue(adminObserver.getAdminLogs().get(0).contains("TN58AB1234"));

        manager.releaseSlot(slot);
        assertEquals(2, userObserver.getReceivedNotifications().size());
        assertTrue(userObserver.getReceivedNotifications().get(1).contains("C01 is now available"));
    }
}
