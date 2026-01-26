package com.appointment.dto.staff;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class StaffAvailabilityDto {

    @NotNull
    private Integer dayOfWeek;

    @NotNull
    private Boolean isAvailable;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    public Integer getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean available) { isAvailable = available; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}