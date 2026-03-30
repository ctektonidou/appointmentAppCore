package com.appointment.dto.auth;

public class AuthResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String message;
    private Long businessId;
    private Long staffId;

    public AuthResponseDto(Long id, String email, String firstName, String lastName,
                           String role, String message, Long businessId, Long staffId) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.message = message;
        this.businessId = businessId;
        this.staffId = staffId;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getRole() { return role; }
    public String getMessage() { return message; }
    public Long getBusinessId() { return businessId; }
    public Long getStaffId() { return staffId; }
}