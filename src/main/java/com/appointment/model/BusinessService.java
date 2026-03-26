package com.appointment.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "services")
public class BusinessService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    private String name;
    private String description;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "price_amount")
    private BigDecimal priceAmount;
    private String currency;

    @Column(name = "color_hex")
    private String colorHex;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public BusinessService() {

    }

    // getters/setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public BigDecimal getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(BigDecimal priceAmount) {
        this.priceAmount = priceAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    public BusinessService(Long id, Business business, String name, String description, Integer durationMinutes, BigDecimal priceAmount, String currency, String colorHex, Boolean isActive, Instant createdAt) {
        this.id = id;
        this.business = business;
        this.name = name;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.priceAmount = priceAmount;
        this.currency = currency;
        this.colorHex = colorHex;
        this.active = isActive;
        this.createdAt = createdAt;
    }
}

