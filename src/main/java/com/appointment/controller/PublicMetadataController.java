package com.appointment.controller;

import com.appointment.dto.industry.IndustryResponse;
import com.appointment.service.PublicMetadataAppService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicMetadataController {

    private final PublicMetadataAppService appService;

    public PublicMetadataController(PublicMetadataAppService appService) {
        this.appService = appService;
    }

    @GetMapping("/industries")
    public List<IndustryResponse> listIndustries() {
        return appService.listIndustries();
    }

    @GetMapping("/locations")
    public List<String> listLocations() {
        return appService.listLocations();
    }
}