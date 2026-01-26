package com.appointment.repository;

import com.appointment.model.BusinessService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessServiceRepository extends JpaRepository<BusinessService, Long> {

    List<BusinessService> findByBusiness_IdOrderByNameAsc(Long businessId);

    List<BusinessService> findByBusiness_IdAndActiveTrueOrderByNameAsc(Long businessId);

    Optional<BusinessService> findByIdAndBusiness_Id(Long id, Long businessId);

    boolean existsByBusiness_IdAndNameIgnoreCase(Long businessId, String name);
}
