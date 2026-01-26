package com.appointment.service;

import com.appointment.dto.industry.IndustryResponse;
import com.appointment.model.Industry;
import com.appointment.repository.IndustryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IndustryAppService {

    private final IndustryRepository industryRepo;

    public IndustryAppService(IndustryRepository industryRepo) {
        this.industryRepo = industryRepo;
    }

    @Transactional(readOnly = true)
    public List<IndustryResponse> listActive() {
        return industryRepo.findByIsActiveTrueOrderByIndustryNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private IndustryResponse toResponse(Industry i) {
        return new IndustryResponse(i.getId(), i.getIndustryCode(), i.getIndustryName());
    }
}