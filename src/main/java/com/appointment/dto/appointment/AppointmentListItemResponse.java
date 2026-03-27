package com.appointment.dto.appointment;

import com.appointment.model.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentListItemResponse {
    private Long id;
    private Long businessId;
    private Long serviceId;
    private Long staffId;
    private Long customerUserId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String customerName;
    private String businessName;
    private String serviceName;
    private String staffName;

    private String clientEmail;
    private String clientPhone;
    private String clientNotes;

    private AppointmentStatus status;

    public AppointmentListItemResponse(
            Long id,
            Long businessId,
            Long serviceId,
            Long staffId,
            Long customerUserId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String customerName,
            String businessName,
            String serviceName,
            String staffName,
            String clientEmail,
            String clientPhone,
            String clientNotes,
            AppointmentStatus status
    ) {
        this.id = id;
        this.businessId = businessId;
        this.serviceId = serviceId;
        this.staffId = staffId;
        this.customerUserId = customerUserId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerName = customerName;
        this.businessName = businessName;
        this.serviceName = serviceName;
        this.staffName = staffName;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.clientNotes = clientNotes;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getBusinessId() { return businessId; }
    public Long getServiceId() { return serviceId; }
    public Long getStaffId() { return staffId; }
    public Long getCustomerUserId() { return customerUserId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getCustomerName() { return customerName; }
    public String getBusinessName() { return businessName; }
    public String getServiceName() { return serviceName; }
    public String getStaffName() { return staffName; }
    public String getClientEmail() { return clientEmail; }
    public String getClientPhone() { return clientPhone; }
    public String getClientNotes() { return clientNotes; }
    public AppointmentStatus getStatus() { return status; }
}