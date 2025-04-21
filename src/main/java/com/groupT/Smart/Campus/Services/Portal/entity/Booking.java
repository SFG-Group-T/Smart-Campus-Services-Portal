package com.groupT.Smart.Campus.Services.Portal.entity;

import com.groupT.Smart.Campus.Services.Portal.util.ServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

        private String location;
        private String studentUsername; // To link to the logged-in student
        private String lecturerName;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

}
