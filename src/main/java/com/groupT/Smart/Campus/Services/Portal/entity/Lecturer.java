package com.groupT.Smart.Campus.Services.Portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lecturers")
public class Lecturer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String department;
    private String office;

    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
