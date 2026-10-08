package com.smartparking.repository;

import com.smartparking.model.ParkingSlot;
import com.smartparking.model.SlotStatus;
import com.smartparking.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, String> {
    List<ParkingSlot> findByStatus(SlotStatus status);
    List<ParkingSlot> findByVehicleTypeAllowed(VehicleType vehicleType);
    long countByStatus(SlotStatus status);
    long countByVehicleTypeAllowedAndStatus(VehicleType vehicleType, SlotStatus status);
}
