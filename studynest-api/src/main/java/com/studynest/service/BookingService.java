package com.studynest.service;

import com.studynest.dto.BookingRequestDTO;
import com.studynest.dto.LoginRequestDTO;
import com.studynest.model.*;
import com.studynest.repository.BookingRepository;
import com.studynest.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final StudentService studentService;

    public BookingService(BookingRepository bookingRepository,
                          SeatRepository seatRepository,
                          StudentService studentService) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.studentService = studentService;
    }

    @Transactional
    public Booking createBooking(BookingRequestDTO dto) {
        LocalDate today = LocalDate.now();
        LocalDate maxAdvanceDate = today.plusDays(7);

        if (dto.getBookingDate().isBefore(today)) {
            throw new IllegalArgumentException("Cannot book seats for past dates.");
        }

        if (dto.getBookingDate().isAfter(maxAdvanceDate)) {
            throw new IllegalArgumentException("Reservations can only be made up to 7 days in advance.");
        }

        if (dto.getStartTime().isAfter(dto.getEndTime()) || dto.getStartTime().equals(dto.getEndTime())) {
            throw new IllegalArgumentException("Start time must precede the end time.");
        }

        Seat seat = seatRepository.findBySeatNumber(dto.getSeatNumber())
                .orElseThrow(() -> new IllegalArgumentException("Seat " + dto.getSeatNumber() + " does not exist."));

        // Check for double booking / overlapping
        List<Booking> overlapping = bookingRepository.findOverlappingBookings(seat, dto.getBookingDate(), dto.getStartTime(), dto.getEndTime());
        if (!overlapping.isEmpty()) {
            throw new IllegalStateException("Seat " + seat.getSeatCode() + " is already reserved for the selected date and time window.");
        }

        // Student resolution
        Student student;
        if (dto.getStudentId() != null) {
            student = studentService.getStudentById(dto.getStudentId());
        } else if (dto.getStudentPhone() != null && !dto.getStudentPhone().trim().isEmpty()) {
            LoginRequestDTO loginDTO = new LoginRequestDTO();
            loginDTO.setName(dto.getStudentName());
            loginDTO.setPhone(dto.getStudentPhone());
            loginDTO.setRole("STUDENT");
            student = studentService.authenticate(loginDTO);
        } else {
            throw new IllegalArgumentException("Student credentials (ID or Phone) are required for reservation.");
        }

        Booking booking = new Booking(student, seat, dto.getBookingDate(), dto.getStartTime(), dto.getEndTime());
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking checkIn(Long bookingId, Long requestingUserId, String requestingUserRole) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + bookingId));

        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.EXPIRED) {
            throw new IllegalStateException("Cannot check into a cancelled or expired reservation.");
        }

        boolean isAdmin = "ADMIN".equalsIgnoreCase(requestingUserRole);
        if (!isAdmin && requestingUserId != null && !booking.getStudent().getId().equals(requestingUserId)) {
            throw new SecurityException("Access Denied: Only the assigned student or an administrator can confirm check-in.");
        }

        booking.setCheckedIn(true);
        booking.setCheckedInAt(LocalDateTime.now());
        booking.setStatus(BookingStatus.CHECKED_IN);

        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking cancelBooking(Long bookingId, Long requestingUserId, String requestingUserRole) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + bookingId));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Reservation has already been cancelled.");
        }

        boolean isAdmin = "ADMIN".equalsIgnoreCase(requestingUserRole);
        
        // Strictly verify that ONLY the student who booked can cancel (or an admin)
        if (!isAdmin) {
            if (requestingUserId == null || !booking.getStudent().getId().equals(requestingUserId)) {
                throw new SecurityException("Access Denied: You are only authorized to cancel your own reservations.");
            }
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public List<Booking> getStudentBookings(Long studentId) {
        return bookingRepository.findByStudentIdOrderByBookingDateDescStartTimeDesc(studentId);
    }

    public List<Booking> getAllBookingsForDate(LocalDate date) {
        return bookingRepository.findByBookingDateAndStatusNot(date, BookingStatus.CANCELLED);
    }

    public List<Booking> getAllActiveBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation record not found with ID: " + id));
    }
}
