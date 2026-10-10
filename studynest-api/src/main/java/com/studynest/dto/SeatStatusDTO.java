package com.studynest.dto;

import com.studynest.model.SeatStatus;

public class SeatStatusDTO {

    private Long seatId;
    private Integer seatNumber;
    private String seatCode;
    private String zone;
    private boolean hasPowerSocket;
    private boolean isWindowSide;
    
    // Status color: GREEN, RED, YELLOW
    private SeatStatus status; 
    private String statusText; // Human readable description (English only, no emoji)
    
    // Active Booking Details if occupied/booked
    private Long bookingId;
    private Long bookedByStudentId;
    private String bookedByStudentName;
    private String bookedByStudentPhone;
    private String startTime;
    private String endTime;
    private boolean checkedIn;
    private String statusNote; // e.g. "Reserved - Awaiting Check-In"

    public SeatStatusDTO() {}

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
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
        this.isWindowSide = windowSide;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getBookedByStudentId() {
        return bookedByStudentId;
    }

    public void setBookedByStudentId(Long bookedByStudentId) {
        this.bookedByStudentId = bookedByStudentId;
    }

    public String getBookedByStudentName() {
        return bookedByStudentName;
    }

    public void setBookedByStudentName(String bookedByStudentName) {
        this.bookedByStudentName = bookedByStudentName;
    }

    public String getBookedByStudentPhone() {
        return bookedByStudentPhone;
    }

    public void setBookedByStudentPhone(String bookedByStudentPhone) {
        this.bookedByStudentPhone = bookedByStudentPhone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public String getStatusNote() {
        return statusNote;
    }

    public void setStatusNote(String statusNote) {
        this.statusNote = statusNote;
    }
}
