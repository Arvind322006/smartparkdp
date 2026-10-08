# 35 Essential Viva Questions & Answers

**Smart Parking Management System Using Design Patterns**

---

### Q1. What is a design pattern?
**Ans:** A design pattern is a reusable, proven solution to a commonly occurring software design problem in object-oriented development.

### Q2. Why do we need design patterns?
**Ans:** Design patterns speed up development, provide industry-tested architectural templates, promote code maintainability, and ensure loose coupling.

### Q3. Which design patterns did you use in this project?
**Ans:** We implemented four core patterns: Singleton, Factory, Strategy, and Observer.

### Q4. Why did you choose Singleton Pattern?
**Ans:** To ensure that there is strictly only ONE central `ParkingLotManager` controlling all parking slot allocations across the application.

### Q5. Where is Singleton implemented in your project?
**Ans:** In `com.smartparking.singleton.ParkingLotManager.java`.

### Q6. Why should ParkingLotManager be a Singleton?
**Ans:** If multiple managers existed, they would maintain duplicate slot states, leading to double-booking of parking slots.

### Q7. What is Factory Pattern?
**Ans:** A creational design pattern that encapsulates object creation without exposing instantiation logic directly to the client.

### Q8. Where is Factory used in your project?
**Ans:** In `com.smartparking.factory.VehicleFactory.java` to instantiate `Car`, `Bike`, or `Truck` objects.

### Q9. Why not directly create Car objects using `new Car()` in the controller?
**Ans:** Direct creation tightly couples controller code to concrete vehicle implementations, breaking the Open/Closed principle.

### Q10. What is Strategy Pattern?
**Ans:** A behavioral pattern that defines a family of algorithms, encapsulates each one, and makes them interchangeable at runtime.

### Q11. Why is Strategy useful for parking fees?
**Ans:** Different vehicle categories have different fee rate structures (Car: ₹30+₹20, Bike: ₹20+₹10, Truck: ₹50+₹30). Strategy encapsulates each rate algorithm cleanly.

### Q12. What happens if parking prices change in the future?
**Ans:** We simply update the specific concrete strategy class (e.g. `CarFeeStrategy`) without modifying any controller or service code.

### Q13. What is Observer Pattern?
**Ans:** A behavioral pattern where a Subject maintains a list of Observers and notifies them automatically whenever its state changes.

### Q14. Who are the observers in your project?
**Ans:** `ParkingUser` (representing gate display) and `ParkingAdmin` (representing control room audit log).

### Q15. When are observers notified?
**Ans:** Whenever a vehicle is parked (slot occupied) or a vehicle exits (slot released).

### Q16. What happens when a slot becomes available?
**Ans:** `ParkingLotManager.releaseSlot()` changes status to `AVAILABLE` and invokes `notifyObservers()`, broadcasting alert messages.

### Q17. Difference between Factory and Strategy?
**Ans:** Factory is a *Creational* pattern used to create objects, whereas Strategy is a *Behavioral* pattern used to select algorithms.

### Q18. Difference between Singleton and Factory?
**Ans:** Singleton ensures a single class instance exists, while Factory creates different object instances based on input parameters.

### Q19. Difference between Observer and Strategy?
**Ans:** Observer handles event notifications between dependent objects, while Strategy encapsulates interchangeable calculations/algorithms.

### Q20. What is an interface in Java?
**Ans:** An interface is an abstract contract that defines method signatures that implementing classes must fulfill.

### Q21. Why use interfaces in design patterns?
**Ans:** Interfaces enable polymorphism and loose coupling, allowing code to depend on abstractions rather than concrete classes.

### Q22. What is loose coupling?
**Ans:** Loose coupling means components interact with minimal knowledge of each other's internal implementation details.

### Q23. How does your project achieve loose coupling?
**Ans:** Controllers rely on `VehicleFactory` for object creation and `ParkingFeeStrategy` for calculations rather than hardcoded concrete implementations.

### Q24. What happens if all parking slots are occupied?
**Ans:** The system throws an exception: `"No parking slot available for vehicle type: [TYPE]"` and displays a user-friendly alert.

### Q25. How is parking fee calculated?
**Ans:** `Duration (hours) = Exit Time - Entry Time`. Fee = `Strategy.calculateFee(durationHours)`.

### Q26. How is parking history stored?
**Ans:** In the H2 database inside the `parking_records` table via `ParkingRecordRepository`.

### Q27. What database are you using?
**Ans:** H2 In-Memory Database (`jdbc:h2:mem:parkingdb`).

### Q28. Why H2 database?
**Ans:** It requires zero installation, runs lightweight inside JVM memory, and initializes tables automatically.

### Q29. What happens when the application restarts?
**Ans:** Spring Boot `DataInitializer` re-creates the 18 default slots (B01-B05, C01-C10, T01-T03) automatically.

### Q30. What are the limitations of this system?
**Ans:** In-memory H2 database resets data when app terminates unless file persistence is configured.

### Q31. What future enhancements can be added?
**Ans:** ANPR (Automatic Number Plate Recognition) cameras, online payment gateway integration, and slot reservation booking.

### Q32. What is SOLID?
**Ans:** Five object-oriented design principles: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, and Dependency Inversion.

### Q33. Which SOLID principles are relevant here?
**Ans:** Single Responsibility (isolated strategies and factories) and Open/Closed (adding new vehicle types without modifying existing logic).

### Q34. Explain the complete application workflow.
**Ans:** User enters vehicle details -> Factory creates Vehicle -> Singleton assigns slot -> Observer notifies -> Exit calculates fee via Strategy -> Slot released -> Record saved in History.

### Q35. Explain all four patterns in one sentence each.
**Ans:**
- **Singleton**: Guarantees one central `ParkingLotManager`.
- **Factory**: Encapsulates `Car`/`Bike`/`Truck` creation.
- **Strategy**: Calculates fee based on vehicle type.
- **Observer**: Broadcasts slot status changes to display listeners.
