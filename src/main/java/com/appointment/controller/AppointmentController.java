package com.appointment.controller;

import com.appointment.dto.appointment.AppointmentListItemResponse;
import com.appointment.dto.appointment.AppointmentResponse;
import com.appointment.dto.appointment.CreateAppointmentRequest;
import com.appointment.dto.appointment.UpdateAppointmentRequest;
import com.appointment.model.enums.AppointmentStatus;
import com.appointment.service.AppointmentAppService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/businesses/{businessId}/appointments")
public class AppointmentController {

    private final AppointmentAppService appService;

    public AppointmentController(AppointmentAppService appService) {
        this.appService = appService;
    }

    // GET /api/businesses/{businessId}/appointments?from=2026-01-01T00:00:00&to=2026-02-01T00:00:00
    @GetMapping
    public List<AppointmentResponse> list(
            @PathVariable Long businessId,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to
    ) {
        return appService.list(businessId, from, to);
    }

    // GET /api/businesses/{businessId}/appointments/{appointmentId}
    @GetMapping("/{appointmentId}")
    public AppointmentResponse getOne(@PathVariable Long businessId, @PathVariable Long appointmentId) {
        return appService.getOne(businessId, appointmentId);
    }

    // POST /api/businesses/{businessId}/appointments
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponse create(@PathVariable Long businessId,
                                      @Valid @RequestBody CreateAppointmentRequest req) {
        return appService.create(businessId, req);
    }

    // PATCH /api/businesses/{businessId}/appointments/{appointmentId}/status?value=CANCELLED
    @PatchMapping("/{appointmentId}/status")
    public AppointmentResponse setStatus(@PathVariable Long businessId,
                                         @PathVariable Long appointmentId,
                                         @RequestParam("value") AppointmentStatus status) {
        return appService.setStatus(businessId, appointmentId, status);
    }

    @PutMapping("/{appointmentId}")
    public AppointmentResponse update(
            @PathVariable Long businessId,
            @PathVariable Long appointmentId,
            @Valid @RequestBody UpdateAppointmentRequest req
    ) {
        return appService.update(businessId, appointmentId, req);
    }
}