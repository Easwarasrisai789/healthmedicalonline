package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // Method to authenticate doctor based on doctorId and password
    public boolean authenticateDoctor(int doctorId, String password) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if (doctor == null) {
            System.out.println("Doctor with ID " + doctorId + " not found");
            return false;  // Doctor not found
        }

        // Compare stored password with provided password
        boolean isPasswordMatch = doctor.getPassword().equals(password);
        System.out.println("Doctor password match: " + isPasswordMatch);
        return isPasswordMatch;
    }
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
