package com.example.eventmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VenueDto {
    private Long id;
    private String venueName;
    private String address;
    private Long zoneId;
    private String zoneName;
    private int capacity;
    private String description;
    private String amenities;
    private BigDecimal hourlyRate;
    private boolean isAvailable;
    private String contactPhone;
    private String accessibilityFeatures;
}
