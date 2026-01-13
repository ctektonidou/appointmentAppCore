package com.appointment.repository;

import com.appointment.model.StaffAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffAvailabilityRepository extends JpaRepository<StaffAvailability, Long> {

    List<StaffAvailability> findByStaff_IdOrderByDayOfWeekAsc(Long staffId);

    Optional<StaffAvailability> findByStaff_IdAndDayOfWeek(Long staffId, Integer dayOfWeek);

    void deleteByStaff_Id(Long staffId);
}
