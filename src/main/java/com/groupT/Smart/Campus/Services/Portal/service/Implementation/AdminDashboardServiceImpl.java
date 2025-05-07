package com.groupT.Smart.Campus.Services.Portal.service.Implementation;

import com.groupT.Smart.Campus.Services.Portal.entity.Booking;
import com.groupT.Smart.Campus.Services.Portal.entity.DashboardStats;
import com.groupT.Smart.Campus.Services.Portal.entity.MaintenanceRequest;
import com.groupT.Smart.Campus.Services.Portal.entity.User;
import com.groupT.Smart.Campus.Services.Portal.repository.BookingRepository;
import com.groupT.Smart.Campus.Services.Portal.repository.MaintenanceRequestRepository;
import com.groupT.Smart.Campus.Services.Portal.repository.UserRepository;
import com.groupT.Smart.Campus.Services.Portal.service.Interface.AdminDashboardService;
import com.groupT.Smart.Campus.Services.Portal.util.BookingStatus;
import com.groupT.Smart.Campus.Services.Portal.util.MaintenanceStatus;
import com.groupT.Smart.Campus.Services.Portal.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private MaintenanceRequestRepository maintenanceRepository;

    @Override
    public DashboardStats getDashboardStats() {
        // Get counts from different repositories
        List<User> users = userRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();
        List<MaintenanceRequest> maintenanceRequests = maintenanceRepository.findAll();
        
        // User metrics
        int totalUsers = users.size();
        int totalStudents = (int) users.stream().filter(u -> u.getRole() == Role.STUDENT).count();
        int totalLecturers = (int) users.stream().filter(u -> u.getRole() == Role.LECTURER).count();
        int totalAdmins = (int) users.stream().filter(u -> u.getRole() == Role.ADMIN).count();
        
        // Booking metrics
        int totalBookings = bookings.size();
        int pendingBookings = (int) bookings.stream().filter(b -> b.getStatus() == BookingStatus.PENDING).count();
        int completedBookings = (int) bookings.stream().filter(b -> b.getStatus() == BookingStatus.COMPLETED).count();
        int cancelledBookings = (int) bookings.stream().filter(b -> b.getStatus() == BookingStatus.CANCELLED).count();
        
        // Room utilization
        Map<String, Integer> roomUtilization = calculateRoomUtilization(bookings);
        
        // Bookings by day
        Map<String, Integer> bookingsByDay = getBookingsByDay();
        
        // Maintenance metrics
        int totalMaintenanceRequests = maintenanceRequests.size();
        int pendingMaintenanceRequests = (int) maintenanceRequests.stream()
                .filter(m -> m.getStatus() == MaintenanceStatus.PENDING).count();
        int inProgressMaintenanceRequests = (int) maintenanceRequests.stream()
                .filter(m -> m.getStatus() == MaintenanceStatus.IN_PROGRESS).count();
        int resolvedMaintenanceRequests = (int) maintenanceRequests.stream()
                .filter(m -> m.getStatus() == MaintenanceStatus.RESOLVED).count();
        
        // Maintenance by category
        Map<String, Integer> maintenanceByCategory = getMaintenanceByCategory();
        
        return DashboardStats.builder()
                .totalUsers(totalUsers)
                .totalStudents(totalStudents)
                .totalLecturers(totalLecturers)
                .totalAdmins(totalAdmins)
                .totalBookings(totalBookings)
                .pendingBookings(pendingBookings)
                .completedBookings(completedBookings)
                .cancelledBookings(cancelledBookings)
                .roomUtilization(roomUtilization)
                .bookingsByDay(bookingsByDay)
                .totalMaintenanceRequests(totalMaintenanceRequests)
                .pendingMaintenanceRequests(pendingMaintenanceRequests)
                .inProgressMaintenanceRequests(inProgressMaintenanceRequests)
                .resolvedMaintenanceRequests(resolvedMaintenanceRequests)
                .maintenanceByCategory(maintenanceByCategory)
                .build();
    }

    // Add a helper method for room utilization
    private Map<String, Integer> calculateRoomUtilization(List<Booking> bookings) {
        Map<String, Integer> roomUtilization = new HashMap<>();
        
        // Group bookings by location and count
        bookings.stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED || b.getStatus() == BookingStatus.COMPLETED)
                .forEach(booking -> {
                    String location = booking.getLocation();
                    roomUtilization.put(location, roomUtilization.getOrDefault(location, 0) + 1);
                });
        
        return roomUtilization;
    }

    @Override
    public Map<String, Integer> getUserCountByRole() {
        Map<String, Integer> userCountByRole = new HashMap<>();
        userCountByRole.put("STUDENT", (int) userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.STUDENT).count());
        userCountByRole.put("LECTURER", (int) userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.LECTURER).count());
        userCountByRole.put("ADMIN", (int) userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.ADMIN).count());
        return userCountByRole;
    }

    @Override
    public Map<String, Object> getBookingStats() {
        Map<String, Object> bookingStats = new HashMap<>();
        
        bookingStats.put("totalBookings", bookingRepository.count());
        bookingStats.put("pendingBookings", bookingRepository.countByStatus(BookingStatus.PENDING));
        bookingStats.put("confirmedBookings", bookingRepository.countByStatus(BookingStatus.CONFIRMED));
        bookingStats.put("completedBookings", bookingRepository.countByStatus(BookingStatus.COMPLETED));
        bookingStats.put("cancelledBookings", bookingRepository.countByStatus(BookingStatus.CANCELLED));
        
        // Add upcoming bookings
        List<Booking> upcomingBookings = bookingRepository.findUpcomingBookingsByStatus(BookingStatus.CONFIRMED);
        bookingStats.put("upcomingBookingsCount", upcomingBookings.size());
        
        return bookingStats;
    }

    @Override
    public Map<String, Integer> getRoomUtilization() {
        // TODO: Implement room utilization logic
        Map<String, Integer> roomUtilization = new HashMap<>();
        roomUtilization.put("Room A", 10);
        roomUtilization.put("Room B", 5);
        roomUtilization.put("Room C", 15);
        return roomUtilization;
    }

    @Override
    public Map<String, Integer> getBookingsByDay() {
        Map<String, Integer> bookingsByDay = new HashMap<>();
        
        // Initialize all days with 0
        for (DayOfWeek day : DayOfWeek.values()) {
            bookingsByDay.put(day.getDisplayName(TextStyle.FULL, Locale.US), 0);
        }
        
        // Calculate bookings for each day of the week
        List<Booking> bookings = bookingRepository.findAll();
        bookings.forEach(booking -> {
            if (booking.getStartTime() != null) {
                String dayName = booking.getDate().getDayOfWeek()
                        .getDisplayName(TextStyle.FULL, Locale.US);
                bookingsByDay.put(dayName, bookingsByDay.getOrDefault(dayName, 0) + 1);
            }
        });
        
        return bookingsByDay;
    }

    @Override
    public Map<String, Object> getMaintenanceStats() {
        Map<String, Object> maintenanceStats = new HashMap<>();
        
        List<MaintenanceRequest> maintenanceRequests = maintenanceRepository.findAll();
        
        maintenanceStats.put("totalRequests", maintenanceRequests.size());
        maintenanceStats.put("pendingRequests", maintenanceRepository.countByStatus(MaintenanceStatus.PENDING));
        maintenanceStats.put("inProgressRequests", maintenanceRepository.countByStatus(MaintenanceStatus.IN_PROGRESS));
        maintenanceStats.put("resolvedRequests", maintenanceRepository.countByStatus(MaintenanceStatus.RESOLVED));
        maintenanceStats.put("cancelledRequests", maintenanceRepository.countByStatus(MaintenanceStatus.CANCELLED));
        
        // Average resolution time
        maintenanceStats.put("averageResolutionTime", "TO DO");
        
        return maintenanceStats;
    }

    @Override
    public Map<String, Integer> getMaintenanceByCategory() {
        Map<String, Integer> categoryMap = new HashMap<>();
        
        // Use the repository query to get category counts
        List<Object[]> results = maintenanceRepository.countByCategory();
        
        for (Object[] result : results) {
            String category = (String) result[0];
            Long count = (Long) result[1];
            categoryMap.put(category, count.intValue());
        }
        
        return categoryMap;
    }

    @Override
    public Map<String, Object> generateCustomReport(Map<String, Object> parameters) {
        // TODO: Implement custom report generation
        Map<String, Object> report = new HashMap<>();
        report.put("message", "Custom report generation is not implemented yet");
        report.put("parameters", parameters);
        return report;
    }
} 