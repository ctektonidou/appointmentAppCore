package com.appointment.service;

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

        //Check business-level blocked dates
        boolean businessBlocked = !blockedDateRepo
                .findByBusiness_IdAndStaffIsNullAndStartTimeLessThanAndEndTimeGreaterThan(
                        businessId,
                        req.getEndTime(),
                        req.getStartTime()
                ).isEmpty();

        if (businessBlocked) {
            throw new IllegalArgumentException("Business is closed for the selected time range.");
        }

        //Check staff-level blocked dates
        boolean staffBlocked = !blockedDateRepo
                .findByStaff_IdAndStartTimeLessThanAndEndTimeGreaterThan(
                        staff.getId(),
                        req.getEndTime(),
                        req.getStartTime()
                ).isEmpty();

        if (staffBlocked) {
            throw new IllegalArgumentException("Staff is not available (blocked) for the selected time range.");
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
}