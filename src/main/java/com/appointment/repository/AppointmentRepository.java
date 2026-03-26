package com.appointment.repository;

import com.appointment.model.Appointment;
import com.appointment.model.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByBusiness_IdAndStartTimeBetweenOrderByStartTimeAsc(
            Long businessId, LocalDateTime from, LocalDateTime to
    );

    List<Appointment> findByBusiness_IdAndStaff_IdAndStartTimeBetweenOrderByStartTimeAsc(
            Long businessId, Long staffId, LocalDateTime from, LocalDateTime to
    );

    Optional<Appointment> findByIdAndBusiness_Id(Long appointmentId, Long businessId);

    Optional<Appointment> findByManageToken(String manageToken);

    List<Appointment> findByStaff_IdAndStatusInAndStartTimeLessThanAndEndTimeGreaterThan(
            Long staffId,
            List<AppointmentStatus> statuses,
            LocalDateTime endExclusive,
            LocalDateTime startExclusive
    );

    boolean existsByStaff_IdAndStartTimeLessThanAndEndTimeGreaterThan(
            Long staffId, LocalDateTime endExclusive, LocalDateTime startExclusive
    );

    long countByCustomerUserIdAndStartTimeBetween(
            Long customerUserId,
            LocalDateTime from,
            LocalDateTime to
    );

    long countByCustomerUserIdAndStatusAndStartTimeBetween(
            Long customerUserId,
            AppointmentStatus status,
            LocalDateTime from,
            LocalDateTime to
    );

    List<Appointment> findByCustomerUserIdAndStartTimeBetweenOrderByStartTimeAsc(
            Long customerUserId,
            LocalDateTime from,
            LocalDateTime to
    );

    long countByStaff_IdAndStartTimeBetween(
            Long staffId,
            LocalDateTime from,
            LocalDateTime to
    );

    long countByStaff_IdAndStatusAndStartTimeBetween(
            Long staffId,
            AppointmentStatus status,
            LocalDateTime from,
            LocalDateTime to
    );

    long countByBusiness_IdAndStartTimeBetween(
            Long businessId,
            LocalDateTime from,
            LocalDateTime to
    );

    long countByBusiness_IdAndStatusAndStartTimeBetween(
            Long businessId,
            AppointmentStatus status,
            LocalDateTime from,
            LocalDateTime to
    );

    List<Appointment> findByBusiness_IdOrderByStartTimeDesc(Long businessId);

    List<Appointment> findByStaff_IdOrderByStartTimeDesc(Long staffId);
    
    List<Appointment> findByCustomerUserIdOrderByStartTimeDesc(Long customerUserId);
}