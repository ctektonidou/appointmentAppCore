package com.appointment.service;

import com.appointment.dto.staff.CreateStaffRequest;
import com.appointment.dto.staff.StaffResponse;
import com.appointment.dto.staff.UpdateStaffRequest;
import com.appointment.model.Business;
import com.appointment.model.Staff;
import com.appointment.repository.BusinessRepository;
import com.appointment.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StaffAppService {

    private final StaffRepository staffRepo;
    private final BusinessRepository businessRepo;

    public StaffAppService(StaffRepository staffRepo, BusinessRepository businessRepo) {
        this.staffRepo = staffRepo;
        this.businessRepo = businessRepo;
    }

    @Transactional(readOnly = true)
    public List<StaffResponse> list(Long businessId, boolean activeOnly) {
        List<Staff> staff = activeOnly
                ? staffRepo.findByBusiness_IdAndIsActiveTrueOrderByFirstNameAsc(businessId)
                : staffRepo.findByBusiness_IdOrderByFirstNameAsc(businessId);

        return staff.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public StaffResponse getOne(Long businessId, Long staffId) {
        Staff s = staffRepo.findByIdAndBusiness_Id(staffId, businessId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found for this business."));
        return toResponse(s);
    }

    @Transactional
    public StaffResponse create(Long businessId, CreateStaffRequest req) {
        Business business = businessRepo.findById(businessId)
                .orElseThrow(() -> new IllegalArgumentException("Business not found: " + businessId));

        if (req.getEmail() != null && staffRepo.existsByBusiness_IdAndEmailIgnoreCase(businessId, req.getEmail())) {
            throw new IllegalArgumentException("Staff email already exists for this business.");
        }

        Staff s = new Staff(); // make sure Staff has a public/protected no-arg ctor
        s.setBusiness(business);
        s.setFirstName(req.getFirstName());
        s.setLastName(req.getLastName());
        s.setEmail(req.getEmail());
        s.setPhone(req.getPhone());
        s.setColorHex(req.getColorHex());
        s.setActive(req.getIsActive() != null ? req.getIsActive() : true);

        // optional: user linkage (only if you have User entity/repo wired)
        // For now, store it later when User model is ready.

        return toResponse(staffRepo.save(s));
    }

    @Transactional
    public StaffResponse update(Long businessId, Long staffId, UpdateStaffRequest req) {
        Staff s = staffRepo.findByIdAndBusiness_Id(staffId, businessId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found for this business."));

        if (req.getEmail() != null
                && s.getEmail() != null
                && !s.getEmail().equalsIgnoreCase(req.getEmail())
                && staffRepo.existsByBusiness_IdAndEmailIgnoreCase(businessId, req.getEmail())) {
            throw new IllegalArgumentException("Staff email already exists for this business.");
        }

        s.setFirstName(req.getFirstName());
        s.setLastName(req.getLastName());
        s.setEmail(req.getEmail());
        s.setPhone(req.getPhone());
        s.setColorHex(req.getColorHex());
        if (req.getIsActive() != null) {
            s.setActive(req.getIsActive());
        }

        return toResponse(staffRepo.save(s));
    }

    @Transactional
    public void deactivate(Long businessId, Long staffId) {
        Staff s = staffRepo.findByIdAndBusiness_Id(staffId, businessId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found for this business."));
        s.setActive(false);
        staffRepo.save(s);
    }

    private StaffResponse toResponse(Staff s) {
        Long userId = (s.getUser() != null) ? s.getUser().getId() : null;

        return new StaffResponse(
                s.getId(),
                s.getBusiness().getId(),
                userId,
                s.getFirstName(),
                s.getLastName(),
                s.getEmail(),
                s.getPhone(),
                s.getColorHex(),
                s.getActive()
        );
    }
}