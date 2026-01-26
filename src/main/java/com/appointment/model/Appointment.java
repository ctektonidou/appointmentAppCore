package com.appointment.model;

import com.appointment.model.enums.AppointmentSource;
import com.appointment.model.enums.AppointmentStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Multi-tenant
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private BusinessService service;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(name = "customer_user_id")
    private Long customerUserId;

    // Embedded client info
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "client_phone")
    private String clientPhone;

    @Column(name = "client_notes", columnDefinition = "TEXT")
    private String clientNotes;

    // Time
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentSource source = AppointmentSource.INTERNAL;

    // Management link
    @Column(name = "manage_token", unique = true)
    private String manageToken;

    @Column(name = "manage_token_expires_at")
    private LocalDateTime manageTokenExpiresAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Appointment() {

    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
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

    public BusinessService getService() {
        return service;
    }

    public void setService(BusinessService service) {
        this.service = service;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientNotes() {
        return clientNotes;
    }

    public void setClientNotes(String clientNotes) {
        this.clientNotes = clientNotes;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getManageTokenExpiresAt() {
        return manageTokenExpiresAt;
    }

    public void setManageTokenExpiresAt(LocalDateTime manageTokenExpiresAt) {
        this.manageTokenExpiresAt = manageTokenExpiresAt;
    }

    public String getManageToken() {
        return manageToken;
    }

    public void setManageToken(String manageToken) {
        this.manageToken = manageToken;
    }

    public AppointmentSource getSource() {
        return source;
    }

    public void setSource(AppointmentSource source) {
        this.source = source;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getCustomerUserId() { return customerUserId; }

    public void setCustomerUserId(Long customerUserId) { this.customerUserId = customerUserId; }

    public Appointment(Long id, BusinessService service, Business business, Staff staff, Long customerUserId, String clientName, String clientEmail, String clientPhone, String clientNotes, LocalDateTime startTime, LocalDateTime endTime, AppointmentStatus status, AppointmentSource source, String manageToken, LocalDateTime manageTokenExpiresAt, Instant updatedAt, Instant createdAt) {
        this.id = id;
        this.service = service;
        this.business = business;
        this.staff = staff;
        this.customerUserId = customerUserId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.clientNotes = clientNotes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.source = source;
        this.manageToken = manageToken;
        this.manageTokenExpiresAt = manageTokenExpiresAt;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }
}
