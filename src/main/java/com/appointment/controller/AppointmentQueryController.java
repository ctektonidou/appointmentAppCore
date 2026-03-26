package com.appointment.controller;

import com.appointment.dto.appointment.AppointmentListItemResponse;
import com.appointment.model.enums.AppointmentStatus;
import com.appointment.service.AppointmentAppService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentQueryController {

    private final AppointmentAppService appService;

    public AppointmentQueryController(AppointmentAppService appService) {
        this.appService = appService;
    }

    @GetMapping("/owner/user/{userId}")
    public List<AppointmentListItemResponse> listForOwnerUser(
            @PathVariable Long userId,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false) Long staffId,
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) String search
    ) {
        return appService.listForOwnerUser(userId, from, to, status, staffId, serviceId, search);
    }

    @GetMapping("/staff/user/{userId}")
    public List<AppointmentListItemResponse> listForStaffUser(
            @PathVariable Long userId,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) String search
    ) {
        return appService.listForStaffUser(userId, from, to, status, serviceId, search);
    }

    @GetMapping("/customer/user/{userId}")
    public List<AppointmentListItemResponse> listForCustomerUser(
            @PathVariable Long userId,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false) Long businessId,
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) String search
    ) {
        return appService.listForCustomerUser(userId, from, to, status, businessId, serviceId, search);
    }
}