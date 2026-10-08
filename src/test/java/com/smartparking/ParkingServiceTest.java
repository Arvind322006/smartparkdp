package com.smartparking;

import com.smartparking.model.ParkingRecord;
import com.smartparking.model.ParkingSlot;
import com.smartparking.model.SlotStatus;
import com.smartparking.model.Vehicle;
import com.smartparking.repository.NotificationRepository;
import com.smartparking.repository.ParkingRecordRepository;
import com.smartparking.repository.ParkingSlotRepository;
import com.smartparking.repository.VehicleRepository;
import com.smartparking.service.ParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ParkingServiceTest {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingSlotRepository slotRepository;

    @Autowired
    private ParkingRecordRepository recordRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    public void setUp() {
        vehicleRepository.deleteAll();
        recordRepository.deleteAll();
        notificationRepository.deleteAll();

        // Reset all parking slots in DB to AVAILABLE
        List<ParkingSlot> slots = slotRepository.findAll();
        for (ParkingSlot s : slots) {
            s.setStatus(SlotStatus.AVAILABLE);
            s.setCurrentVehicleNumber(null);
        }
        slotRepository.saveAll(slots);

        parkingService.syncSingletonCache();
    }

    @Test
    @DisplayName("TC03 & TC08 - Park car and assign slot successfully")
    public void testParkCarSuccess() {
        Vehicle vehicle = parkingService.parkVehicle("Karthi", "TN58AB1234", "CAR");
        assertNotNull(vehicle);
        assertEquals("TN58AB1234", vehicle.getVehicleNumber());
        assertEquals("C01", vehicle.getAssignedSlot());
        assertTrue(vehicleRepository.existsByVehicleNumber("TN58AB1234"));
    }

    @Test
    @DisplayName("TC09 - Reject duplicate vehicle park request")
    public void testDuplicateVehicleRejected() {
        parkingService.parkVehicle("Karthi", "TN58AB1234", "CAR");
        assertThrows(IllegalArgumentException.class, () -> {
            parkingService.parkVehicle("Karthi", "TN58AB1234", "CAR");
        });
    }

    @Test
    @DisplayName("TC12 & TC13 - Process vehicle exit, calculate fee and release slot")
    public void testVehicleExitAndFeeCalculation() {
        Vehicle vehicle = parkingService.parkVehicle("Karthi", "TN58AB9999", "CAR");
        String assignedSlot = vehicle.getAssignedSlot();

        ParkingRecord record = parkingService.processVehicleExit("TN58AB9999", 2);

        assertNotNull(record);
        assertEquals("TN58AB9999", record.getVehicleNumber());
        assertEquals(2, record.getDurationHours());
        assertEquals(50.0, record.getParkingFee()); // ₹30 1st hr + ₹20 2nd hr
        assertFalse(vehicleRepository.existsByVehicleNumber("TN58AB9999"));

        ParkingSlot slot = slotRepository.findById(assignedSlot).orElse(null);
        assertNotNull(slot);
        assertEquals(SlotStatus.AVAILABLE, slot.getStatus());
    }
}
