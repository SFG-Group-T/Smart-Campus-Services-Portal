package com.groupT.Smart.Campus.Services.Portal.controller;

import com.groupT.Smart.Campus.Services.Portal.entity.Booking;
import com.groupT.Smart.Campus.Services.Portal.service.Interface.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    private final BookingService bookingService;

    @GetMapping("/bookings")
    public List<Booking> viewBookings(Principal principal) {
        String username = principal.getName();
       return this.bookingService.getBookingsForStudent(username);
    }

    @PostMapping("/bookings")
    public String saveBooking(@RequestBody Booking booking, Principal principal) {
        booking.setStudentUsername(principal.getName());
        this.bookingService.saveBooking(booking);
        return "Booking saved";
    }

    @DeleteMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        this.bookingService.deleteBooking(id);
        return "Booking Deleted";
    }
}
