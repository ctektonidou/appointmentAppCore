package com.appointment.controller;

import com.appointment.dto.services.CreateServiceRequest;
import com.appointment.dto.services.ServiceResponse;
import com.appointment.dto.services.UpdateServiceRequest;
import com.appointment.service.BusinessServiceAppService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses/{businessId}/services")
public class BusinessServiceController {

    private final BusinessServiceAppService appService;

    public BusinessServiceController(BusinessServiceAppService appService) {
        this.appService = appService;
    }

    // GET /api/businesses/{businessId}/services?activeOnly=true
    @GetMapping
    public List<ServiceResponse> list(
            @PathVariable Long businessId,
            @RequestParam(defaultValue = "false") boolean activeOnly
    ) {
        return appService.listServices(businessId, activeOnly);
    }

    // GET /api/businesses/{businessId}/services/{serviceId}
    @GetMapping("/{serviceId}")
    public ServiceResponse getOne(
            @PathVariable Long businessId,
            @PathVariable Long serviceId
    ) {
        return appService.getService(businessId, serviceId);
    }

    // POST /api/businesses/{businessId}/services
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse create(
            @PathVariable Long businessId,
            @Valid @RequestBody CreateServiceRequest req
    ) {
        return appService.createService(businessId, req);
    }

    // PUT /api/businesses/{businessId}/services/{serviceId}
    @PutMapping("/{serviceId}")
    public ServiceResponse update(
            @PathVariable Long businessId,
            @PathVariable Long serviceId,
            @Valid @RequestBody UpdateServiceRequest req
    ) {
        return appService.updateService(businessId, serviceId, req);
    }

    // PATCH /api/businesses/{businessId}/services/{serviceId}/activate
    @PatchMapping("/{serviceId}/activate")
    public ServiceResponse activate(
            @PathVariable Long businessId,
            @PathVariable Long serviceId
    ) {
        return appService.setActive(businessId, serviceId, true);
    }

    // PATCH /api/businesses/{businessId}/services/{serviceId}/deactivate
    @PatchMapping("/{serviceId}/deactivate")
    public ServiceResponse deactivate(
            @PathVariable Long businessId,
            @PathVariable Long serviceId
    ) {
        return appService.setActive(businessId, serviceId, false);
    }

    @DeleteMapping("/{serviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long businessId,
            @PathVariable Long serviceId
    ) {
        appService.deleteService(businessId, serviceId);
    }
}
