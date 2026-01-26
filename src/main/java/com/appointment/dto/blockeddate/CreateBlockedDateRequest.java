package com.appointment.dto.blockeddate;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateBlockedDateRequest {

    // optional; null => whole business is blocked
    private Long staffId;

    @NotNull
    private LocalDate date;

    private String reason;

    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
