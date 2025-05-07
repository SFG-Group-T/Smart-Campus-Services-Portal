package com.groupT.Smart.Campus.Services.Portal.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends User {

    private String fullName;

    private String employeeNumber;

    private String department;

    private String positionTitle;

    private boolean superAdmin;

    private LocalDate dateHired;

}

