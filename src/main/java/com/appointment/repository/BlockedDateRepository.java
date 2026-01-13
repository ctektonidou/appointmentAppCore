package com.appointment.repository;

import com.appointment.model.BlockedDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BlockedDateRepository extends JpaRepository<BlockedDate, Long> {

    // Business-wide blocked dates + staff-specific in a date range
    List<BlockedDate> findByBusiness_IdAndDateBetweenOrderByDateAsc(Long businessId, LocalDate from, LocalDate to);

    // Only business-wide blocks (staff_id IS NULL)
    List<BlockedDate> findByBusiness_IdAndStaffIsNullAndDateBetweenOrderByDateAsc(Long businessId, LocalDate from, LocalDate to);

    // Only staff-specific blocks
    List<BlockedDate> findByBusiness_IdAndStaff_IdAndDateBetweenOrderByDateAsc(Long businessId, Long staffId, LocalDate from, LocalDate to);
}
