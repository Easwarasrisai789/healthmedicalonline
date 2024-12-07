package com.example.demo;

import com.example.demo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "http://localhost:3000")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping
    public ResponseEntity<String> createPrescription(@RequestBody PrescriptionRequest request) {
        // Find the booking by ID
        var booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Create a new prescription
        Prescription prescription = new Prescription();
        prescription.setBooking(booking);

        for (MedicineRequest medRequest : request.getMedicines()) {
            Medicine medicine = new Medicine();
            medicine.setName(medRequest.getName());
            medicine.setDosage(medRequest.getDosage());
            medicine.setFrequency(medRequest.getFrequency());
            medicine.setPrescription(prescription);
            prescription.getMedicines().add(medicine);
        }

        prescriptionRepository.save(prescription);
        return ResponseEntity.status(HttpStatus.CREATED).body("Prescription added successfully!");
    }
    @GetMapping("/prescriptions/{bookingId}")
    public List<Prescription> getPrescriptionsByBooking(@PathVariable Long bookingId) {
        return prescriptionRepository.findByBookingId(bookingId);
    }
}