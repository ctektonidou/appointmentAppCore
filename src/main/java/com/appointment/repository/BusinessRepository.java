package com.appointment.repository;

import com.appointment.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {

    List<Business> findByOwnerUserIdOrderByNameAsc(Long ownerUserId);

    Optional<Business> findByIdAndOwnerUserId(Long businessId, Long ownerUserId);

    Optional<Business> findFirstByOwnerUserId(Long ownerUserId);

    boolean existsByOwnerUserIdAndNameIgnoreCase(Long ownerUserId, String name);

    List<Business> findByNameContainingIgnoreCaseOrderByNameAsc(String name);

    List<Business> findByIndustry_IdOrderByNameAsc(Long industryId);

    List<Business> findByNameContainingIgnoreCaseAndIndustry_IdOrderByNameAsc(
            String name,
            Long industryId
    );

    List<Business> findByLocationIgnoreCaseOrderByNameAsc(String location);

    List<Business> findByNameContainingIgnoreCaseAndIndustry_IdAndLocationIgnoreCaseOrderByNameAsc(
            String name,
            Long industryId,
            String location
    );
}