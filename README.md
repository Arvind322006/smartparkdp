# Smart Parking Management System Using Design Patterns

**College Mini Project** | Design Patterns Course (3rd Year B.E. / B.Tech Computer Science & Engineering)

---

## 👥 Team Members

1. **Hariharan R**
2. **Arvind B**
3. **Kirubakaran S**

---

## 📌 Problem Statement

Conventional parking management in educational institutions and commercial hubs often suffers from unorganized slot allocation, delayed fee calculation, manual error, and lack of real-time visibility into available space. This project delivers a **Smart Parking Management System** that automates vehicle entry, optimal slot assignment, vehicle category handling, fee calculation, and real-time status notifications while showcasing 4 essential **Software Design Patterns**.

---

## 🎯 Objectives

1. Demonstrate 4 core object-oriented software design patterns (**Singleton**, **Factory**, **Strategy**, **Observer**) in a clean Java enterprise architecture.
2. Automate parking slot assignment and release for **Cars**, **Bikes**, and **Trucks**.
3. Dynamically calculate parking fees based on vehicle type and duration.
4. Provide real-time observer notifications for slot status updates.
5. Provide a responsive, beginner-friendly web UI built with Spring Boot and Thymeleaf.

---

## 🛠️ Technology Stack

| Layer | Technology |
| :--- | :--- |
| **Backend Language** | Java 17+ (Java 25 verified) |
| **Framework** | Spring Boot 3.3.4 |
| **Frontend Templates** | HTML5, CSS3, Vanilla JS, Thymeleaf |
| **Database** | H2 In-Memory Database (`jdbc:h2:mem:parkingdb`) |
| **Build Tool** | Apache Maven 3.9+ |
| **Unit Testing** | JUnit 5 & Spring Boot Test |

---

## 🧩 Design Patterns Implemented

### 1. SINGLETON PATTERN
- **Class**: `com.smartparking.singleton.ParkingLotManager`
- **Purpose**: Ensures ONLY ONE central instance controls parking slots, assignment, and release across the application.
- **Key Code**:
  ```java
  private static volatile ParkingLotManager instance;
  private ParkingLotManager() {}
  public static ParkingLotManager getInstance() { ... }
  ```

### 2. FACTORY PATTERN
- **Class**: `com.smartparking.factory.VehicleFactory`
- **Purpose**: Encapsulates creation of `Car`, `Bike`, and `Truck` objects based on user selection without coupling controller logic to concrete classes.
- **Key Code**:
  ```java
  Vehicle vehicle = VehicleFactory.createVehicle("CAR", "TN58AB1234", "Arvind");
  ```

### 3. STRATEGY PATTERN
- **Interface & Classes**: `ParkingFeeStrategy`, `CarFeeStrategy`, `BikeFeeStrategy`, `TruckFeeStrategy`, `FeeStrategyContext`
- **Purpose**: Defines interchangeable fee algorithms based on vehicle type.
- **Rates**:
  - **Bike**: ₹20 for 1st hour + ₹10/additional hour
  - **Car**: ₹30 for 1st hour + ₹20/additional hour
  - **Truck**: ₹50 for 1st hour + ₹30/additional hour

### 4. OBSERVER PATTERN
- **Subject**: `ParkingLotManager` (implements `ParkingSubject`)
- **Observers**: `ParkingUser`, `ParkingAdmin` (implements `ParkingObserver`)
- **Purpose**: Automatically broadcasts real-time alerts to registered observers whenever a slot status changes (`OCCUPIED` / `AVAILABLE`).

---

## 🚀 How to Run the Project

### Prerequisites
- JDK 17 or higher
- Maven 3.8+

### Step-by-Step Instructions

1. **Clone / Open Project Directory**:
   ```bash
   cd "c:/Users/arvin/OneDrive/Desktop/design pattern"
   ```

2. **Run Unit Tests**:
   ```bash
   mvn test
   ```

3. **Start Application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access Web Application**:
   Open browser at: `http://localhost:8080`

5. **Access H2 Database Console**:
   Open browser at: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:parkingdb`
   - Username: `sa`
   - Password: *(leave blank)*

---

## 🧪 Sample Demonstration Script

1. **Dashboard (`http://localhost:8080`)**: Check initial stats (Total: 18, Available: 18, Occupied: 0).
2. **Park Vehicle**: Navigate to "Park Vehicle", enter Owner: `Arvind`, Vehicle Number: `TN58AB1234`, Type: `CAR`. Click **Park Vehicle**.
3. **Verify Slot**: Navigate to "Parking Slots". Slot **C01** shows `OCCUPIED`.
4. **Check Notifications**: Navigate to "Notifications". Confirm entry notification logged by Observer.
5. **Vehicle Exit**: Navigate to "Current Vehicles", select simulated duration (e.g. 2 hours), click **Exit & Pay**.
6. **Verify Strategy & Release**: System displays fee **₹50** (Car strategy for 2 hours) and slot **C01** reverts to `AVAILABLE`.

---

## 📂 Project Directory Structure

```
design pattern/
├── pom.xml
├── README.md
├── DESIGN_PATTERNS.md
├── VIVA_QUESTIONS.md
├── PRESENTATION.md
├── PROJECT_REPORT.md
├── TESTING.md
└── src/
    ├── main/
    │   ├── java/com/smartparking/
    │   │   ├── SmartParkingApplication.java
    │   │   ├── configuration/DataInitializer.java
    │   │   ├── controller/ParkingController.java
    │   │   ├── factory/VehicleFactory.java
    │   │   ├── model/ (Vehicle, Car, Bike, Truck, ParkingSlot, ParkingRecord, Notification, etc.)
    │   │   ├── observer/ (ParkingObserver, ParkingSubject, ParkingUser, ParkingAdmin)
    │   │   ├── repository/ (VehicleRepository, ParkingSlotRepository, etc.)
    │   │   ├── service/ParkingService.java
    │   │   ├── singleton/ParkingLotManager.java
    │   │   └── strategy/ (ParkingFeeStrategy, CarFeeStrategy, BikeFeeStrategy, TruckFeeStrategy, FeeStrategyContext)
    │   └── resources/
    │       ├── application.properties
    │       ├── static/ (css/style.css, js/script.js)
    │       └── templates/ (dashboard.html, slots.html, park-vehicle.html, current-vehicles.html, history.html, notifications.html)
    └── test/java/com/smartparking/ (JUnit test suite)
```
