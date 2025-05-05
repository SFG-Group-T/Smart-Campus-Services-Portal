package com.groupT.Smart.Campus.Services.Portal.entity;

import com.groupT.Smart.Campus.Services.Portal.util.MaintenanceStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Table(name = "maintenance_requests")
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    private String location;
    
    private String category;
    
    private String reportedBy;
    
    private LocalDateTime reportedAt;
    
    private LocalDateTime resolvedAt;
    
    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;
    
    private String assignedTo;
    
    private Integer priority; // 1-5 with 5 being highest priority
} 