package com.groupT.Smart.Campus.Services.Portal.controller;

import com.groupT.Smart.Campus.Services.Portal.entity.DashboardStats;
import com.groupT.Smart.Campus.Services.Portal.service.Interface.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    /**
     * Get dashboard overview with all stats
     */
    @GetMapping
    public ResponseEntity<DashboardStats> getDashboard() {
        return ResponseEntity.ok(adminDashboardService.getDashboardStats());
    }

    /**
     * Get user statistics
     */
    @GetMapping("/users")
    public ResponseEntity<Map<String, Integer>> getUserStats() {
        return ResponseEntity.ok(adminDashboardService.getUserCountByRole());
    }

    /**
     * Get booking statistics
     */
    @GetMapping("/bookings")
    public ResponseEntity<Map<String, Object>> getBookingStats() {
        return ResponseEntity.ok(adminDashboardService.getBookingStats());
    }

    /**
     * Get room utilization data
     */
    @GetMapping("/rooms/utilization")
    public ResponseEntity<Map<String, Integer>> getRoomUtilization() {
        return ResponseEntity.ok(adminDashboardService.getRoomUtilization());
    }

    /**
     * Get bookings by day of week
     */
    @GetMapping("/bookings/by-day")
    public ResponseEntity<Map<String, Integer>> getBookingsByDay() {
        return ResponseEntity.ok(adminDashboardService.getBookingsByDay());
    }

    /**
     * Get maintenance request statistics
     */
    @GetMapping("/maintenance")
    public ResponseEntity<Map<String, Object>> getMaintenanceStats() {
        return ResponseEntity.ok(adminDashboardService.getMaintenanceStats());
    }

    /**
     * Get maintenance requests by category
     */
    @GetMapping("/maintenance/by-category")
    public ResponseEntity<Map<String, Integer>> getMaintenanceByCategory() {
        return ResponseEntity.ok(adminDashboardService.getMaintenanceByCategory());
    }

    /**
     * Generate a custom report
     */
    @PostMapping("/reports/custom")
    public ResponseEntity<Map<String, Object>> generateCustomReport(@RequestBody Map<String, Object> parameters) {
        return ResponseEntity.ok(adminDashboardService.generateCustomReport(parameters));
    }
} 