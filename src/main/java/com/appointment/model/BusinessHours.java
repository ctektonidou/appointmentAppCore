package com.appointment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(name = "business_hours")
public class BusinessHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @Column(name = "day_of_week")
    @NotNull
    private Integer dayOfWeek; // 0 = Monday ... 6 = Sunday

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen = true;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public BusinessHours() {
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    // getters / setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Business getBusiness() { return business; }

    public void setBusiness(Business business) { this.business = business; }

    public Integer getDayOfWeek() { return dayOfWeek; }

    public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Boolean getOpen() { return isOpen; }

    public void setOpen(Boolean open) { isOpen = open; }

    public LocalTime getOpenTime() { return openTime; }

    public void setOpenTime(LocalTime openTime) { this.openTime = openTime; }

    public LocalTime getCloseTime() { return closeTime; }

    public void setCloseTime(LocalTime closeTime) { this.closeTime = closeTime; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}