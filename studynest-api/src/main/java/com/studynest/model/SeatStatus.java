package com.studynest.model;

public enum SeatStatus {
    GREEN,   // Available / Khaali hai
    RED,     // Booked & Occupied / Checked-in
    YELLOW   // Booked but student not arrived yet (15 min grace wait)
}
