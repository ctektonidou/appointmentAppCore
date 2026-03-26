package com.appointment.dto.appointment;

import com.appointment.model.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class CustomerDashboardAppointmentDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String clientName;
    private AppointmentStatus status;
    private Long businessId;
    private Long serviceId;
    private Long staffId;

    public CustomerDashboardAppointmentDto(
            Long id,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String clientName,
            AppointmentStatus status,
            Long businessId,
            Long serviceId,
            Long staffId
    ) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.clientName = clientName;
        this.status = status;
        this.businessId = businessId;
        this.serviceId = serviceId;
        this.staffId = staffId;
    }

    public Long getId() { return id; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getClientName() { return clientName; }
    public AppointmentStatus getStatus() { return status; }
    public Long getBusinessId() { return businessId; }
    public Long getServiceId() { return serviceId; }
    public Long getStaffId() { return staffId; }
}