package com.smartparking.service;

import com.smartparking.factory.VehicleFactory;
import com.smartparking.model.*;
import com.smartparking.repository.NotificationRepository;
import com.smartparking.repository.ParkingRecordRepository;
import com.smartparking.repository.ParkingSlotRepository;
import com.smartparking.repository.VehicleRepository;
import com.smartparking.singleton.ParkingLotManager;
import com.smartparking.strategy.FeeStrategyContext;
import com.smartparking.strategy.ParkingFeeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ParkingService {

    private final VehicleRepository vehicleRepository;
    private final ParkingSlotRepository slotRepository;
    private final ParkingRecordRepository recordRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public ParkingService(VehicleRepository vehicleRepository,
                          ParkingSlotRepository slotRepository,
                          ParkingRecordRepository recordRepository,
                          NotificationRepository notificationRepository) {
        this.vehicleRepository = vehicleRepository;
        this.slotRepository = slotRepository;
        this.recordRepository = recordRepository;
        this.notificationRepository = notificationRepository;
    }

    /**
     * Synchronize DB slots into the Singleton ParkingLotManager cache.
     */
    public void syncSingletonCache() {
        List<ParkingSlot> slots = slotRepository.findAll();
        ParkingLotManager.getInstance().initializeSlots(slots);
    }

    // ----------------------------------------------------
    // FEATURE: PARK VEHICLE (Uses FACTORY & SINGLETON & OBSERVER)
    // ----------------------------------------------------
    @Transactional
    public Vehicle parkVehicle(String ownerName, String vehicleNumber, String vehicleTypeStr) {
        // Validation 1: Owner Name check
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be empty.");
        }

        // Validation 2: Vehicle Number check
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle number cannot be empty.");
        }

        String formattedNumber = vehicleNumber.trim().toUpperCase();

        // Validation 3: Duplicate active vehicle check
        if (vehicleRepository.existsByVehicleNumber(formattedNumber)) {
            throw new IllegalArgumentException("Vehicle with number '" + formattedNumber + "' is already currently parked.");
        }

        // STEP 1: FACTORY PATTERN - Create vehicle object dynamically
        Vehicle vehicle = VehicleFactory.createVehicle(vehicleTypeStr, formattedNumber, ownerName);

        // Ensure cache is sync'd
        syncSingletonCache();

        // STEP 2: SINGLETON PATTERN - Request slot assignment from central manager
        ParkingLotManager manager = ParkingLotManager.getInstance();
        Optional<ParkingSlot> availableSlotOpt = manager.findAvailableSlot(vehicle.getVehicleType());

        if (availableSlotOpt.isEmpty()) {
            throw new IllegalStateException("No parking slot available for vehicle type: " + vehicle.getVehicleType());
        }

        ParkingSlot slot = availableSlotOpt.get();

        // STEP 3: Assign slot and record entry time
        manager.assignSlot(slot, vehicle);

        // Update database entities
        slotRepository.save(slot);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        // Record Notification into DB
        String notifMsg = "Vehicle " + formattedNumber + " (" + vehicle.getVehicleType() + ") owned by " + ownerName.trim() + " parked at slot " + slot.getSlotId() + ".";
        notificationRepository.save(new Notification(notifMsg));

        return savedVehicle;
    }

    // ----------------------------------------------------
    // FEATURE: VEHICLE EXIT (Uses STRATEGY & SINGLETON & OBSERVER)
    // ----------------------------------------------------
    @Transactional
    public ParkingRecord processVehicleExit(String vehicleNumber, Integer simulatedHours) {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle number cannot be empty.");
        }

        String formattedNumber = vehicleNumber.trim().toUpperCase();

        // Fetch vehicle from DB
        Optional<Vehicle> vehicleOpt = vehicleRepository.findByVehicleNumber(formattedNumber);
        if (vehicleOpt.isEmpty()) {
            throw new IllegalArgumentException("No currently parked vehicle found with registration number: " + formattedNumber);
        }

        Vehicle vehicle = vehicleOpt.get();
        LocalDateTime entryTime = vehicle.getEntryTime();
        LocalDateTime exitTime = LocalDateTime.now();

        // Calculate parking duration (minimum 1 hour charge)
        long actualHours = Duration.between(entryTime, exitTime).toHours();
        long durationHours = (simulatedHours != null && simulatedHours > 0) ? simulatedHours : Math.max(1, actualHours);

        // STEP 4: STRATEGY PATTERN - Calculate fee using appropriate fee strategy
        ParkingFeeStrategy feeStrategy = FeeStrategyContext.getStrategy(vehicle.getVehicleType());
        double parkingFee = feeStrategy.calculateFee(durationHours);

        // STEP 5: SINGLETON PATTERN - Release slot in central manager
        String assignedSlotId = vehicle.getAssignedSlot();
        ParkingLotManager manager = ParkingLotManager.getInstance();

        Optional<ParkingSlot> slotOpt = slotRepository.findById(assignedSlotId);
        if (slotOpt.isPresent()) {
            ParkingSlot slot = slotOpt.get();
            manager.releaseSlot(slot);
            slotRepository.save(slot);
        }

        // STEP 6: Save completed Parking Record in History table
        ParkingRecord record = new ParkingRecord(
                vehicle.getVehicleNumber(),
                vehicle.getOwnerName(),
                vehicle.getVehicleType(),
                assignedSlotId,
                entryTime,
                exitTime,
                durationHours,
                parkingFee
        );
        recordRepository.save(record);

        // Remove vehicle from currently parked vehicles table
        vehicleRepository.delete(vehicle);

        // Save exit notification
        String notifMsg = "Vehicle " + formattedNumber + " exited slot " + assignedSlotId + ". Duration: " + durationHours + " hour(s), Fee: ₹" + (long) parkingFee + ". Slot is now available.";
        notificationRepository.save(new Notification(notifMsg));

        return record;
    }

    // ----------------------------------------------------
    // DASHBOARD & QUERY METHODS
    // ----------------------------------------------------
    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();

        long totalSlots = slotRepository.count();
        long occupiedSlots = slotRepository.countByStatus(SlotStatus.OCCUPIED);
        long availableSlots = totalSlots - occupiedSlots;

        long carsParked = slotRepository.countByVehicleTypeAllowedAndStatus(VehicleType.CAR, SlotStatus.OCCUPIED);
        long bikesParked = slotRepository.countByVehicleTypeAllowedAndStatus(VehicleType.BIKE, SlotStatus.OCCUPIED);
        long trucksParked = slotRepository.countByVehicleTypeAllowedAndStatus(VehicleType.TRUCK, SlotStatus.OCCUPIED);

        Double totalRevenue = recordRepository.calculateTotalRevenue();
        if (totalRevenue == null) {
            totalRevenue = 0.0;
        }

        stats.put("totalSlots", totalSlots);
        stats.put("availableSlots", availableSlots);
        stats.put("occupiedSlots", occupiedSlots);
        stats.put("carsParked", carsParked);
        stats.put("bikesParked", bikesParked);
        stats.put("trucksParked", trucksParked);
        stats.put("totalRevenue", String.format("%.2f", totalRevenue));

        return stats;
    }

    public List<ParkingSlot> getAllSlots() {
        return slotRepository.findAll();
    }

    public List<Vehicle> getCurrentVehicles() {
        return vehicleRepository.findAll();
    }

    public List<ParkingRecord> getParkingHistory() {
        return recordRepository.findAllByOrderByIdDesc();
    }

    public List<Notification> getRecentNotifications() {
        return notificationRepository.findTop10ByOrderByIdDesc();
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAllByOrderByIdDesc();
    }
}
