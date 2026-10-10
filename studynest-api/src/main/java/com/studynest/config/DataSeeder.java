package com.studynest.config;

import com.studynest.model.Booking;
import com.studynest.model.BookingStatus;
import com.studynest.model.Seat;
import com.studynest.model.Student;
import com.studynest.repository.BookingRepository;
import com.studynest.repository.SeatRepository;
import com.studynest.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private final SeatRepository seatRepository;
    private final StudentRepository studentRepository;
    private final BookingRepository bookingRepository;

    public DataSeeder(SeatRepository seatRepository,
                      StudentRepository studentRepository,
                      BookingRepository bookingRepository) {
        this.seatRepository = seatRepository;
        this.studentRepository = studentRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize 20 Study Center Seats
        if (seatRepository.count() == 0) {
            System.out.println("[INFO] Initializing 20 Study Center Seats...");
            
            for (int i = 1; i <= 20; i++) {
                String seatCode = String.format("S-%02d", i);
                String zone;
                boolean windowSide = (i % 4 == 1 || i % 4 == 0);
                boolean powerSocket = (i % 2 != 0);

                if (i <= 6) {
                    zone = "Quiet Study Zone";
                } else if (i <= 14) {
                    zone = "Central Workstation";
                } else {
                    zone = "Window Study Bay";
                }

                Seat seat = new Seat(i, seatCode, zone, powerSocket, windowSide);
                seatRepository.save(seat);
            }
            System.out.println("[INFO] 20 Seats created successfully.");
        }

        // Initialize Administrator and Sample Students
        if (studentRepository.count() == 0) {
            System.out.println("[INFO] Creating Administrator and sample student accounts...");
            
            // Administrator Account
            Student admin = new Student("Center Administrator", "9999999999", "admin@studynest.com", "admin123", "ADMIN", "ADMIN-01");
            studentRepository.save(admin);

            // Student Accounts
            Student s1 = new Student("Alex Johnson", "9876543210", "alex@studynest.com", "pass123", "STUDENT", "SN-1001");
            Student s2 = new Student("Sarah Williams", "9812345678", "sarah@studynest.com", "pass123", "STUDENT", "SN-1002");
            Student s3 = new Student("Michael Brown", "9988776655", "michael@studynest.com", "pass123", "STUDENT", "SN-1003");
            
            studentRepository.save(s1);
            studentRepository.save(s2);
            studentRepository.save(s3);
            
            System.out.println("[INFO] Accounts created successfully.");

            // Seed initial sample reservations for current date
            LocalDate today = LocalDate.now();
            LocalTime slotStart = LocalTime.of(14, 0); // 2:00 PM
            LocalTime slotEnd = LocalTime.of(17, 0);   // 5:00 PM

            // Seat 2: RED (Occupied & Checked-In) by Sarah Williams
            Seat seat2 = seatRepository.findBySeatNumber(2).orElse(null);
            if (seat2 != null) {
                Booking b2 = new Booking(s2, seat2, today, slotStart, slotEnd);
                b2.setCheckedIn(true);
                b2.setCheckedInAt(LocalDateTime.now().minusMinutes(15));
                b2.setStatus(BookingStatus.CHECKED_IN);
                bookingRepository.save(b2);
            }

            // Seat 5: YELLOW (Reserved, Awaiting arrival/check-in) by Alex Johnson
            Seat seat5 = seatRepository.findBySeatNumber(5).orElse(null);
            if (seat5 != null) {
                Booking b5 = new Booking(s1, seat5, today, slotStart, slotEnd);
                b5.setCheckedIn(false);
                b5.setStatus(BookingStatus.CONFIRMED);
                bookingRepository.save(b5);
            }

            // Seat 12: RED (Occupied & Checked-In) by Michael Brown
            Seat seat12 = seatRepository.findBySeatNumber(12).orElse(null);
            if (seat12 != null) {
                Booking b12 = new Booking(s3, seat12, today, slotStart, slotEnd);
                b12.setCheckedIn(true);
                b12.setCheckedInAt(LocalDateTime.now().minusMinutes(30));
                b12.setStatus(BookingStatus.CHECKED_IN);
                bookingRepository.save(b12);
            }

            System.out.println("[INFO] Sample reservations initialized (Available, Occupied, and Awaiting Check-In states).");
        }
    }
}
