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

    // For conflict checks: any overlap with existing appointments for staff
    List<Appointment> findByStaff_IdAndStatusInAndStartTimeLessThanAndEndTimeGreaterThan(
            Long staffId,
            List<AppointmentStatus> statuses,
            LocalDateTime endExclusive,
            LocalDateTime startExclusive
    );
}
