package com.appointment.repository;

import com.appointment.model.BlockedDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BlockedDateRepository extends JpaRepository<BlockedDate, Long> {

    // Business-wide blocked dates + staff-specific in a date range
    List<BlockedDate> findByBusiness_IdAndDateBetweenOrderByDateAsc(Long businessId, LocalDate from, LocalDate to);

    // Only business-wide blocks (staff_id IS NULL)
    List<BlockedDate> findByBusiness_IdAndStaffIsNullAndDateBetweenOrderByDateAsc(Long businessId, LocalDate from, LocalDate to);

    // Only staff-specific blocks
    List<BlockedDate> findByBusiness_IdAndStaff_IdAndDateBetweenOrderByDateAsc(Long businessId, Long staffId, LocalDate from, LocalDate to);

    //Business-level blocked dates (staff_id IS NULL) Overlapping time range check
    List<BlockedDate> findByBusiness_IdAndStaffIsNullAndStartTimeLessThanAndEndTimeGreaterThan(Long businessId, LocalDateTime endExclusive, LocalDateTime startExclusive);

    // Staff-level blocked dates Overlapping time range check
    List<BlockedDate> findByStaff_IdAndStartTimeLessThanAndEndTimeGreaterThan(Long staffId, LocalDateTime endExclusive, LocalDateTime startExclusive);

    // Is the whole business blocked on this date?
    boolean existsByBusiness_IdAndStaffIsNullAndDate(Long businessId, LocalDate date);

    // Is this staff member blocked on this date?
    boolean existsByStaff_IdAndDate(Long staffId, LocalDate date);

    List<BlockedDate> findByBusiness_Id(Long businessId);
}
