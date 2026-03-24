package com.appointment.repository;

import com.appointment.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    List<Staff> findByBusiness_IdOrderByFirstNameAscLastNameAsc(Long businessId);

    List<Staff> findByBusiness_IdAndIsActiveTrueOrderByFirstNameAscLastNameAsc(Long businessId);

    List<Staff> findByBusiness_IdAndIsActiveTrueOrderByFirstNameAsc(Long businessId);

    List<Staff> findByBusiness_IdOrderByFirstNameAsc(Long businessId);

    Optional<Staff> findByIdAndBusiness_Id(Long staffId, Long businessId);

    boolean existsByBusiness_IdAndEmailIgnoreCase(Long businessId, String email);

    Optional<Staff> findByBusinessIdAndEmailIgnoreCase(Long businessId, String email);
}
