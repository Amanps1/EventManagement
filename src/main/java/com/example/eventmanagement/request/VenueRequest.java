package com.example.eventmanagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueRequest {
    private String venueName;
    private String address;
    private Long zoneId;
    private int capacity;
    private String description;
    private String amenities;
    private BigDecimal hourlyRate;
    private boolean isAvailable;
    private String contactPhone;
    private String accessibilityFeatures;
}
