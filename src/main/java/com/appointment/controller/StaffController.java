package com.appointment.controller;

import com.appointment.dto.staff.CreateStaffRequest;
import com.appointment.dto.staff.StaffResponse;
import com.appointment.dto.staff.UpdateStaffRequest;
import com.appointment.service.StaffAppService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses/{businessId}/staff")
public class StaffController {

    private final StaffAppService appService;

    public StaffController(StaffAppService appService) {
        this.appService = appService;
    }

    // GET /api/businesses/{businessId}/staff?activeOnly=true
    @GetMapping
    public List<StaffResponse> list(
            @PathVariable Long businessId,
            @RequestParam(defaultValue = "true") boolean activeOnly
    ) {
        return appService.list(businessId, activeOnly);
    }

    // GET /api/businesses/{businessId}/staff/{staffId}
    @GetMapping("/{staffId}")
    public StaffResponse getOne(@PathVariable Long businessId, @PathVariable Long staffId) {
        return appService.getOne(businessId, staffId);
    }

    // POST /api/businesses/{businessId}/staff
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StaffResponse create(@PathVariable Long businessId, @Valid @RequestBody CreateStaffRequest req) {
        return appService.create(businessId, req);
    }

    // PUT /api/businesses/{businessId}/staff/{staffId}
    @PutMapping("/{staffId}")
    public StaffResponse update(@PathVariable Long businessId, @PathVariable Long staffId,
                                @Valid @RequestBody UpdateStaffRequest req) {
        return appService.update(businessId, staffId, req);
    }

    // DELETE /api/businesses/{businessId}/staff/{staffId}  (soft delete)
    @DeleteMapping("/{staffId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long businessId, @PathVariable Long staffId) {
        appService.delete(businessId, staffId);
    }
}