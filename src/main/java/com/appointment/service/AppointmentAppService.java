package com.appointment.service;

import com.appointment.dto.appointment.AppointmentListItemResponse;
import com.appointment.dto.appointment.AppointmentResponse;
import com.appointment.dto.appointment.CreateAppointmentRequest;
import com.appointment.model.Appointment;
import com.appointment.model.Business;
import com.appointment.model.BusinessService;
import com.appointment.model.Staff;
import com.appointment.model.enums.AppointmentSource;
import com.appointment.model.enums.AppointmentStatus;
import com.appointment.repository.AppointmentRepository;
import com.appointment.repository.BusinessRepository;
import com.appointment.repository.BusinessServiceRepository;
import com.appointment.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.appointment.repository.BlockedDateRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentAppService {

    private final AppointmentRepository apptRepo;
    private final BusinessRepository businessRepo;
    private final BusinessServiceRepository serviceRepo;
    private final StaffRepository staffRepo;
    private final BlockedDateRepository blockedDateRepo;

    public AppointmentAppService(AppointmentRepository apptRepo,
                                 BusinessRepository businessRepo,
                                 BusinessServiceRepository serviceRepo,
                                 StaffRepository staffRepo,
                                 BlockedDateRepository blockedDateRepo) {
        this.apptRepo = apptRepo;
        this.businessRepo = businessRepo;
        this.serviceRepo = serviceRepo;
        this.staffRepo = staffRepo;
        this.blockedDateRepo = blockedDateRepo;
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> list(Long businessId, LocalDateTime from, LocalDateTime to) {
        return apptRepo.findByBusiness_IdAndStartTimeBetweenOrderByStartTimeAsc(businessId, from, to)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getOne(Long businessId, Long appointmentId) {
        Appointment a = apptRepo.findByIdAndBusiness_Id(appointmentId, businessId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found for this business."));
        return toResponse(a);
    }

    @Transactional
    public AppointmentResponse create(Long businessId, CreateAppointmentRequest req) {
        Business business = businessRepo.findById(businessId)
                .orElseThrow(() -> new IllegalArgumentException("Business not found: " + businessId));

        BusinessService service = serviceRepo.findByIdAndBusiness_Id(req.getServiceId(), businessId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found for this business."));

        Staff staff = staffRepo.findByIdAndBusiness_Id(req.getStaffId(), businessId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found for this business."));

        if (!req.getStartTime().isBefore(req.getEndTime())) {
            throw new IllegalArgumentException("startTime must be before endTime.");
        }
        // We assume appointments do not cross midnight
        LocalDate appointmentDate = req.getStartTime().toLocalDate();

        // 1) business-level blocked date (whole day closed)
        boolean businessBlocked = blockedDateRepo
                .existsByBusiness_IdAndStaffIsNullAndDate(businessId, appointmentDate);

        if (businessBlocked) {
            throw new IllegalArgumentException("Business is closed on this date.");
        }

        // 2) staff-level blocked date (staff is off that whole day)
        boolean staffBlocked = blockedDateRepo
                .existsByStaff_IdAndDate(staff.getId(), appointmentDate);

        if (staffBlocked) {
            throw new IllegalArgumentException("Staff is not available (blocked) on this date.");
        }

        boolean overlaps = apptRepo.existsByStaff_IdAndStartTimeLessThanAndEndTimeGreaterThan(
                staff.getId(),
                req.getEndTime(),
                req.getStartTime()
        );
        if (overlaps) {
            throw new IllegalArgumentException("Staff is not available for the selected time range.");
        }

        Appointment a = new Appointment();
        a.setBusiness(business);
        a.setService(service);
        a.setStaff(staff);

        a.setCustomerUserId(req.getCustomerUserId());

        a.setClientName(req.getClientName());
        a.setClientEmail(req.getClientEmail());
        a.setClientPhone(req.getClientPhone());
        a.setClientNotes(req.getClientNotes());

        a.setStartTime(req.getStartTime());
        a.setEndTime(req.getEndTime());

        a.setStatus(AppointmentStatus.SCHEDULED);
        a.setSource(AppointmentSource.INTERNAL);

        return toResponse(apptRepo.save(a));
    }

    @Transactional
    public AppointmentResponse setStatus(Long businessId, Long appointmentId, AppointmentStatus status) {
        Appointment a = apptRepo.findByIdAndBusiness_Id(appointmentId, businessId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found for this business."));
        a.setStatus(status);
        return toResponse(apptRepo.save(a));
    }

    private AppointmentResponse toResponse(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getBusiness().getId(),
                a.getService().getId(),
                a.getStaff().getId(),
                a.getCustomerUserId(),
                a.getClientName(),
                a.getClientEmail(),
                a.getClientPhone(),
                a.getClientNotes(),
                a.getStartTime(),
                a.getEndTime(),
                a.getStatus(),
                a.getSource()
        );
    }

    @Transactional(readOnly = true)
    public List<AppointmentListItemResponse> listForOwnerUser(
            Long userId,
            LocalDateTime from,
            LocalDateTime to,
            AppointmentStatus status,
            Long staffId,
            Long serviceId,
            String search
    ) {
        Business business = businessRepo.findFirstByOwnerUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Business not found for owner user."));

        List<Appointment> appointments = apptRepo.findByBusiness_IdOrderByStartTimeDesc(business.getId());

        String searchTerm = search == null ? "" : search.trim().toLowerCase();

        return appointments.stream()
                .filter(a -> from == null || !a.getStartTime().isBefore(from))
                .filter(a -> to == null || !a.getStartTime().isAfter(to))
                .filter(a -> status == null || a.getStatus() == status)
                .filter(a -> staffId == null || a.getStaff().getId().equals(staffId))
                .filter(a -> serviceId == null || a.getService().getId().equals(serviceId))
                .filter(a -> {
                    if (searchTerm.isBlank()) return true;

                    String haystack = String.join(" ",
                            safe(a.getClientName()),
                            safe(a.getService().getName()),
                            safe(a.getStaff().getFirstName()),
                            safe(a.getStaff().getLastName())
                    ).toLowerCase();

                    return haystack.contains(searchTerm);
                })
                .map(this::toListItemResponse)
                .toList();
    }

    private AppointmentListItemResponse toListItemResponse(Appointment a) {
        String staffName = (safe(a.getStaff().getFirstName()) + " " + safe(a.getStaff().getLastName())).trim();

        return new AppointmentListItemResponse(
                a.getId(),
                a.getStartTime(),
                a.getEndTime(),
                a.getClientName(),
                a.getBusiness().getName(),
                a.getService().getName(),
                staffName,
                a.getStatus()
        );
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    @Transactional(readOnly = true)
    public List<AppointmentListItemResponse> listForStaffUser(
            Long userId,
            LocalDateTime from,
            LocalDateTime to,
            AppointmentStatus status,
            Long serviceId,
            String search
    ) {
        Staff staff = staffRepo.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found for user."));

        List<Appointment> appointments = apptRepo.findByStaff_IdOrderByStartTimeDesc(staff.getId());

        String searchTerm = search == null ? "" : search.trim().toLowerCase();

        return appointments.stream()
                .filter(a -> from == null || !a.getStartTime().isBefore(from))
                .filter(a -> to == null || !a.getStartTime().isAfter(to))
                .filter(a -> status == null || a.getStatus() == status)
                .filter(a -> serviceId == null || a.getService().getId().equals(serviceId))
                .filter(a -> {
                    if (searchTerm.isBlank()) return true;

                    String haystack = String.join(" ",
                            safe(a.getClientName()),
                            safe(a.getService().getName())
                    ).toLowerCase();

                    return haystack.contains(searchTerm);
                })
                .map(this::toListItemResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentListItemResponse> listForCustomerUser(
            Long userId,
            LocalDateTime from,
            LocalDateTime to,
            AppointmentStatus status,
            Long businessId,
            Long serviceId,
            String search
    ) {
        List<Appointment> appointments = apptRepo.findByCustomerUserIdOrderByStartTimeDesc(userId);

        String searchTerm = search == null ? "" : search.trim().toLowerCase();

        return appointments.stream()
                .filter(a -> from == null || !a.getStartTime().isBefore(from))
                .filter(a -> to == null || !a.getStartTime().isAfter(to))
                .filter(a -> status == null || a.getStatus() == status)
                .filter(a -> businessId == null || a.getBusiness().getId().equals(businessId))
                .filter(a -> serviceId == null || a.getService().getId().equals(serviceId))
                .filter(a -> {
                    if (searchTerm.isBlank()) return true;

                    String haystack = String.join(" ",
                            safe(a.getBusiness().getName()),
                            safe(a.getService().getName()),
                            safe(a.getStaff().getFirstName()),
                            safe(a.getStaff().getLastName())
                    ).toLowerCase();

                    return haystack.contains(searchTerm);
                })
                .map(this::toListItemResponse)
                .toList();
    }
}