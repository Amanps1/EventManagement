package com.example.eventmanagement.dto;

import com.example.eventmanagement.model.Resources;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourcesDto {
    private Long id;
    private String resourceName;
    private Resources.ResourceType resourceType;
    private String description;
    private Long venueId;
    private int quantityAvailable;
    private BigDecimal hourlyRate;
    private boolean isAvailable;
}
