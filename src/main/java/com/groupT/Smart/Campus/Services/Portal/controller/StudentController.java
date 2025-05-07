package com.groupT.Smart.Campus.Services.Portal.controller;

import com.groupT.Smart.Campus.Services.Portal.dtos.response.ResponseObject;
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
    public ResponseObject saveBooking(@RequestBody Booking booking, Principal principal) {
        booking.setStudentUsername(principal.getName());
        this.bookingService.saveBooking(booking);
        return new ResponseObject("Booking saved");
    }

    @DeleteMapping("/bookings/delete/{id}")
    public ResponseObject deleteBooking(@PathVariable Long id) {
        this.bookingService.deleteBooking(id);
        return new ResponseObject("Booking Deleted");
    }

    @PutMapping("/update/bookings/{id}")
    public ResponseObject updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        bookingService.updateBooking(id, booking);
        return new ResponseObject ("Booking updated successfully");
    }

}
