package com.groupT.Smart.Campus.Services.Portal.dtos;

import com.groupT.Smart.Campus.Services.Portal.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private Long id;
    private String serviceType;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String location;
    private String studentEmail;
    private String studentNumber;

    public BookingDTO(Booking booking) {
        this.id = booking.getId();
        this.serviceType = booking.getServiceType().toString();
        this.date = booking.getDate();
        this.location = booking.getLocation();
        this.startTime = booking.getStartTime();
        this.endTime = booking.getEndTime();

        if (booking.getStudent() != null) {
            this.studentEmail = booking.getStudent().getEmail();
            this.studentNumber = booking.getStudent().getStudentNumber();
        }
    }
}

