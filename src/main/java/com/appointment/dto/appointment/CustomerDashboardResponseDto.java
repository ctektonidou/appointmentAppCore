package com.appointment.dto.appointment;

import java.util.List;

public class CustomerDashboardResponseDto {
    private long todayCount;
    private long weekCount;
    private double cancelRate;
    private List<CustomerDashboardAppointmentDto> todayAppointments;

    public CustomerDashboardResponseDto(
            long todayCount,
            long weekCount,
            double cancelRate,
            List<CustomerDashboardAppointmentDto> todayAppointments
    ) {
        this.todayCount = todayCount;
        this.weekCount = weekCount;
        this.cancelRate = cancelRate;
        this.todayAppointments = todayAppointments;
    }

    public long getTodayCount() { return todayCount; }
    public long getWeekCount() { return weekCount; }
    public double getCancelRate() { return cancelRate; }
    public List<CustomerDashboardAppointmentDto> getTodayAppointments() { return todayAppointments; }
}