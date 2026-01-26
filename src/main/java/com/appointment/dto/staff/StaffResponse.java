package com.appointment.dto.staff;

public class StaffResponse {
    private Long id;
    private Long businessId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String colorHex;
    private Boolean isActive;

    public StaffResponse(Long id, Long businessId, Long userId, String firstName, String lastName,
                         String email, String phone, String colorHex, Boolean isActive) {
        this.id = id;
        this.businessId = businessId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.colorHex = colorHex;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public Long getBusinessId() { return businessId; }
    public Long getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getColorHex() { return colorHex; }
    public Boolean getIsActive() { return isActive; }
}