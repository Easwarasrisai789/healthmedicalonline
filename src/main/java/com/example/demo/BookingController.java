package com.example.demo;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/bookings")
@CrossOrigin("http://localhost:3000")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping
    public Booking createBooking(
            @RequestParam("appointmentDateTime") String appointmentDateTime,
            @RequestParam("doctorSpecialty") String doctorSpecialty,
            @RequestParam("userName") String userName,
            @RequestParam("reason") String reason,
            @RequestParam("status") String status,
            @RequestParam("image") MultipartFile image) {

        Booking newBooking = new Booking();
        newBooking.setAppointmentDateTime(LocalDateTime.parse(appointmentDateTime));
        newBooking.setDoctorSpecialty(doctorSpecialty);
        newBooking.setUserName(userName);
        newBooking.setReason(reason);
        newBooking.setStatus(status);

        if (image != null && !image.isEmpty()) {
            try {
                newBooking.setImage(image.getBytes()); // Store image as byte array
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to process the image file.");
            }
        }

        return bookingRepository.save(newBooking);
    }



   
    @GetMapping
    public List<Map<String, Object>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(booking -> {
                    Map<String, Object> bookingMap = new HashMap<>();
                    bookingMap.put("id", booking.getId());
                    bookingMap.put("appointmentDateTime", booking.getAppointmentDateTime());
                    bookingMap.put("doctorSpecialty", booking.getDoctorSpecialty());
                    bookingMap.put("userName", booking.getUserName());
                    bookingMap.put("reason", booking.getReason());
                    bookingMap.put("status", booking.getStatus());
                    bookingMap.put("image", booking.getImage() != null ? Base64.getEncoder().encodeToString(booking.getImage()) : null);
                    return bookingMap;
                })
                .collect(Collectors.toList());
    }

    // Get bookings by userName
    @GetMapping("/user/{userName}")
    public List<Booking> getBookingsByUserName(@PathVariable String userName) {
        return bookingRepository.findByUserName(userName);
    }

    // Update booking status
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookingStatus(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setStatus(updatedBooking.getStatus());
            bookingRepository.save(booking);
            return ResponseEntity.ok(booking);
        }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        return bookingRepository.findById(id).map(booking -> {
            bookingRepository.delete(booking);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
    
    
    public class BookingResponse {
        private Long id;
        private LocalDateTime appointmentDateTime;
        private String doctorSpecialty;
        private String userName;
        private String reason;
        private String status;
        private String image; // Base64 encoded image string

        // Constructor, Getters, and Setters
    }

}
