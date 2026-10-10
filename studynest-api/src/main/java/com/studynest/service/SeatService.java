package com.studynest.service;

import com.studynest.dto.SeatStatusDTO;
import com.studynest.model.Booking;
import com.studynest.model.Seat;
import com.studynest.model.SeatStatus;
import com.studynest.repository.BookingRepository;
import com.studynest.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    public SeatService(SeatRepository seatRepository, BookingRepository bookingRepository) {
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<SeatStatusDTO> getSeatMapForSlot(LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Seat> seats = seatRepository.findAll();
        // Ensure sorted by seat number 1..20
        seats.sort((a, b) -> Integer.compare(a.getSeatNumber(), b.getSeatNumber()));

        List<SeatStatusDTO> dtoList = new ArrayList<>();

        for (Seat seat : seats) {
            List<Booking> overlapping = bookingRepository.findOverlappingBookings(seat, date, startTime, endTime);

            SeatStatusDTO dto = new SeatStatusDTO();
            dto.setSeatId(seat.getId());
            dto.setSeatNumber(seat.getSeatNumber());
            dto.setSeatCode(seat.getSeatCode());
            dto.setZone(seat.getZone());
            dto.setHasPowerSocket(seat.isHasPowerSocket());
            dto.setWindowSide(seat.isWindowSide());

            if (overlapping.isEmpty()) {
                // GREEN: Available
                dto.setStatus(SeatStatus.GREEN);
                dto.setStatusText("Available");
                dto.setStatusNote("Seat is vacant and ready to reserve.");
                dto.setCheckedIn(false);
            } else {
                Booking activeBooking = overlapping.get(0);
                dto.setBookingId(activeBooking.getId());
                dto.setBookedByStudentId(activeBooking.getStudent().getId());
                dto.setBookedByStudentName(activeBooking.getStudent().getName());
                dto.setBookedByStudentPhone(activeBooking.getStudent().getPhone());
                dto.setStartTime(activeBooking.getStartTime().format(TIME_FORMATTER));
                dto.setEndTime(activeBooking.getEndTime().format(TIME_FORMATTER));
                dto.setCheckedIn(activeBooking.isCheckedIn());

                if (activeBooking.isCheckedIn()) {
                    // RED: Occupied & Checked in
                    dto.setStatus(SeatStatus.RED);
                    dto.setStatusText("Occupied");
                    dto.setStatusNote("Reserved and checked in by " + activeBooking.getStudent().getName() + ".");
                } else {
                    // YELLOW: Reserved - student not arrived yet (15 min grace window)
                    dto.setStatus(SeatStatus.YELLOW);
                    dto.setStatusText("Awaiting Check-In");
                    dto.setStatusNote("Reserved by " + activeBooking.getStudent().getName() + ". 15-minute arrival grace period in progress.");
                }
            }

            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<Seat> getAllSeats() {
        List<Seat> seats = seatRepository.findAll();
        seats.sort((a, b) -> Integer.compare(a.getSeatNumber(), b.getSeatNumber()));
        return seats;
    }
}
