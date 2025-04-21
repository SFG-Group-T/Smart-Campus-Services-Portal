package com.groupT.Smart.Campus.Services.Portal.service.Implementation;

import com.groupT.Smart.Campus.Services.Portal.entity.Booking;
import com.groupT.Smart.Campus.Services.Portal.exception.BookingNotFoundException;
import com.groupT.Smart.Campus.Services.Portal.exception.NotFoundBookingsException;
import com.groupT.Smart.Campus.Services.Portal.repository.BookingRepository;
import com.groupT.Smart.Campus.Services.Portal.service.Interface.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;

    @Override
    public List<Booking> getBookingsForStudent(String username) {

        List<Booking> userBookingsList = this.repository.findByStudentUsername(username);

        if (userBookingsList.isEmpty()) {
            throw new NotFoundBookingsException("No Bookings Available for the Student");
        }
        return userBookingsList;
    }

    @Override
    public void saveBooking(Booking booking) {
        repository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID not found"));

        this.repository.delete(booking);
    }
}
