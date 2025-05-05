package com.groupT.Smart.Campus.Services.Portal.entity;

import lombok.*;
import java.util.Map;
import java.util.HashMap;

/**
 * This class represents the dashboard statistics data.
 * It's not an entity stored in the database but a data container
 * for dashboard analytics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStats {
    
    // User metrics
    private int totalUsers;
    private int totalStudents;
    private int totalLecturers;
    private int totalAdmins;
    
    // Booking metrics
    private int totalBookings;
    private int pendingBookings;
    private int completedBookings;
    private int cancelledBookings;
    
    // Room utilization metrics
    private Map<String, Integer> roomUtilization = new HashMap<>();
    private Map<String, Integer> bookingsByDay = new HashMap<>();
    
    // Maintenance metrics
    private int totalMaintenanceRequests;
    private int pendingMaintenanceRequests;
    private int inProgressMaintenanceRequests;
    private int resolvedMaintenanceRequests;
    
    // Common maintenance issues (category -> count)
    private Map<String, Integer> maintenanceByCategory = new HashMap<>();
    
    // For reporting features
    private Map<String, Object> customMetrics = new HashMap<>();
} 