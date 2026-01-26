package com.appointment.dto.businessHours;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class BusinessHoursDto {

    @NotNull
    private Integer dayOfWeek;    // 1..7

    @NotNull
    private Boolean isOpen;

    private LocalTime openTime;
    private LocalTime closeTime;

    public Integer getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Boolean getIsOpen() { return isOpen; }
    public void setIsOpen(Boolean open) { isOpen = open; }

    public LocalTime getOpenTime() { return openTime; }
    public void setOpenTime(LocalTime openTime) { this.openTime = openTime; }

    public LocalTime getCloseTime() { return closeTime; }
    public void setCloseTime(LocalTime closeTime) { this.closeTime = closeTime; }
}