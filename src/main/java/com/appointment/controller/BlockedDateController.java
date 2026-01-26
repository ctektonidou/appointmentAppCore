package com.appointment.controller;

import com.appointment.dto.blockeddate.BlockedDateResponse;
import com.appointment.dto.blockeddate.CreateBlockedDateRequest;
import com.appointment.service.BlockedDateAppService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses/{businessId}/blocked-dates")
public class BlockedDateController {

    private final BlockedDateAppService appService;

    public BlockedDateController(BlockedDateAppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public List<BlockedDateResponse> list(@PathVariable Long businessId) {
        return appService.listForBusiness(businessId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlockedDateResponse create(@PathVariable Long businessId,
                                      @Valid @RequestBody CreateBlockedDateRequest req) {
        return appService.create(businessId, req);
    }

    @DeleteMapping("/{blockedDateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long businessId,
                       @PathVariable Long blockedDateId) {
        appService.delete(blockedDateId);
    }
}