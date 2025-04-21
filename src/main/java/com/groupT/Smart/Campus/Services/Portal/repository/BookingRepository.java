package com.groupT.Smart.Campus.Services.Portal.repository;

import com.groupT.Smart.Campus.Services.Portal.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByStudentUsername(String username);
}
