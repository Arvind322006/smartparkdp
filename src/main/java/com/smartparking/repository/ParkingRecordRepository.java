package com.smartparking.repository;

import com.smartparking.model.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Long> {
    List<ParkingRecord> findAllByOrderByIdDesc();

    @Query("SELECT SUM(r.parkingFee) FROM ParkingRecord r")
    Double calculateTotalRevenue();
}
