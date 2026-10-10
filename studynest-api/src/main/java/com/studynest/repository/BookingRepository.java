package com.studynest.repository;

import com.studynest.model.Booking;
import com.studynest.model.BookingStatus;
import com.studynest.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBookingDateAndStatusNot(LocalDate date, BookingStatus status);

    List<Booking> findByStudentIdOrderByBookingDateDescStartTimeDesc(Long studentId);

    @Query("SELECT b FROM Booking b WHERE b.seat = :seat AND b.bookingDate = :date AND b.status != 'CANCELLED' AND b.status != 'EXPIRED' AND b.startTime < :endTime AND b.endTime > :startTime")
    List<Booking> findOverlappingBookings(@Param("seat") Seat seat,
                                         @Param("date") LocalDate date,
                                         @Param("startTime") LocalTime startTime,
                                         @Param("endTime") LocalTime endTime);

    @Query("SELECT b FROM Booking b WHERE b.bookingDate = :date AND b.status != 'CANCELLED' AND b.status != 'EXPIRED' AND b.startTime < :endTime AND b.endTime > :startTime")
    List<Booking> findAllOverlappingBookings(@Param("date") LocalDate date,
                                            @Param("startTime") LocalTime startTime,
                                            @Param("endTime") LocalTime endTime);

    List<Booking> findByBookingDateAndStatus(LocalDate date, BookingStatus status);
}
