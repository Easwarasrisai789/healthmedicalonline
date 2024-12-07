package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    // Create a new booking
    @PostMapping
    public Booking createBooking(@RequestBody Booking newBooking) {
        System.out.println("Received booking: " + newBooking);
        return bookingRepository.save(newBooking);
    }

    // Get all bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
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

}
