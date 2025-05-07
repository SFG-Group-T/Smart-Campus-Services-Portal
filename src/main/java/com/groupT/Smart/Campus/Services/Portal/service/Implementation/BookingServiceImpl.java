package com.groupT.Smart.Campus.Services.Portal.service.Implementation;

import com.groupT.Smart.Campus.Services.Portal.entity.Booking;
import com.groupT.Smart.Campus.Services.Portal.entity.Lecturer;
import com.groupT.Smart.Campus.Services.Portal.entity.Student;
import com.groupT.Smart.Campus.Services.Portal.exception.*;
import com.groupT.Smart.Campus.Services.Portal.repository.BookingRepository;
import com.groupT.Smart.Campus.Services.Portal.repository.LecturerRepository;
import com.groupT.Smart.Campus.Services.Portal.repository.StudentRepository;
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
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;

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

        if ("appointment".equalsIgnoreCase(booking.getServiceType().toString())) {

            String lecturerName = booking.getLecturerName(); // from dropdown menu frontend

            if (lecturerName == null || lecturerName.isBlank()) {
                throw new LecturerNotFoundException("Lecturer name must be provided for appointments.");
            }

            Lecturer lecturer = this.lecturerRepository.findByFullName(lecturerName)
                    .orElseThrow(() -> new LecturerNotFoundException("Lecturer with name " + lecturerName + " not found"));

            booking.setLecturer(lecturer);
            booking.setLecturerName(lecturerName);
        }

        String studentUsername = booking.getStudentUsername();

        Student student = this.studentRepository.findByUsername(studentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Student with username " + studentUsername + " not found"));

        booking.setStudent(student);

        this.repository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = this.repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID not found"));

        this.repository.delete(booking);
    }

    @Override
    public void updateBooking(Long id, Booking booking) {

        Booking existingBooking = repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID not found"));

        boolean serviceTypeChanged = !existingBooking.getServiceType().equals(booking.getServiceType());
        boolean studentChanged = booking.getStudent() != null && !existingBooking.getStudent().equals(booking.getStudent());
        boolean otherFieldsChanged = serviceTypeChanged || studentChanged;

        if (otherFieldsChanged) {
            throw new FieldModificationNotAllowedException("Only location, startTime, and endTime can be updated.");
        }

        existingBooking.setLocation(booking.getLocation());
        existingBooking.setStartTime(booking.getStartTime());
        existingBooking.setEndTime(booking.getEndTime());

        this.repository.save(existingBooking);
    }
}
