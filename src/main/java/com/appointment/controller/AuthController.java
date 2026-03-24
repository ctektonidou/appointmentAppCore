package com.appointment.controller;

import com.appointment.dto.auth.*;
import com.appointment.model.User;
import com.appointment.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto request) {
        User user = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(toAuthResponse(user, "Login successful"));
    }

    @PostMapping("/signup/customer")
    public ResponseEntity<AuthResponseDto> signupCustomer(@RequestBody CustomerSignupRequestDto request) {
        User user = authService.signupCustomer(
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName()
        );
        return ResponseEntity.ok(toAuthResponse(user, "Signup successful"));
    }

    @PostMapping("/signup/staff")
    public ResponseEntity<AuthResponseDto> signupStaff(@RequestBody StaffSignupRequestDto request) {
        User user = authService.signupStaff(
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getBusinessCode(),
                request.getPhone(),
                request.getColorHex()
        );
        return ResponseEntity.ok(toAuthResponse(user, "Signup successful"));
    }

    @PostMapping("/signup/business")
    public ResponseEntity<AuthResponseDto> signupBusiness(@RequestBody BusinessSignupRequestDto request) {
        User user = authService.signupBusiness(
                request.getOwnerEmail(),
                request.getPassword(),
                request.getOwnerFirstName(),
                request.getOwnerLastName(),
                request.getBusinessName(),
                request.getIndustryId(),
                request.getPhone(),
                request.getBusinessEmail(),
                request.getTimezone(),
                request.getAddress(),
                request.getLogoUrl()
        );
        return ResponseEntity.ok(toAuthResponse(user, "Signup successful"));
    }

    private AuthResponseDto toAuthResponse(User user, String message) {
        return new AuthResponseDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                message
        );
    }
}