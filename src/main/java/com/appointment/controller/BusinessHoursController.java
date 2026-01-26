package com.appointment.controller;

import com.appointment.dto.businessHours.BusinessHoursDto;
import com.appointment.service.BusinessHoursAppService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses/{businessId}/hours")
public class BusinessHoursController {

    private final BusinessHoursAppService appService;

    public BusinessHoursController(BusinessHoursAppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public List<BusinessHoursDto> get(@PathVariable Long businessId) {
        return appService.getForBusiness(businessId);
    }

    @PutMapping
    public List<BusinessHoursDto> save(@PathVariable Long businessId,
                                       @Valid @RequestBody List<BusinessHoursDto> body) {
        return appService.saveForBusiness(businessId, body);
    }
}