package com.appointment.dto.appointment;

public class DashboardStatsResponseDto {

    private long todayCount;
    private long weekCount;
    private double cancelRate;
    private long noShowsCount;

    public DashboardStatsResponseDto(long todayCount, long weekCount, double cancelRate, long noShowsCount) {
        this.todayCount = todayCount;
        this.weekCount = weekCount;
        this.cancelRate = cancelRate;
        this.noShowsCount = noShowsCount;
    }

    public long getTodayCount() {
        return todayCount;
    }

    public long getWeekCount() {
        return weekCount;
    }

    public double getCancelRate() {
        return cancelRate;
    }

    public long getNoShowsCount() {
        return noShowsCount;
    }
}