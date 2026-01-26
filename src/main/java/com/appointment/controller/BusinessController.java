package com.appointment.controller;

import com.appointment.dto.business.BusinessResponse;
import com.appointment.dto.business.CreateBusinessRequest;
import com.appointment.dto.business.UpdateBusinessRequest;
import com.appointment.service.BusinessAppService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses")
public class BusinessController {

    private final BusinessAppService appService;

    public BusinessController(BusinessAppService appService) {
        this.appService = appService;
    }

    // GET /api/businesses?ownerUserId=123
    @GetMapping
    public List<BusinessResponse> list(@RequestParam Long ownerUserId) {
        return appService.listForOwner(ownerUserId);
    }

    // GET /api/businesses/{businessId}?ownerUserId=123
    @GetMapping("/{businessId}")
    public BusinessResponse getOne(
            @PathVariable Long businessId,
            @RequestParam Long ownerUserId
    ) {
        return appService.getOne(ownerUserId, businessId);
    }

    // POST /api/businesses
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessResponse create(@Valid @RequestBody CreateBusinessRequest req) {
        return appService.create(req);
    }

    // PUT /api/businesses/{businessId}
    @PutMapping("/{businessId}")
    public BusinessResponse update(
            @PathVariable Long businessId,
            @Valid @RequestBody UpdateBusinessRequest req
    ) {
        return appService.update(businessId, req);
    }
}