package com.appointment.controller;

import com.appointment.dto.auth.*;
import com.appointment.model.Business;
import com.appointment.model.Staff;
import com.appointment.model.User;
import com.appointment.repository.BusinessRepository;
import com.appointment.repository.StaffRepository;
import com.appointment.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;
    private final BusinessRepository businessRepository;
    private final StaffRepository staffRepository;

    public AuthController(
            AuthService authService,
            BusinessRepository businessRepository,
            StaffRepository staffRepository
    ) {
        this.authService = authService;
        this.businessRepository = businessRepository;
        this.staffRepository = staffRepository;
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
        Long businessId = null;
        Long staffId = null;

        if ("owner".equalsIgnoreCase(user.getRole())) {
            businessId = businessRepository.findFirstByOwnerUserId(user.getId())
                    .map(Business::getId)
                    .orElse(null);
        } else if ("staff".equalsIgnoreCase(user.getRole())) {
            Staff staff = staffRepository.findByUser_Id(user.getId())
                    .orElse(null);

            if (staff != null) {
                staffId = staff.getId();
                businessId = staff.getBusiness() != null ? staff.getBusiness().getId() : null;
            }
        }

        return new AuthResponseDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                message,
                businessId,
                staffId
        );
    }
}