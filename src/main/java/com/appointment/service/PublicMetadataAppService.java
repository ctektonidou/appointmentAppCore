package com.appointment.service;

import com.appointment.dto.industry.IndustryResponse;
import com.appointment.repository.BusinessRepository;
import com.appointment.repository.IndustryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublicMetadataAppService {

    private final IndustryRepository industryRepo;
    private final BusinessRepository businessRepo;

    public PublicMetadataAppService(IndustryRepository industryRepo,
                                    BusinessRepository businessRepo) {
        this.industryRepo = industryRepo;
        this.businessRepo = businessRepo;
    }

    @Transactional(readOnly = true)
    public List<IndustryResponse> listIndustries() {
        return industryRepo.findByIsActiveTrueOrderByIndustryNameAsc()
                .stream()
                .map(industry -> new IndustryResponse(
                        industry.getId(),
                        industry.getIndustryCode(),
                        industry.getIndustryName()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<String> listLocations() {
        return businessRepo.findDistinctLocations();
    }
}