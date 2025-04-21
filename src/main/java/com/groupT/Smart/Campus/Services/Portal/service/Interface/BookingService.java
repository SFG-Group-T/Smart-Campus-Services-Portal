package com.groupT.Smart.Campus.Services.Portal.service.Interface;

import com.groupT.Smart.Campus.Services.Portal.entity.Booking;

import java.util.List;

public interface BookingService {

    public List<Booking> getBookingsForStudent(String username);
    public void saveBooking(Booking booking);
    public void deleteBooking(Long id);
}
