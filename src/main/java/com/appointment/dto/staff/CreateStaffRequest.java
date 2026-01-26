package com.appointment.dto.staff;

import jakarta.validation.constraints.NotBlank;

public class CreateStaffRequest {

    @NotBlank
    private String firstName;

    private String lastName;
    private String email;
    private String phone;
    private String colorHex;
    private Boolean isActive;

    // optional: link staff to a user account
    private Long userId;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}