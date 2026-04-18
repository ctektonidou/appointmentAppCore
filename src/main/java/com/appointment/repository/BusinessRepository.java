package com.appointment.repository;

import com.appointment.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query("""
    select distinct b.location
    from Business b
    where b.location is not null and trim(b.location) <> ''
    order by b.location asc
    """)
    List<String> findDistinctLocations();
}