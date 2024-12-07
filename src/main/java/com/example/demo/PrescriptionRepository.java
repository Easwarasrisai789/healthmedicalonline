package com.example.demo;

import com.example.demo.Prescription;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

	List<Prescription> findByBookingId(Long bookingId);
}