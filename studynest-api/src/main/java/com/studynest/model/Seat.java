package com.studynest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer seatNumber; // 1 to 20

    @Column(nullable = false)
    private String seatCode; // S-01, S-02, ...

    private String zone; // Quiet Zone, Window Zone, Central Hub

    private boolean hasPowerSocket;

    private boolean isWindowSide;

    public Seat() {}

    public Seat(Integer seatNumber, String seatCode, String zone, boolean hasPowerSocket, boolean isWindowSide) {
        this.seatNumber = seatNumber;
        this.seatCode = seatCode;
        this.zone = zone;
        this.hasPowerSocket = hasPowerSocket;
        this.isWindowSide = isWindowSide;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public boolean isHasPowerSocket() {
        return hasPowerSocket;
    }

    public void setHasPowerSocket(boolean hasPowerSocket) {
        this.hasPowerSocket = hasPowerSocket;
    }

    public boolean isWindowSide() {
        return isWindowSide;
    }

    public void setWindowSide(boolean windowSide) {
        isWindowSide = windowSide;
    }
}
