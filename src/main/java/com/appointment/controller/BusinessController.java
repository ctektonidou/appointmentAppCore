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

    @GetMapping("/public-search")
    public List<BusinessResponse> publicSearch(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long industryId
    ) {
        return appService.publicSearch(name, industryId);
    }

    @GetMapping
    public List<BusinessResponse> list(@RequestParam Long ownerUserId) {
        return appService.listForOwner(ownerUserId);
    }

    @GetMapping("/{businessId}")
    public BusinessResponse getOne(
            @PathVariable Long businessId,
            @RequestParam Long ownerUserId
    ) {
        return appService.getOne(ownerUserId, businessId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessResponse create(@Valid @RequestBody CreateBusinessRequest req) {
        return appService.create(req);
    }

    @PutMapping("/{businessId}")
    public BusinessResponse update(
            @PathVariable Long businessId,
            @Valid @RequestBody UpdateBusinessRequest req
    ) {
        return appService.update(businessId, req);
    }

    @GetMapping("/owner/user/{userId}/primary")
    public BusinessResponse getPrimaryBusinessByOwnerUserId(@PathVariable Long userId) {
        return appService.getPrimaryBusinessByOwnerUserId(userId);
    }
}