package com.groupT.Smart.Campus.Services.Portal.entity;

import com.groupT.Smart.Campus.Services.Portal.util.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String cellNumbers;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;
}
