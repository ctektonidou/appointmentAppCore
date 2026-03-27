package com.appointment.controller;

import com.appointment.dto.blockeddate.BlockedDateResponse;
import com.appointment.dto.blockeddate.CreateBlockedDateRequest;
import com.appointment.service.BlockedDateAppService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blocked-dates/staff/user/{userId}")
@CrossOrigin(origins = "http://localhost:5173")
public class BlockedDateQueryController {

    private final BlockedDateAppService appService;

    public BlockedDateQueryController(BlockedDateAppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public List<BlockedDateResponse> listForStaffUser(@PathVariable Long userId) {
        return appService.listForStaffUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlockedDateResponse createForStaffUser(
            @PathVariable Long userId,
            @Valid @RequestBody CreateBlockedDateRequest req
    ) {
        return appService.createForStaffUser(userId, req);
    }

    @DeleteMapping("/{blockedDateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteForStaffUser(
            @PathVariable Long userId,
            @PathVariable Long blockedDateId
    ) {
        appService.deleteForStaffUser(userId, blockedDateId);
    }
}