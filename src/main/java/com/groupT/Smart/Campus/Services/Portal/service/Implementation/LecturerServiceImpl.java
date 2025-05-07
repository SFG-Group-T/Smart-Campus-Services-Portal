package com.groupT.Smart.Campus.Services.Portal.service.Implementation;

import com.groupT.Smart.Campus.Services.Portal.dtos.BookingDTO;
import com.groupT.Smart.Campus.Services.Portal.entity.Booking;
import com.groupT.Smart.Campus.Services.Portal.entity.Lecturer;
import com.groupT.Smart.Campus.Services.Portal.exception.LecturerNotFoundException;
import com.groupT.Smart.Campus.Services.Portal.repository.BookingRepository;
import com.groupT.Smart.Campus.Services.Portal.repository.LecturerRepository;
import com.groupT.Smart.Campus.Services.Portal.service.Interface.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final BookingRepository bookingRepository;
    @Override

    public List<BookingDTO> bookingsForLecturer(String username) {

        Lecturer lecturer = this.lecturerRepository.findByUsername(username)
                .orElseThrow(() -> new LecturerNotFoundException("Lecturer with username" + username + " not found"));

        List<Booking> bookings = this.bookingRepository.findByLecturer_Id(lecturer.getId());

        return bookings.stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());
    }
}
