package com.groupT.Smart.Campus.Services.Portal.repository;

import com.groupT.Smart.Campus.Services.Portal.entity.Booking;
import com.groupT.Smart.Campus.Services.Portal.util.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByStudentUsername(String username);
    
    List<Booking> findByStatus(BookingStatus status);
    
    List<Booking> findByLocation(String location);
    
    // Find booking count by status
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = ?1")
    int countByStatus(BookingStatus status);
    
    // Find bookings for a specific day
    @Query("SELECT b FROM Booking b WHERE DATE(b.startTime) = DATE(?1)")
    List<Booking> findBookingsForDay(LocalDateTime dateTime);
    
    // Find bookings by location and status
    List<Booking> findByLocationAndStatus(String location, BookingStatus status);
    
    // Find upcoming bookings
    @Query("SELECT b FROM Booking b WHERE b.startTime > CURRENT_TIMESTAMP AND b.status = ?1 ORDER BY b.startTime ASC")
    List<Booking> findUpcomingBookingsByStatus(BookingStatus status);
}
