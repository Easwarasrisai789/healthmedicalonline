package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React frontend
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private DoctorRepository doctorRepository ;

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DoctorLoginRequest loginRequest) {
        System.out.println("Received login request: Doctor ID " + loginRequest.getDoctorId() + ", Password " + loginRequest.getPassword());

        boolean isAuthenticated = doctorService.authenticateDoctor(loginRequest.getDoctorId(), loginRequest.getPassword());

        if (isAuthenticated) {
            System.out.println("Login successful for Doctor ID " + loginRequest.getDoctorId());
            return ResponseEntity.ok("Login successful");
        } else {
            System.out.println("Invalid credentials for Doctor ID " + loginRequest.getDoctorId());
            return ResponseEntity.status(401).body("Invalid Doctor ID or Password");
        }
    }
    
    @GetMapping
    public List<Doctor> getDoctors() {
        return doctorService.getAllDoctors();
    }

}
