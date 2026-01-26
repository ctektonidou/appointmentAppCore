package com.appointment.service;

import com.appointment.dto.business.BusinessResponse;
import com.appointment.dto.business.CreateBusinessRequest;
import com.appointment.dto.business.UpdateBusinessRequest;
import com.appointment.model.Business;
import com.appointment.model.Industry;
import com.appointment.repository.BusinessRepository;
import com.appointment.repository.IndustryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessAppService {

    private final BusinessRepository businessRepo;
    private final IndustryRepository industryRepo;

    public BusinessAppService(BusinessRepository businessRepo,
                              IndustryRepository industryRepo) {
        this.businessRepo = businessRepo;
        this.industryRepo = industryRepo;
    }

    @Transactional(readOnly = true)
    public List<BusinessResponse> listForOwner(Long ownerUserId) {
        return businessRepo.findByOwnerUserIdOrderByNameAsc(ownerUserId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BusinessResponse getOne(Long ownerUserId, Long businessId) {
        Business b = businessRepo.findByIdAndOwnerUserId(businessId, ownerUserId)
                .orElseThrow(() -> new IllegalArgumentException("Business not found for this owner."));
        return toResponse(b);
    }

    @Transactional
    public BusinessResponse create(CreateBusinessRequest req) {

        if (businessRepo.existsByOwnerUserIdAndNameIgnoreCase(req.getOwnerUserId(), req.getName())) {
            throw new IllegalArgumentException("Business name already exists for this owner.");
        }

        Business b = new Business();
        b.setOwnerUserId(req.getOwnerUserId());
        b.setName(req.getName());
        b.setPhone(req.getPhone());
        b.setEmail(req.getEmail());
        b.setTimezone(req.getTimezone());
        b.setAddress(req.getAddress());
        b.setLogoUrl(req.getLogoUrl());

        if (req.getIndustryId() != null) {
            Industry industry = industryRepo.findById(req.getIndustryId())
                    .orElseThrow(() -> new IllegalArgumentException("Industry not found: " + req.getIndustryId()));
            b.setIndustry(industry);
        }

        Business saved = businessRepo.save(b);
        return toResponse(saved);
    }

    @Transactional
    public BusinessResponse update(Long businessId, UpdateBusinessRequest req) {

        Business b = businessRepo.findByIdAndOwnerUserId(businessId, req.getOwnerUserId())
                .orElseThrow(() -> new IllegalArgumentException("Business not found for this owner."));

        // optional uniqueness check if name changes
        if (!b.getName().equalsIgnoreCase(req.getName()) &&
                businessRepo.existsByOwnerUserIdAndNameIgnoreCase(req.getOwnerUserId(), req.getName())) {
            throw new IllegalArgumentException("Business name already exists for this owner.");
        }

        b.setName(req.getName());
        b.setPhone(req.getPhone());
        b.setEmail(req.getEmail());
        b.setTimezone(req.getTimezone());
        b.setAddress(req.getAddress());
        b.setLogoUrl(req.getLogoUrl());

        if (req.getIndustryId() != null) {
            Industry industry = industryRepo.findById(req.getIndustryId())
                    .orElseThrow(() -> new IllegalArgumentException("Industry not found: " + req.getIndustryId()));
            b.setIndustry(industry);
        } else {
            b.setIndustry(null);
        }

        return toResponse(businessRepo.save(b));
    }

    private BusinessResponse toResponse(Business b) {
        Long industryId = (b.getIndustry() != null) ? b.getIndustry().getId() : null;

        return new BusinessResponse(
                b.getId(),
                b.getOwnerUserId(),
                b.getName(),
                industryId,
                b.getPhone(),
                b.getEmail(),
                b.getTimezone(),
                b.getAddress(),
                b.getLogoUrl(),
                b.getCreatedAt()
        );
    }
}