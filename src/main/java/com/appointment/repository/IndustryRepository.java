package com.appointment.repository;

import com.appointment.model.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IndustryRepository extends JpaRepository<Industry, Long> {

    List<Industry> findByIsActiveTrueOrderByIndustryNameAsc();

    Optional<Industry> findByIndustryCodeIgnoreCase(String industryCode);
}