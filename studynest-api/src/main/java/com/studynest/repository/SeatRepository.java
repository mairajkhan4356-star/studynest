package com.studynest.repository;

import com.studynest.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findBySeatNumber(Integer seatNumber);
    Optional<Seat> findBySeatCode(String seatCode);
}
