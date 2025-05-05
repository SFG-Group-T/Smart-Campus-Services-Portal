package com.groupT.Smart.Campus.Services.Portal.service.Interface;

import com.groupT.Smart.Campus.Services.Portal.entity.DashboardStats;
import java.util.Map;

public interface AdminDashboardService {

    /**
     * Get overall dashboard statistics
     */
    DashboardStats getDashboardStats();
    
    /**
     * Get user count by role
     */
    Map<String, Integer> getUserCountByRole();
    
    /**
     * Get booking statistics
     */
    Map<String, Object> getBookingStats();
    
    /**
     * Get room utilization data
     */
    Map<String, Integer> getRoomUtilization();
    
    /**
     * Get bookings by day of week
     */
    Map<String, Integer> getBookingsByDay();
    
    /**
     * Get maintenance request statistics
     */
    Map<String, Object> getMaintenanceStats();
    
    /**
     * Get maintenance requests by category
     */
    Map<String, Integer> getMaintenanceByCategory();
    
    /**
     * Generate a custom report based on parameters
     */
    Map<String, Object> generateCustomReport(Map<String, Object> parameters);
} 