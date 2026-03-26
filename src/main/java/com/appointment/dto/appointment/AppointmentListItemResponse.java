package com.appointment.dto.appointment;

import com.appointment.model.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentListItemResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String customerName;
    private String businessName;
    private String serviceName;
    private String staffName;
    private AppointmentStatus status;

    public AppointmentListItemResponse(
            Long id,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String customerName,
            String businessName,
            String serviceName,
            String staffName,
            AppointmentStatus status
    ) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerName = customerName;
        this.businessName = businessName;
        this.serviceName = serviceName;
        this.staffName = staffName;
        this.status = status;
    }

    public Long getId() { return id; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getCustomerName() { return customerName; }
    public String getBusinessName() { return businessName; }
    public String getServiceName() { return serviceName; }
    public String getStaffName() { return staffName; }
    public AppointmentStatus getStatus() { return status; }
}