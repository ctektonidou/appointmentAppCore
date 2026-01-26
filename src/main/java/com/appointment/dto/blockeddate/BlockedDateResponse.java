package com.appointment.dto.blockeddate;

import java.time.LocalDate;

public class BlockedDateResponse {

    private Long id;
    private Long businessId;
    private Long staffId; // null = business-wide
    private LocalDate date;
    private String reason;

    public BlockedDateResponse(Long id,
                               Long businessId,
                               Long staffId,
                               LocalDate date,
                               String reason) {
        this.id = id;
        this.businessId = businessId;
        this.staffId = staffId;
        this.date = date;
        this.reason = reason;
    }

    public Long getId() { return id; }
    public Long getBusinessId() { return businessId; }
    public Long getStaffId() { return staffId; }
    public LocalDate getDate() { return date; }
    public String getReason() { return reason; }
}
