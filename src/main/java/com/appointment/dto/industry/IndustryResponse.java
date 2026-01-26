package com.appointment.dto.industry;

public class IndustryResponse {
    private Long id;
    private String industryCode;
    private String industryName;

    public IndustryResponse(Long id, String industryCode, String industryName) {
        this.id = id;
        this.industryCode = industryCode;
        this.industryName = industryName;
    }

    public Long getId() { return id; }
    public String getIndustryCode() { return industryCode; }
    public String getIndustryName() { return industryName; }
}