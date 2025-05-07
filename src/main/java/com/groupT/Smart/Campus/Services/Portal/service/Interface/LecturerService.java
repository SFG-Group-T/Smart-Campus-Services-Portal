package com.groupT.Smart.Campus.Services.Portal.service.Interface;

import com.groupT.Smart.Campus.Services.Portal.dtos.BookingDTO;

import java.util.List;

public interface LecturerService {
    public List<BookingDTO> bookingsForLecturer(String username);
}
