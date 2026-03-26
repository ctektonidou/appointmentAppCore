package com.appointment.service;

import com.appointment.dto.appointment.CustomerDashboardAppointmentDto;
import com.appointment.dto.appointment.CustomerDashboardResponseDto;
import com.appointment.dto.appointment.DashboardStatsResponseDto;
import com.appointment.model.Appointment;
import com.appointment.model.Business;
import com.appointment.model.Staff;
import com.appointment.model.enums.AppointmentStatus;
import com.appointment.repository.AppointmentRepository;
import com.appointment.repository.BusinessRepository;
import com.appointment.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    private final AppointmentRepository appointmentRepository;
    private final StaffRepository staffRepository;
    private final BusinessRepository businessRepository;

    public DashboardService(
            AppointmentRepository appointmentRepository,
            StaffRepository staffRepository,
            BusinessRepository businessRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.staffRepository = staffRepository;
        this.businessRepository = businessRepository;
    }

    @Transactional(readOnly = true)
    public CustomerDashboardResponseDto getCustomerDashboard(Long customerUserId) {
        LocalDate today = LocalDate.now();

        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime tomorrowStart = today.plusDays(1).atStartOfDay();

        LocalDate weekStartDate = today.with(DayOfWeek.MONDAY);
        LocalDateTime weekStart = weekStartDate.atStartOfDay();
        LocalDateTime weekEnd = weekStartDate.plusDays(7).atStartOfDay();

        long todayCount = appointmentRepository.countByCustomerUserIdAndStartTimeBetween(
                customerUserId,
                todayStart,
                tomorrowStart
        );

        long weekCount = appointmentRepository.countByCustomerUserIdAndStartTimeBetween(
                customerUserId,
                weekStart,
                weekEnd
        );

        long cancelledWeekCount = appointmentRepository.countByCustomerUserIdAndStatusAndStartTimeBetween(
                customerUserId,
                AppointmentStatus.CANCELLED,
                weekStart,
                weekEnd
        );

        double cancelRate = weekCount == 0 ? 0.0 : (cancelledWeekCount * 100.0) / weekCount;

        List<CustomerDashboardAppointmentDto> todayAppointments =
                appointmentRepository.findByCustomerUserIdAndStartTimeBetweenOrderByStartTimeAsc(
                                customerUserId,
                                todayStart,
                                tomorrowStart
                        ).stream()
                        .map(this::toCustomerDashboardAppointmentDto)
                        .toList();

        return new CustomerDashboardResponseDto(
                todayCount,
                weekCount,
                Math.round(cancelRate * 100.0) / 100.0,
                todayAppointments
        );
    }

    private CustomerDashboardAppointmentDto toCustomerDashboardAppointmentDto(Appointment appointment) {
        return new CustomerDashboardAppointmentDto(
                appointment.getId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getClientName(),
                appointment.getStatus(),
                appointment.getBusiness().getId(),
                appointment.getService().getId(),
                appointment.getStaff().getId()
        );
    }

    @Transactional(readOnly = true)
    public DashboardStatsResponseDto getCustomerStats(Long userId) {
        LocalDate today = LocalDate.now();

        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime tomorrowStart = today.plusDays(1).atStartOfDay();

        LocalDate weekStartDate = today.with(DayOfWeek.MONDAY);
        LocalDateTime weekStart = weekStartDate.atStartOfDay();
        LocalDateTime weekEnd = weekStartDate.plusDays(7).atStartOfDay();

        long todayCount = appointmentRepository.countByCustomerUserIdAndStartTimeBetween(
                userId,
                todayStart,
                tomorrowStart
        );

        long weekCount = appointmentRepository.countByCustomerUserIdAndStartTimeBetween(
                userId,
                weekStart,
                weekEnd
        );

        long cancelledWeekCount = appointmentRepository.countByCustomerUserIdAndStatusAndStartTimeBetween(
                userId,
                AppointmentStatus.CANCELLED,
                weekStart,
                weekEnd
        );

        double cancelRate = weekCount == 0 ? 0.0 : (cancelledWeekCount * 100.0) / weekCount;

        return new DashboardStatsResponseDto(
                todayCount,
                weekCount,
                Math.round(cancelRate * 100.0) / 100.0,
                0L
        );
    }

    @Transactional(readOnly = true)
    public DashboardStatsResponseDto getStaffStatsByUserId(Long userId) {
        Staff staff = staffRepository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found for user id: " + userId));

        Long staffId = staff.getId();

        LocalDate today = LocalDate.now();

        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime tomorrowStart = today.plusDays(1).atStartOfDay();

        LocalDate weekStartDate = today.with(DayOfWeek.MONDAY);
        LocalDateTime weekStart = weekStartDate.atStartOfDay();
        LocalDateTime weekEnd = weekStartDate.plusDays(7).atStartOfDay();

        long todayCount = appointmentRepository.countByStaff_IdAndStartTimeBetween(
                staffId,
                todayStart,
                tomorrowStart
        );

        long weekCount = appointmentRepository.countByStaff_IdAndStartTimeBetween(
                staffId,
                weekStart,
                weekEnd
        );

        long cancelledWeekCount = appointmentRepository.countByStaff_IdAndStatusAndStartTimeBetween(
                staffId,
                AppointmentStatus.CANCELLED,
                weekStart,
                weekEnd
        );

        double cancelRate = weekCount == 0 ? 0.0 : (cancelledWeekCount * 100.0) / weekCount;

        return new DashboardStatsResponseDto(
                todayCount,
                weekCount,
                Math.round(cancelRate * 100.0) / 100.0,
                0L
        );
    }

    @Transactional(readOnly = true)
    public DashboardStatsResponseDto getOwnerStatsByUserId(Long userId) {
        Business business = businessRepository.findFirstByOwnerUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Business not found for owner user id: " + userId));

        Long businessId = business.getId();

        LocalDate today = LocalDate.now();

        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime tomorrowStart = today.plusDays(1).atStartOfDay();

        LocalDate weekStartDate = today.with(DayOfWeek.MONDAY);
        LocalDateTime weekStart = weekStartDate.atStartOfDay();
        LocalDateTime weekEnd = weekStartDate.plusDays(7).atStartOfDay();

        long todayCount = appointmentRepository.countByBusiness_IdAndStartTimeBetween(
                businessId,
                todayStart,
                tomorrowStart
        );

        long weekCount = appointmentRepository.countByBusiness_IdAndStartTimeBetween(
                businessId,
                weekStart,
                weekEnd
        );

        long cancelledWeekCount = appointmentRepository.countByBusiness_IdAndStatusAndStartTimeBetween(
                businessId,
                AppointmentStatus.CANCELLED,
                weekStart,
                weekEnd
        );

        long noShowsCount = appointmentRepository.countByBusiness_IdAndStatusAndStartTimeBetween(
                businessId,
                AppointmentStatus.NO_SHOW,
                weekStart,
                weekEnd
        );

        double cancelRate = weekCount == 0 ? 0.0 : (cancelledWeekCount * 100.0) / weekCount;

        return new DashboardStatsResponseDto(
                todayCount,
                weekCount,
                Math.round(cancelRate * 100.0) / 100.0,
                noShowsCount
        );
    }
}