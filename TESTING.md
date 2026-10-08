# Software Testing Document

**Smart Parking Management System Using Design Patterns**

---

## 🧪 Test Suite Summary

Total Test Cases: **15**  
Passed: **15**  
Failed: **0**  
Test Framework: **JUnit 5 + Spring Boot Test**

---

## 📋 Detailed Test Execution Log

| Test Case ID | Test Case Name | Objective / Description | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :---: |
| **TC01** | Dashboard Loads | Verify that the dashboard route `/` returns HTTP 200 and loads statistics. | Dashboard renders with total, available, and occupied slot counts. | Dashboard loaded with correct slot counts. | **PASS** |
| **TC02** | Parking Slots Initialized | Verify `DataInitializer` populates 18 slots (B01-B05, C01-C10, T01-T03) on startup. | 18 slots created in H2 database with `AVAILABLE` status. | 18 slots initialized successfully. | **PASS** |
| **TC03** | Park a Car | Register and park a Car with vehicle number `TN58AB1234`. | Vehicle created, assigned slot `C01`, slot status changes to `OCCUPIED`. | Car parked at slot `C01`. | **PASS** |
| **TC04** | Park a Bike | Register and park a Bike with vehicle number `TN58XY4567`. | Vehicle created, assigned slot `B01`, slot status changes to `OCCUPIED`. | Bike parked at slot `B01`. | **PASS** |
| **TC05** | Park a Truck | Register and park a Truck with vehicle number `TN58ZZ9999`. | Vehicle created, assigned slot `T01`, slot status changes to `OCCUPIED`. | Truck parked at slot `T01`. | **PASS** |
| **TC06** | VehicleFactory Test | Verify `VehicleFactory.createVehicle("CAR", ...)` returns `Car` instance. | Returns instance of `com.smartparking.model.Car`. | `Car` instance returned. | **PASS** |
| **TC07** | Singleton Unique Reference | Verify `ParkingLotManager.getInstance()` returns the exact same memory reference. | `manager1 == manager2` evaluates to `true`. | `Same` reference confirmed. | **PASS** |
| **TC08** | Slot Assignment Logic | Verify `ParkingLotManager` finds first available slot for vehicle category. | Returns lowest available slot ID matching category. | Correct slot assigned. | **PASS** |
| **TC09** | Duplicate Vehicle Rejection | Attempt to park a vehicle with an already active registration number. | System throws `IllegalArgumentException` with duplicate error message. | Duplicate request rejected with error message. | **PASS** |
| **TC10** | No Slot Available Error | Attempt to park vehicle when all category slots are occupied. | System throws `IllegalStateException`: "No parking slot available". | Exception caught and error displayed. | **PASS** |
| **TC11** | Parking Fee Calculation | Verify `ParkingFeeStrategy` fee calculation for 2 hours (Car: ₹50, Bike: ₹30, Truck: ₹80). | Car = ₹50, Bike = ₹30, Truck = ₹80. | Calculated fees match exact rules. | **PASS** |
| **TC12** | Vehicle Exit Process | Trigger exit for parked vehicle `TN58AB1234`. | Record created in history, vehicle deleted from current table, fee displayed. | Exit processed cleanly. | **PASS** |
| **TC13** | Slot Release Verification | Verify assigned slot reverts from `OCCUPIED` to `AVAILABLE` after exit. | Slot status changes to `AVAILABLE` in DB and Singleton cache. | Slot status updated to `AVAILABLE`. | **PASS** |
| **TC14** | Observer Notification Alert | Verify registered Observers receive notification when slot state changes. | `ParkingUser` and `ParkingAdmin` receive alert strings. | Observer update methods called successfully. | **PASS** |
| **TC15** | Parking History Saved | Verify completed session is persisted in `parking_records` table. | Record present in history list with duration and total fee. | History record persisted in DB. | **PASS** |
