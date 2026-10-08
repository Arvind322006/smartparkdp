# Presentation Slides Outline

**Smart Parking Management System Using Design Patterns**

---

### SLIDE 1: Title & Team Information
- **Project Title**: Smart Parking Management System Using Design Patterns
- **Course**: Object Oriented Analysis & Design / Software Design Patterns
- **Team Members**:
  1. Hariharan R
  2. Arvind B
  3. Kirubakaran S
- **Department**: Computer Science & Engineering (3rd Year)

---

### SLIDE 2: Problem Statement
- Traditional parking systems face manual delays, improper slot utilization, human errors in fee calculation, and lack of live occupancy tracking.
- Hardcoded software implementations result in rigid, fragile codebases that are difficult to extend when adding new vehicle types or pricing rules.

---

### SLIDE 3: Objectives
- Build a lightweight, full-stack Smart Parking Management System.
- Demonstrate **4 fundamental software design patterns** in real enterprise Java code:
  1. **Singleton Pattern**
  2. **Factory Pattern**
  3. **Strategy Pattern**
  4. **Observer Pattern**
- Automate slot allocation, vehicle category handling, pricing calculation, and status alerts.

---

### SLIDE 4: Proposed System & Architecture
- **Tech Stack**: Java 17+, Spring Boot 3.3, Thymeleaf, H2 Database, Maven.
- **Key Modules**:
  - Web UI (Dashboard, Slot Grid, Park Form, History)
  - Controller & Service Layer
  - Design Pattern Package Layer
  - H2 JPA Repositories

---

### SLIDE 5: System Architecture Diagram
```
+-------------------------------------------------------------------+
|                        SPRING BOOT WEB UI                         |
|      (Thymeleaf Templates: Dashboard, Slots, Park, History)       |
+-------------------------------------------------------------------+
                                  |
                                  v
+-------------------------------------------------------------------+
|                         PARKING SERVICE                           |
+-------------------------------------------------------------------+
    |                   |                     |                 |
    v                   v                     v                 v
+-----------+   +---------------+   +-------------------+   +----------+
| SINGLETON |   |    FACTORY    |   |     STRATEGY      |   | OBSERVER |
|ParkingLot |   |VehicleFactory |   |ParkingFeeStrategy |   | Parking  |
|  Manager  |   |(Car/Bike/Truck|   |(Car/Bike/TruckFee)|   | Observer |
+-----------+   +---------------+   +-------------------+   +----------+
```

---

### SLIDE 6: Design Pattern 1 — Singleton Pattern
- **Component**: `ParkingLotManager`
- **Role**: Maintains central control over 18 parking slots (B01-B05, C01-C10, T01-T03).
- **Implementation**: Private constructor + Private static instance + Synchronized `getInstance()`.
- **Benefit**: Ensures single point of truth across concurrent Web threads.

---

### SLIDE 7: Design Pattern 2 — Factory Pattern
- **Component**: `VehicleFactory`
- **Role**: Creates `Car`, `Bike`, or `Truck` objects based on user selection.
- **Implementation**: Static method `createVehicle(type, number, owner)`.
- **Benefit**: Decouples application logic from concrete vehicle constructors.

---

### SLIDE 8: Design Pattern 3 — Strategy Pattern
- **Component**: `ParkingFeeStrategy` interface & concrete strategies.
- **Rates**:
  - Bike: ₹20 (1st hr) + ₹10/add hr
  - Car: ₹30 (1st hr) + ₹20/add hr
  - Truck: ₹50 (1st hr) + ₹30/add hr
- **Benefit**: Encapsulates pricing algorithms into interchangeable modules.

---

### SLIDE 9: Design Pattern 4 — Observer Pattern & Live Demo
- **Component**: `ParkingSubject` (`ParkingLotManager`) and `ParkingObserver` (`ParkingUser`, `ParkingAdmin`).
- **Trigger**: Dispatches notification events whenever a vehicle enters or exits.
- **Live Demo Workflow**:
  1. Park Car (`TN58AB1234`) -> Assigns `C01` -> Triggers Observer.
  2. Exit Car -> Strategy calculates ₹50 fee -> Slot `C01` freed.

---

### SLIDE 10: Conclusion & Future Enhancements
- **Conclusion**: Successfully implemented a complete, runnable college mini-project demonstrating clean design patterns and enterprise Java best practices.
- **Future Enhancements**:
  - ANPR (Automatic License Plate Recognition) camera integration.
  - Payment gateway integration (UPI / NetBanking).
  - Online slot reservation mobile application.
