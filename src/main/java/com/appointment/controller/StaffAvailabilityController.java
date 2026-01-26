package com.appointment.controller;

import com.appointment.dto.staff.StaffAvailabilityDto;
import com.appointment.service.StaffAvailabilityAppService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses/{businessId}/staff/{staffId}/availability")
public class StaffAvailabilityController {

    private final StaffAvailabilityAppService appService;

    public StaffAvailabilityController(StaffAvailabilityAppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public List<StaffAvailabilityDto> get(@PathVariable Long businessId,
                                          @PathVariable Long staffId) {
        // businessId not used yet; staffId is enough because of foreign key
        return appService.getForStaff(staffId);
    }

    @PutMapping
    public List<StaffAvailabilityDto> save(@PathVariable Long businessId,
                                           @PathVariable Long staffId,
                                           @Valid @RequestBody List<StaffAvailabilityDto> body) {
        return appService.saveForStaff(staffId, body);
    }
}