# Design Patterns Documentation

Detailed documentation of all software design patterns implemented in the **Smart Parking Management System**.

---

## 1. SINGLETON PATTERN

### Definition
The Singleton Pattern is a creational design pattern that ensures a class has only one instance and provides a global point of access to that instance throughout the application.

### Why we use it
In a parking management system, having multiple manager objects would result in inconsistent slot allocations, race conditions, and corrupted parking states. Singleton ensures a single source of truth.

### Where it is used
`com.smartparking.singleton.ParkingLotManager`

### Classes involved
- `ParkingLotManager` (Singleton Class & Observer Subject)

### How it works
1. Declare a `private static volatile` instance variable.
2. Make the constructor `private` so outside classes cannot invoke `new ParkingLotManager()`.
3. Provide a `public static synchronized getInstance()` method that lazily instantiates the object if null and returns the same reference every time.

### Real-world analogy
A central traffic control tower at an airport. There is only one central tower controlling runway allocations to ensure plane safety.

### Advantages
- Guaranteed single instance across thread execution.
- Global access point to central parking state.
- Saves memory by preventing redundant instance allocations.

### Possible disadvantage
Can make unit testing slightly tricky if global state is not reset between tests (resolved in our project via `resetInstance()`).

### Example from our project
```java
ParkingLotManager manager1 = ParkingLotManager.getInstance();
ParkingLotManager manager2 = ParkingLotManager.getInstance();
// manager1 == manager2 returns true (Same memory address)
```

---

## 2. FACTORY PATTERN

### Definition
The Factory Pattern is a creational pattern that provides an interface for creating objects in a superclass, but allows subclasses or factory methods to alter the type of objects that will be created.

### Why we use it
The main application shouldn't need to know the concrete instantiation details for different vehicle types (`Car`, `Bike`, `Truck`). Using a factory decouples the client from concrete classes.

### Where it is used
`com.smartparking.factory.VehicleFactory`

### Classes involved
- `Vehicle` (Abstract Superclass)
- `Car`, `Bike`, `Truck` (Concrete Product Classes)
- `VehicleFactory` (Factory Class)

### How it works
The client passes a string or enum (`CAR`, `BIKE`, `TRUCK`) to `VehicleFactory.createVehicle()`. The factory uses a switch condition to instantiate and return the correct subclass as a generic `Vehicle` reference.

### Real-world analogy
An automobile manufacturing plant. You order a vehicle type ("Car" or "Bike"), and the factory line produces the exact automobile without requiring you to manually assemble engine parts yourself.

### Advantages
- Eliminates direct coupling between client code and concrete vehicle classes.
- Single Responsibility Principle: Vehicle creation logic is isolated in one place.
- Open/Closed Principle: Adding a new vehicle type (e.g. `ElectricScooter`) requires no changes to controller code.

### Possible disadvantage
Requires creating additional subclass files for every new product type.

### Example from our project
```java
// Client does NOT do: Vehicle v = new Car("TN58AB1234", "Arvind");
// Instead, Factory handles it:
Vehicle vehicle = VehicleFactory.createVehicle("CAR", "TN58AB1234", "Arvind");
```

---

## 3. STRATEGY PATTERN

### Definition
The Strategy Pattern is a behavioral design pattern that defines a family of algorithms, encapsulates each one, and makes them interchangeable at runtime.

### Why we use it
Different vehicle categories (`Bike`, `Car`, `Truck`) have completely different pricing rules and hourly rates. Hardcoding `if-else` blocks in fee calculation code leads to fragile, unmaintainable code. Strategy separates the algorithm from fee calculation.

### Where it is used
`com.smartparking.strategy.*`

### Classes involved
- `ParkingFeeStrategy` (Strategy Interface)
- `CarFeeStrategy` (Concrete Strategy: ₹30 1st hr + ₹20/add hr)
- `BikeFeeStrategy` (Concrete Strategy: ₹20 1st hr + ₹10/add hr)
- `TruckFeeStrategy` (Concrete Strategy: ₹50 1st hr + ₹30/add hr)
- `FeeStrategyContext` (Context / Strategy Resolver)

### How it works
The context resolves the appropriate `ParkingFeeStrategy` implementation according to the vehicle's category and executes `calculateFee(durationHours)`.

### Real-world analogy
Payment modes at a checkout counter. You can pay via Credit Card, UPI, or Cash. The billing counter accepts the generic payment strategy interface, and each mode calculates transaction charges independently.

### Advantages
- Cleanly eliminates conditional statements (`if-else` / `switch`) for pricing logic.
- Adding a new pricing policy (e.g., Weekend Discount Strategy) doesn't break existing strategies.
- Easily testable algorithms in isolation.

### Possible disadvantage
Increases total class count by creating separate strategy classes.

### Example from our project
```java
ParkingFeeStrategy strategy = FeeStrategyContext.getStrategy(vehicle.getVehicleType());
double totalFee = strategy.calculateFee(durationHours);
```

---

## 4. OBSERVER PATTERN

### Definition
The Observer Pattern is a behavioral pattern that defines a one-to-many dependency between objects so that when one object (Subject) changes state, all its dependents (Observers) are notified and updated automatically.

### Why we use it
When a slot becomes occupied or available, multiple interested components (User LED Displays, Admin Audit Logs, Dashboard Counters) need real-time notification without tight coupling.

### Where it is used
`com.smartparking.observer.*` and `com.smartparking.singleton.ParkingLotManager`

### Classes involved
- `ParkingSubject` (Subject Interface)
- `ParkingLotManager` (Concrete Subject)
- `ParkingObserver` (Observer Interface)
- `ParkingUser`, `ParkingAdmin` (Concrete Observers)

### How it works
Observers register themselves with `ParkingLotManager.registerObserver()`. Whenever a vehicle is parked or released, `ParkingLotManager` iterates through its observer list and calls `observer.update(message)`.

### Real-world analogy
Subscribing to a YouTube channel or newspaper. When a new video or edition is published, all subscribers receive a push notification automatically.

### Advantages
- Loose coupling between Subject (`ParkingLotManager`) and Observers (`ParkingUser`, `ParkingAdmin`).
- Dynamic registration and removal of listeners at runtime.
- Broadcast communication model.

### Possible disadvantage
If observers perform blocking slow operations, notifications can lag unless handled asynchronously.

### Example from our project
```java
// Subject notifies all subscribers when slot status changes:
String message = "Notification: Slot C01 occupied by TN58AB1234";
notifyObservers(message);
```
