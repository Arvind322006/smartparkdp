package com.smartparking.configuration;

import com.smartparking.model.ParkingSlot;
import com.smartparking.model.SlotStatus;
import com.smartparking.model.VehicleType;
import com.smartparking.observer.ParkingAdmin;
import com.smartparking.observer.ParkingUser;
import com.smartparking.repository.NotificationRepository;
import com.smartparking.repository.ParkingSlotRepository;
import com.smartparking.singleton.ParkingLotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ParkingSlotRepository slotRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public DataInitializer(ParkingSlotRepository slotRepository, NotificationRepository notificationRepository) {
        this.slotRepository = slotRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (slotRepository.count() == 0) {
            List<ParkingSlot> slots = new ArrayList<>();

            // 5 Bike Slots: B01 to B05
            for (int i = 1; i <= 5; i++) {
                String slotId = String.format("B%02d", i);
                slots.add(new ParkingSlot(slotId, VehicleType.BIKE, SlotStatus.AVAILABLE, null));
            }

            // 10 Car Slots: C01 to C10
            for (int i = 1; i <= 10; i++) {
                String slotId = String.format("C%02d", i);
                slots.add(new ParkingSlot(slotId, VehicleType.CAR, SlotStatus.AVAILABLE, null));
            }

            // 3 Truck Slots: T01 to T03
            for (int i = 1; i <= 3; i++) {
                String slotId = String.format("T%02d", i);
                slots.add(new ParkingSlot(slotId, VehicleType.TRUCK, SlotStatus.AVAILABLE, null));
            }

            slotRepository.saveAll(slots);
            System.out.println(">>> Initialized 18 Parking Slots (5 Bike, 10 Car, 3 Truck) in H2 Database.");
        }

        // Initialize Singleton ParkingLotManager instance and register Observers
        ParkingLotManager manager = ParkingLotManager.getInstance();
        manager.initializeSlots(slotRepository.findAll());

        // Register Observer Pattern Observers
        ParkingUser defaultUserObserver = new ParkingUser("Main Gate LED Display");
        ParkingAdmin defaultAdminObserver = new ParkingAdmin("Control Room Dashboard");

        manager.registerObserver(defaultUserObserver);
        manager.registerObserver(defaultAdminObserver);

        System.out.println(">>> Singleton ParkingLotManager initialized with 2 registered Observers (User & Admin).");
    }
}
