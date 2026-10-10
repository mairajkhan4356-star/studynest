package com.studynest.controller;

import com.studynest.dto.ApiResponseDTO;
import com.studynest.dto.SeatStatusDTO;
import com.studynest.model.Seat;
import com.studynest.service.SeatService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/map")
    public ResponseEntity<ApiResponseDTO<List<SeatStatusDTO>>> getSeatMap(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {

        if (date == null) {
            date = LocalDate.now();
        }
        if (startTime == null) {
            startTime = LocalTime.of(14, 0); // 2 PM default
        }
        if (endTime == null) {
            endTime = LocalTime.of(17, 0); // 5 PM default
        }

        List<SeatStatusDTO> seatMap = seatService.getSeatMapForSlot(date, startTime, endTime);
        return ResponseEntity.ok(ApiResponseDTO.success("20 Seat grid map generated successfully", seatMap));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<Seat>>> getAllSeats() {
        return ResponseEntity.ok(ApiResponseDTO.success("Seats retrieved successfully", seatService.getAllSeats()));
    }
}
