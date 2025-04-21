package com.groupT.Smart.Campus.Services.Portal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lecturer")
@PreAuthorize("hasRole('LECTURER')")
public class LecturerController {

    @GetMapping("/add")
    public String addUser(){
        return "I am lecturer";
    }
}
