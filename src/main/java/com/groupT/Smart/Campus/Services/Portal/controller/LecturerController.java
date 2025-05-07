package com.groupT.Smart.Campus.Services.Portal.controller;

import com.groupT.Smart.Campus.Services.Portal.dtos.BookingDTO;
import com.groupT.Smart.Campus.Services.Portal.service.Interface.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lecturer")
@PreAuthorize("hasRole('LECTURER')")
public class LecturerController {

    private final LecturerService lecturerService;

    @GetMapping("/bookings")
    public List<BookingDTO> getLecturerBookings(Principal principal) {
        String username = principal.getName();
       return this.lecturerService.bookingsForLecturer(username);
    }

}
