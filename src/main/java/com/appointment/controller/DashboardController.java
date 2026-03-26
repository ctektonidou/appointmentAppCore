package com.appointment.controller;

import com.appointment.dto.appointment.DashboardStatsResponseDto;
import com.appointment.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/customer/{userId}")
    public DashboardStatsResponseDto getCustomerStats(@PathVariable Long userId) {
        return dashboardService.getCustomerStats(userId);
    }

    @GetMapping("/staff/user/{userId}")
    public DashboardStatsResponseDto getStaffStats(@PathVariable Long userId) {
        return dashboardService.getStaffStatsByUserId(userId);
    }

    @GetMapping("/owner/user/{userId}")
    public DashboardStatsResponseDto getOwnerStats(@PathVariable Long userId) {
        return dashboardService.getOwnerStatsByUserId(userId);
    }
}