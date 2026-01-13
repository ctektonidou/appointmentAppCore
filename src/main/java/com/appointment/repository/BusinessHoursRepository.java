package com.appointment.repository;

import com.appointment.model.BusinessHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {

    List<BusinessHours> findByBusiness_IdOrderByDayOfWeekAsc(Long businessId);

    Optional<BusinessHours> findByBusiness_IdAndDayOfWeek(Long businessId, Integer dayOfWeek);

    void deleteByBusiness_Id(Long businessId);
}
