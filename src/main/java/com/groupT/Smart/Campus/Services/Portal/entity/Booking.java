package com.groupT.Smart.Campus.Services.Portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.groupT.Smart.Campus.Services.Portal.util.BookingStatus;
import com.groupT.Smart.Campus.Services.Portal.util.ServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        private ServiceType serviceType; // e.g., "Room", "Appointment"

        @Enumerated(EnumType.STRING)
        private BookingStatus status = BookingStatus.PENDING; // Default status is PENDING

        private String location;
        private String studentUsername; // To link to the logged-in student
        private String lecturerName;

        @DateTimeFormat(pattern = "HH:mm")
        private LocalTime startTime;
        @DateTimeFormat(pattern = "HH:mm")
        private LocalTime endTime;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate date = LocalDate.now();

        @ManyToOne
        @JoinColumn(name = "student_id")
        @JsonIgnore
        private Student student;

        @ManyToOne
        @JoinColumn(name = "lecturer_id")
        @JsonIgnore
        private Lecturer lecturer;
}
