package com.appointment.repository;

import com.appointment.model.BlockedDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BlockedDateRepository extends JpaRepository<BlockedDate, Long> {

    // Is the whole business blocked on this date?
    boolean existsByBusiness_IdAndStaffIsNullAndDate(Long businessId, LocalDate date);

    // Is this staff member blocked on this date?
    boolean existsByStaff_IdAndDate(Long staffId, LocalDate date);

    List<BlockedDate> findByBusiness_Id(Long businessId);
}
