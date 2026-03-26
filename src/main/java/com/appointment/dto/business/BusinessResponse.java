package com.appointment.dto.business;

import java.time.Instant;

public class BusinessResponse {

    private Long id;
    private Long ownerUserId;
    private String name;
    private Long industryId;
    private String phone;
    private String email;
    private String timezone;
    private String address;
    private String logoUrl;
    private Instant createdAt;
    private String location;

    public BusinessResponse(
            Long id,
            Long ownerUserId,
            String name,
            Long industryId,
            String phone,
            String email,
            String timezone,
            String address,
            String logoUrl,
            Instant createdAt,
            String location
    ) {
        this.id = id;
        this.ownerUserId = ownerUserId;
        this.name = name;
        this.industryId = industryId;
        this.phone = phone;
        this.email = email;
        this.timezone = timezone;
        this.address = address;
        this.logoUrl = logoUrl;
        this.createdAt = createdAt;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public String getName() {
        return name;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getAddress() {
        return address;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getLocation() {return location;}
}