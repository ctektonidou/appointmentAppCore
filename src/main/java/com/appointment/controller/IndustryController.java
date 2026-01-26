package com.appointment.controller;

import com.appointment.dto.industry.IndustryResponse;
import com.appointment.service.IndustryAppService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/industries")
public class IndustryController {

    private final IndustryAppService appService;

    public IndustryController(IndustryAppService appService) {
        this.appService = appService;
    }

    // GET /api/industries
    @GetMapping
    public List<IndustryResponse> listActive() {
        return appService.listActive();
    }
}