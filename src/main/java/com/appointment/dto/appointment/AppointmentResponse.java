package com.appointment.dto.appointment;

import com.appointment.model.enums.AppointmentSource;
import com.appointment.model.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentResponse {
    private Long id;
    private Long businessId;
    private Long serviceId;
    private Long staffId;
    private Long customerUserId;

    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private String clientNotes;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private AppointmentStatus status;
    private AppointmentSource source;

    public AppointmentResponse(Long id, Long businessId, Long serviceId, Long staffId, Long customerUserId,
                               String clientName, String clientEmail, String clientPhone, String clientNotes,
                               LocalDateTime startTime, LocalDateTime endTime,
                               AppointmentStatus status, AppointmentSource source) {
        this.id = id;
        this.businessId = businessId;
        this.serviceId = serviceId;
        this.staffId = staffId;
        this.customerUserId = customerUserId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.clientNotes = clientNotes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.source = source;
    }

    public Long getId() { return id; }
    public Long getBusinessId() { return businessId; }
    public Long getServiceId() { return serviceId; }
    public Long getStaffId() { return staffId; }
    public Long getCustomerUserId() { return customerUserId; }
    public String getClientName() { return clientName; }
    public String getClientEmail() { return clientEmail; }
    public String getClientPhone() { return clientPhone; }
    public String getClientNotes() { return clientNotes; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public AppointmentStatus getStatus() { return status; }
    public AppointmentSource getSource() { return source; }
}