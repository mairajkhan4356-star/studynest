package com.studynest.controller;

import com.studynest.dto.ApiResponseDTO;
import com.studynest.dto.BookingRequestDTO;
import com.studynest.model.Booking;
import com.studynest.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<Booking>> createBooking(@Valid @RequestBody BookingRequestDTO requestDTO) {
        try {
            Booking booking = bookingService.createBooking(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.success("Seat " + booking.getSeat().getSeatCode() + " successfully reserved.", booking));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Reservation request failed: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/check-in")
    public ResponseEntity<ApiResponseDTO<Booking>> checkIn(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "STUDENT") String role) {
        try {
            Booking booking = bookingService.checkIn(id, userId, role);
            return ResponseEntity.ok(ApiResponseDTO.success("Check-in confirmed. Seat is now marked Occupied.", booking));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponseDTO.error(e.getMessage()));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponseDTO<Booking>> cancelBooking(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "STUDENT") String role) {
        try {
            Booking booking = bookingService.cancelBooking(id, userId, role);
            return ResponseEntity.ok(ApiResponseDTO.success("Reservation cancelled successfully.", booking));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponseDTO.error(e.getMessage()));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponseDTO<List<Booking>>> getStudentBookings(@PathVariable Long studentId) {
        List<Booking> bookings = bookingService.getStudentBookings(studentId);
        return ResponseEntity.ok(ApiResponseDTO.success("Student reservations retrieved.", bookings));
    }

    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponseDTO<List<Booking>>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllActiveBookings();
        return ResponseEntity.ok(ApiResponseDTO.success("All center reservations retrieved.", bookings));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<ApiResponseDTO<List<Booking>>> getBookingsForDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Booking> bookings = bookingService.getAllBookingsForDate(date);
        return ResponseEntity.ok(ApiResponseDTO.success("Reservations for " + date + " retrieved.", bookings));
    }
}
