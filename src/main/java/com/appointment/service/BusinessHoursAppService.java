package com.appointment.service;

import com.appointment.dto.businessHours.BusinessHoursDto;
import com.appointment.model.Business;
import com.appointment.model.BusinessHours;
import com.appointment.repository.BusinessHoursRepository;
import com.appointment.repository.BusinessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessHoursAppService {

    private final BusinessHoursRepository hoursRepo;
    private final BusinessRepository businessRepo;

    public BusinessHoursAppService(BusinessHoursRepository hoursRepo,
                                   BusinessRepository businessRepo) {
        this.hoursRepo = hoursRepo;
        this.businessRepo = businessRepo;
    }

    @Transactional(readOnly = true)
    public List<BusinessHoursDto> getForBusiness(Long businessId) {
        return hoursRepo.findByBusiness_IdOrderByDayOfWeekAsc(businessId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public List<BusinessHoursDto> saveForBusiness(Long businessId, List<BusinessHoursDto> dtos) {
        Business business = businessRepo.findById(businessId)
                .orElseThrow(() -> new IllegalArgumentException("Business not found: " + businessId));

        for (BusinessHoursDto dto : dtos) {
            if (dto.getDayOfWeek() == null || dto.getDayOfWeek() < 0 || dto.getDayOfWeek() > 6) {
                throw new IllegalArgumentException("dayOfWeek must be between 0 and 6.");
            }

            if (Boolean.TRUE.equals(dto.getIsOpen())) {
                if (dto.getOpenTime() == null || dto.getCloseTime() == null) {
                    throw new IllegalArgumentException("Open days must have openTime and closeTime.");
                }

                if (!dto.getOpenTime().isBefore(dto.getCloseTime())) {
                    throw new IllegalArgumentException("openTime must be before closeTime.");
                }
            }
        }

        List<BusinessHours> existing = hoursRepo.findByBusiness_Id(businessId);
        hoursRepo.deleteAll(existing);
        hoursRepo.flush(); // IMPORTANT

        List<BusinessHours> entities = dtos.stream().map(dto -> {
            BusinessHours bh = new BusinessHours();
            bh.setBusiness(business);
            bh.setDayOfWeek(dto.getDayOfWeek());
            bh.setOpen(dto.getIsOpen());
            bh.setOpenTime(dto.getOpenTime());
            bh.setCloseTime(dto.getCloseTime());
            return bh;
        }).toList();

        return hoursRepo.saveAll(entities)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private BusinessHoursDto toDto(BusinessHours bh) {
        BusinessHoursDto dto = new BusinessHoursDto();
        dto.setDayOfWeek(bh.getDayOfWeek());
        dto.setIsOpen(bh.getOpen());
        dto.setOpenTime(bh.getOpenTime());
        dto.setCloseTime(bh.getCloseTime());
        return dto;
    }
}