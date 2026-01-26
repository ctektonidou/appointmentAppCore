package com.appointment.service;

import com.appointment.dto.blockeddate.BlockedDateResponse;
import com.appointment.dto.blockeddate.CreateBlockedDateRequest;
import com.appointment.model.BlockedDate;
import com.appointment.model.Business;
import com.appointment.model.Staff;
import com.appointment.repository.BlockedDateRepository;
import com.appointment.repository.BusinessRepository;
import com.appointment.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlockedDateAppService {

    private final BlockedDateRepository blockedRepo;
    private final BusinessRepository businessRepo;
    private final StaffRepository staffRepo;

    public BlockedDateAppService(BlockedDateRepository blockedRepo,
                                 BusinessRepository businessRepo,
                                 StaffRepository staffRepo) {
        this.blockedRepo = blockedRepo;
        this.businessRepo = businessRepo;
        this.staffRepo = staffRepo;
    }

    @Transactional
    public BlockedDateResponse create(Long businessId, CreateBlockedDateRequest req) {
        Business business = businessRepo.findById(businessId)
                .orElseThrow(() -> new IllegalArgumentException("Business not found: " + businessId));

        BlockedDate bd = new BlockedDate();
        bd.setBusiness(business);
        bd.setDate(req.getDate());
        bd.setReason(req.getReason());

        if (req.getStaffId() != null) {
            Staff staff = staffRepo.findById(req.getStaffId())
                    .orElseThrow(() -> new IllegalArgumentException("Staff not found: " + req.getStaffId()));
            bd.setStaff(staff);
        }

        return toResponse(blockedRepo.save(bd));
    }

    @Transactional(readOnly = true)
    public List<BlockedDateResponse> listForBusiness(Long businessId) {
        return blockedRepo.findByBusiness_Id(businessId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void delete(Long blockedDateId) {
        blockedRepo.deleteById(blockedDateId);
    }

    private BlockedDateResponse toResponse(BlockedDate bd) {
        Long staffId = bd.getStaff() != null ? bd.getStaff().getId() : null;
        return new BlockedDateResponse(
                bd.getId(),
                bd.getBusiness().getId(),
                staffId,
                bd.getDate(),
                bd.getReason()
        );
    }
}