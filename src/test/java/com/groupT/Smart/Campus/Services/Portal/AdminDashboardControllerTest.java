package com.groupT.Smart.Campus.Services.Portal;

import com.groupT.Smart.Campus.Services.Portal.controller.AdminDashboardController;
import com.groupT.Smart.Campus.Services.Portal.entity.DashboardStats;
import com.groupT.Smart.Campus.Services.Portal.service.Interface.AdminDashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminDashboardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminDashboardService adminDashboardService;

    @InjectMocks
    private AdminDashboardController adminDashboardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminDashboardController).build();
    }

    @Test
    void testGetDashboard() throws Exception {
        // Create a mock DashboardStats object
        DashboardStats stats = DashboardStats.builder()
                .totalUsers(100)
                .totalStudents(80)
                .totalLecturers(15)
                .totalAdmins(5)
                .totalBookings(50)
                .pendingBookings(10)
                .completedBookings(30)
                .cancelledBookings(10)
                .build();
        
        when(adminDashboardService.getDashboardStats()).thenReturn(stats);
        
        mockMvc.perform(get("/api/admin/dashboard")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUsers").value(100))
                .andExpect(jsonPath("$.totalStudents").value(80))
                .andExpect(jsonPath("$.totalLecturers").value(15))
                .andExpect(jsonPath("$.totalAdmins").value(5))
                .andExpect(jsonPath("$.totalBookings").value(50));
        
        verify(adminDashboardService, times(1)).getDashboardStats();
    }

    @Test
    void testGetUserStats() throws Exception {
        Map<String, Integer> userStats = new HashMap<>();
        userStats.put("STUDENT", 80);
        userStats.put("LECTURER", 15);
        userStats.put("ADMIN", 5);
        
        when(adminDashboardService.getUserCountByRole()).thenReturn(userStats);
        
        mockMvc.perform(get("/api/admin/dashboard/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.STUDENT").value(80))
                .andExpect(jsonPath("$.LECTURER").value(15))
                .andExpect(jsonPath("$.ADMIN").value(5));
        
        verify(adminDashboardService, times(1)).getUserCountByRole();
    }

    @Test
    void testGetBookingStats() throws Exception {
        Map<String, Object> bookingStats = new HashMap<>();
        bookingStats.put("totalBookings", 50);
        bookingStats.put("pendingBookings", 10);
        bookingStats.put("confirmedBookings", 20);
        bookingStats.put("completedBookings", 30);
        bookingStats.put("cancelledBookings", 10);
        
        when(adminDashboardService.getBookingStats()).thenReturn(bookingStats);
        
        mockMvc.perform(get("/api/admin/dashboard/bookings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalBookings").value(50))
                .andExpect(jsonPath("$.pendingBookings").value(10))
                .andExpect(jsonPath("$.confirmedBookings").value(20))
                .andExpect(jsonPath("$.completedBookings").value(30))
                .andExpect(jsonPath("$.cancelledBookings").value(10));
        
        verify(adminDashboardService, times(1)).getBookingStats();
    }

    @Test
    void testGetMaintenanceStats() throws Exception {
        Map<String, Object> maintenanceStats = new HashMap<>();
        maintenanceStats.put("totalRequests", 40);
        maintenanceStats.put("pendingRequests", 15);
        maintenanceStats.put("inProgressRequests", 10);
        maintenanceStats.put("resolvedRequests", 15);
        
        when(adminDashboardService.getMaintenanceStats()).thenReturn(maintenanceStats);
        
        mockMvc.perform(get("/api/admin/dashboard/maintenance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRequests").value(40))
                .andExpect(jsonPath("$.pendingRequests").value(15))
                .andExpect(jsonPath("$.inProgressRequests").value(10))
                .andExpect(jsonPath("$.resolvedRequests").value(15));
        
        verify(adminDashboardService, times(1)).getMaintenanceStats();
    }

    @Test
    void testGenerateCustomReport() throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startDate", "2025-01-01");
        parameters.put("endDate", "2025-12-31");
        parameters.put("reportType", "monthly");
        
        Map<String, Object> reportResult = new HashMap<>();
        reportResult.put("message", "Custom report generated");
        reportResult.put("parameters", parameters);
        
        when(adminDashboardService.generateCustomReport(any())).thenReturn(reportResult);
        
        mockMvc.perform(post("/api/admin/dashboard/reports/custom")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2025-01-01\",\"endDate\":\"2025-12-31\",\"reportType\":\"monthly\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Custom report generated"));
        
        verify(adminDashboardService, times(1)).generateCustomReport(any());
    }
} 