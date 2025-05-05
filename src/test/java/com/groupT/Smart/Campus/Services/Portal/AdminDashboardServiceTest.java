package com.groupT.Smart.Campus.Services.Portal;

import com.groupT.Smart.Campus.Services.Portal.entity.Booking;
import com.groupT.Smart.Campus.Services.Portal.entity.DashboardStats;
import com.groupT.Smart.Campus.Services.Portal.entity.MaintenanceRequest;
import com.groupT.Smart.Campus.Services.Portal.entity.User;
import com.groupT.Smart.Campus.Services.Portal.repository.BookingRepository;
import com.groupT.Smart.Campus.Services.Portal.repository.MaintenanceRequestRepository;
import com.groupT.Smart.Campus.Services.Portal.repository.UserRepository;
import com.groupT.Smart.Campus.Services.Portal.service.Implementation.AdminDashboardServiceImpl;
import com.groupT.Smart.Campus.Services.Portal.util.BookingStatus;
import com.groupT.Smart.Campus.Services.Portal.util.MaintenanceStatus;
import com.groupT.Smart.Campus.Services.Portal.util.Role;
import com.groupT.Smart.Campus.Services.Portal.util.ServiceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminDashboardServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private MaintenanceRequestRepository maintenanceRepository;

    @InjectMocks
    private AdminDashboardServiceImpl adminDashboardService;

    private User student1, student2, lecturer, admin;
    private Booking booking1, booking2, booking3;
    private MaintenanceRequest request1, request2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create test users
        student1 = User.builder().id(1L).username("student1").role(Role.STUDENT).build();
        student2 = User.builder().id(2L).username("student2").role(Role.STUDENT).build();
        lecturer = User.builder().id(3L).username("lecturer").role(Role.LECTURER).build();
        admin = User.builder().id(4L).username("admin").role(Role.ADMIN).build();

        // Create test bookings
        LocalDateTime now = LocalDateTime.now();
        booking1 = new Booking();
        booking1.setId(1L);
        booking1.setLocation("Room A");
        booking1.setServiceType(ServiceType.ROOM);
        booking1.setStatus(BookingStatus.CONFIRMED);
        booking1.setStartTime(now.plusDays(1));
        booking1.setEndTime(now.plusDays(1).plusHours(2));

        booking2 = new Booking();
        booking2.setId(2L);
        booking2.setLocation("Room B");
        booking2.setServiceType(ServiceType.ROOM);
        booking2.setStatus(BookingStatus.PENDING);
        booking2.setStartTime(now.plusDays(2));
        booking2.setEndTime(now.plusDays(2).plusHours(1));

        booking3 = new Booking();
        booking3.setId(3L);
        booking3.setLocation("Room A");
        booking3.setServiceType(ServiceType.APPOINTMENT);
        booking3.setStatus(BookingStatus.COMPLETED);
        booking3.setStartTime(now.minusDays(1));
        booking3.setEndTime(now.minusDays(1).plusHours(1));

        // Create test maintenance requests
        request1 = MaintenanceRequest.builder()
                .id(1L)
                .title("Broken Chair")
                .description("Chair in Room A is broken")
                .location("Room A")
                .category("Furniture")
                .status(MaintenanceStatus.PENDING)
                .reportedAt(now.minusDays(2))
                .reportedBy("student1")
                .build();

        request2 = MaintenanceRequest.builder()
                .id(2L)
                .title("Projector not working")
                .description("Projector in Room B is not turning on")
                .location("Room B")
                .category("Electronics")
                .status(MaintenanceStatus.IN_PROGRESS)
                .reportedAt(now.minusDays(3))
                .reportedBy("lecturer")
                .assignedTo("maintenance_staff")
                .build();

        // Setup repository mock responses
        when(userRepository.findAll()).thenReturn(Arrays.asList(student1, student2, lecturer, admin));
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2, booking3));
        when(maintenanceRepository.findAll()).thenReturn(Arrays.asList(request1, request2));
        
        // Setup counts by status
        when(bookingRepository.countByStatus(BookingStatus.PENDING)).thenReturn(1);
        when(bookingRepository.countByStatus(BookingStatus.CONFIRMED)).thenReturn(1);
        when(bookingRepository.countByStatus(BookingStatus.COMPLETED)).thenReturn(1);
        when(bookingRepository.countByStatus(BookingStatus.CANCELLED)).thenReturn(0);
        
        when(maintenanceRepository.countByStatus(MaintenanceStatus.PENDING)).thenReturn(1);
        when(maintenanceRepository.countByStatus(MaintenanceStatus.IN_PROGRESS)).thenReturn(1);
        when(maintenanceRepository.countByStatus(MaintenanceStatus.RESOLVED)).thenReturn(0);
        when(maintenanceRepository.countByStatus(MaintenanceStatus.CANCELLED)).thenReturn(0);
        
        // Setup maintenance category stats
        when(maintenanceRepository.countByCategory()).thenReturn(Arrays.asList(
                new Object[]{"Furniture", 1L},
                new Object[]{"Electronics", 1L}
        ));
        
        // Setup upcoming bookings
        when(bookingRepository.findUpcomingBookingsByStatus(BookingStatus.CONFIRMED))
                .thenReturn(Arrays.asList(booking1));
    }

    @Test
    void testGetDashboardStats() {
        DashboardStats stats = adminDashboardService.getDashboardStats();
        
        // Verify user metrics
        assertEquals(4, stats.getTotalUsers());
        assertEquals(2, stats.getTotalStudents());
        assertEquals(1, stats.getTotalLecturers());
        assertEquals(1, stats.getTotalAdmins());
        
        // Verify booking metrics
        assertEquals(3, stats.getTotalBookings());
        assertEquals(1, stats.getPendingBookings());
        assertEquals(1, stats.getCompletedBookings());
        assertEquals(0, stats.getCancelledBookings());
        
        // Verify room utilization
        assertTrue(stats.getRoomUtilization().containsKey("Room A"));
        assertEquals(1, stats.getRoomUtilization().size()); // Only Room A should have confirmed/completed bookings
        
        // Verify maintenance metrics
        assertEquals(2, stats.getTotalMaintenanceRequests());
        assertEquals(1, stats.getPendingMaintenanceRequests());
        assertEquals(1, stats.getInProgressMaintenanceRequests());
        assertEquals(0, stats.getResolvedMaintenanceRequests());
        
        // Verify maintenance categories
        assertEquals(2, stats.getMaintenanceByCategory().size());
    }

    @Test
    void testGetUserCountByRole() {
        Map<String, Integer> userCounts = adminDashboardService.getUserCountByRole();
        
        assertEquals(2, userCounts.get("STUDENT"));
        assertEquals(1, userCounts.get("LECTURER"));
        assertEquals(1, userCounts.get("ADMIN"));
    }

    @Test
    void testGetBookingStats() {
        Map<String, Object> bookingStats = adminDashboardService.getBookingStats();
        
        assertEquals(1, bookingStats.get("pendingBookings"));
        assertEquals(1, bookingStats.get("confirmedBookings"));
        assertEquals(1, bookingStats.get("completedBookings"));
        assertEquals(0, bookingStats.get("cancelledBookings"));
        assertEquals(1, bookingStats.get("upcomingBookingsCount"));
    }

    @Test
    void testGetMaintenanceStats() {
        Map<String, Object> maintenanceStats = adminDashboardService.getMaintenanceStats();
        
        assertEquals(2, maintenanceStats.get("totalRequests"));
        assertEquals(1, maintenanceStats.get("pendingRequests"));
        assertEquals(1, maintenanceStats.get("inProgressRequests"));
        assertEquals(0, maintenanceStats.get("resolvedRequests"));
    }

    @Test
    void testGetMaintenanceByCategory() {
        Map<String, Integer> categoryStats = adminDashboardService.getMaintenanceByCategory();
        
        assertEquals(2, categoryStats.size());
        assertEquals(1, categoryStats.get("Furniture"));
        assertEquals(1, categoryStats.get("Electronics"));
    }
} 