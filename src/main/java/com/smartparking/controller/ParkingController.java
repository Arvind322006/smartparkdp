package com.smartparking.controller;

import com.smartparking.model.ParkingRecord;
import com.smartparking.model.ParkingSlot;
import com.smartparking.model.Vehicle;
import com.smartparking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class ParkingController {

    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    // 1. Dashboard View
    @GetMapping({"/", "/dashboard"})
    public String viewDashboard(Model model) {
        Map<String, Object> stats = parkingService.getDashboardStatistics();
        model.addAllAttributes(stats);
        model.addAttribute("recentNotifications", parkingService.getRecentNotifications());
        model.addAttribute("activePage", "dashboard");
        return "dashboard";
    }

    // 2. Parking Slots View
    @GetMapping("/slots")
    public String viewSlots(Model model) {
        parkingService.syncSingletonCache();
        List<ParkingSlot> slots = parkingService.getAllSlots();
        model.addAttribute("slots", slots);
        model.addAttribute("activePage", "slots");
        return "slots";
    }

    // 3. Park Vehicle Form (GET)
    @GetMapping("/park")
    public String parkVehicleForm(Model model) {
        model.addAttribute("activePage", "park");
        return "park-vehicle";
    }

    // 3. Park Vehicle Action (POST)
    @PostMapping("/park")
    public String processParkVehicle(@RequestParam("ownerName") String ownerName,
                                     @RequestParam("vehicleNumber") String vehicleNumber,
                                     @RequestParam("vehicleType") String vehicleType,
                                     RedirectAttributes redirectAttributes) {
        try {
            Vehicle parkedVehicle = parkingService.parkVehicle(ownerName, vehicleNumber, vehicleType);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Vehicle parked successfully! Assigned Slot: " + parkedVehicle.getAssignedSlot() + " for Vehicle: " + parkedVehicle.getVehicleNumber());
            redirectAttributes.addFlashAttribute("parkedVehicle", parkedVehicle);
            return "redirect:/park";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("ownerName", ownerName);
            redirectAttributes.addFlashAttribute("vehicleNumber", vehicleNumber);
            redirectAttributes.addFlashAttribute("selectedType", vehicleType);
            return "redirect:/park";
        }
    }

    // 4. Current Vehicles View
    @GetMapping("/current-vehicles")
    public String viewCurrentVehicles(Model model) {
        List<Vehicle> currentVehicles = parkingService.getCurrentVehicles();
        model.addAttribute("currentVehicles", currentVehicles);
        model.addAttribute("activePage", "current");
        return "current-vehicles";
    }

    // 5. Vehicle Exit Action (POST)
    @PostMapping("/exit")
    public String processVehicleExit(@RequestParam("vehicleNumber") String vehicleNumber,
                                     @RequestParam(value = "simulatedHours", required = false, defaultValue = "2") Integer simulatedHours,
                                     RedirectAttributes redirectAttributes) {
        try {
            ParkingRecord record = parkingService.processVehicleExit(vehicleNumber, simulatedHours);
            redirectAttributes.addFlashAttribute("exitSuccessMessage",
                    "Vehicle " + record.getVehicleNumber() + " exited successfully from Slot " + record.getSlotId() + ". Parking Duration: " + record.getDurationHours() + " hr(s). Parking Fee: ₹" + (long) record.getParkingFee());
            return "redirect:/current-vehicles";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/current-vehicles";
        }
    }

    // 6. Parking History View
    @GetMapping("/history")
    public String viewHistory(Model model) {
        List<ParkingRecord> history = parkingService.getParkingHistory();
        model.addAttribute("history", history);
        model.addAttribute("activePage", "history");
        return "history";
    }

    // 7. Observer Notifications Log View
    @GetMapping("/notifications")
    public String viewNotifications(Model model) {
        model.addAttribute("notifications", parkingService.getAllNotifications());
        model.addAttribute("activePage", "notifications");
        return "notifications";
    }
}
