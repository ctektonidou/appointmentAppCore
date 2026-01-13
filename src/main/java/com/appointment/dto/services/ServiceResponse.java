package com.appointment.dto.services;

import java.math.BigDecimal;

public class ServiceResponse {
    private Long id;
    private Long businessId;
    private String name;
    private String description;
    private Integer durationMinutes;
    private BigDecimal priceAmount;
    private String currency;
    private String colorHex;
    private Boolean isActive;

    public ServiceResponse(Long id, Long businessId, String name, String description,
                           Integer durationMinutes, BigDecimal priceAmount, String currency,
                           String colorHex, Boolean isActive) {
        this.id = id;
        this.businessId = businessId;
        this.name = name;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.priceAmount = priceAmount;
        this.currency = currency;
        this.colorHex = colorHex;
        this.isActive = isActive;
    }

    // getters only (DTO)
    public Long getId() { return id; }
    public Long getBusinessId() { return businessId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public BigDecimal getPriceAmount() { return priceAmount; }
    public String getCurrency() { return currency; }
    public String getColorHex() { return colorHex; }
    public Boolean getIsActive() { return isActive; }
}
