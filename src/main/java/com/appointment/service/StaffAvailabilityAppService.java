package com.appointment.service;

import com.appointment.dto.staff.StaffAvailabilityDto;
import com.appointment.model.Staff;
import com.appointment.model.StaffAvailability;
import com.appointment.repository.StaffAvailabilityRepository;
import com.appointment.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class StaffAvailabilityAppService {

    private final StaffAvailabilityRepository availabilityRepo;
    private final StaffRepository staffRepo;

    public StaffAvailabilityAppService(StaffAvailabilityRepository availabilityRepo,
                                       StaffRepository staffRepo) {
        this.availabilityRepo = availabilityRepo;
        this.staffRepo = staffRepo;
    }

    @Transactional(readOnly = true)
    public List<StaffAvailabilityDto> getForStaff(Long staffId) {
        return availabilityRepo.findByStaff_IdOrderByDayOfWeekAsc(staffId)
                .stream()
                .sorted(Comparator.comparing(StaffAvailability::getDayOfWeek)
                        .thenComparing(StaffAvailability::getStartTime))
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public List<StaffAvailabilityDto> saveForStaff(Long staffId, List<StaffAvailabilityDto> dtos) {
        Staff staff = staffRepo.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found: " + staffId));

        long distinctDays = dtos.stream()
                .map(StaffAvailabilityDto::getDayOfWeek)
                .distinct()
                .count();

        if (distinctDays != dtos.size()) {
            throw new IllegalArgumentException("Duplicate dayOfWeek values are not allowed");
        }

        availabilityRepo.deleteByStaff_Id(staffId);
        availabilityRepo.flush();

        List<StaffAvailability> entities = dtos.stream().map(dto -> {
            StaffAvailability sa = new StaffAvailability();
            sa.setStaff(staff);
            sa.setDayOfWeek(dto.getDayOfWeek());
            sa.setAvailable(dto.getIsAvailable());
            sa.setStartTime(dto.getStartTime());
            sa.setEndTime(dto.getEndTime());
            return sa;
        }).toList();

        List<StaffAvailability> saved = availabilityRepo.saveAll(entities);
        availabilityRepo.flush();

        return saved.stream()
                .sorted(Comparator.comparing(StaffAvailability::getDayOfWeek)
                        .thenComparing(StaffAvailability::getStartTime))
                .map(this::toDto)
                .toList();
    }

    private StaffAvailabilityDto toDto(StaffAvailability sa) {
        StaffAvailabilityDto dto = new StaffAvailabilityDto();
        dto.setDayOfWeek(sa.getDayOfWeek());
        dto.setIsAvailable(sa.getAvailable());
        dto.setStartTime(sa.getStartTime());
        dto.setEndTime(sa.getEndTime());
        return dto;
    }
}