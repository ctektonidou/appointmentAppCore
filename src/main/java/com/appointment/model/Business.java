package com.appointment.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "businesses")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // optional: you can map this as a relation to User later
    @Column(name = "owner_user_id", nullable = false)
    private Long ownerUserId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private Industry industry;

    private String phone;
    private String email;
    private String timezone;
    private String address;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public Business() {

    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    // getters/setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Industry  getIndustry() {
        return industry;
    }

    public void setIndustry(Industry  industry) {
        this.industry = industry;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Business(Long id, Long ownerUserId, Industry industry, String phone, String name, String email, String timezone, String address, Instant createdAt) {
        this.id = id;
        this.ownerUserId = ownerUserId;
        this.industry = industry;
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.timezone = timezone;
        this.address = address;
        this.createdAt = createdAt;
    }
}
