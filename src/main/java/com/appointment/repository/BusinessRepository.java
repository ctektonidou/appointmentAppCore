package com.appointment.repository;

import com.appointment.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {

    List<Business> findByOwnerUserIdOrderByNameAsc(Long ownerUserId);

    Optional<Business> findByIdAndOwnerUserId(Long businessId, Long ownerUserId);

    boolean existsByOwnerUserIdAndNameIgnoreCase(Long ownerUserId, String name);
}
