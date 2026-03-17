package com.appointment.controller;

import com.appointment.dto.auth.AuthResponseDto;
import com.appointment.dto.auth.LoginRequestDto;
import com.appointment.dto.auth.SignupRequestDto;
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

        AuthResponseDto response = new AuthResponseDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                "Login successful"
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody SignupRequestDto request) {
        User user = authService.signup(
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getRole()
        );

        AuthResponseDto response = new AuthResponseDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                "Signup successful"
        );

        return ResponseEntity.ok(response);
    }
}