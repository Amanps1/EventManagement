package com.example.eventmanagement.request;

import com.example.eventmanagement.model.Resources.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourcesRequestDto {
    private String resourceName;
    private ResourceType resourceType;
    private String description;
    private Long venueId;
    private int quantityAvailable;
    private BigDecimal hourlyRate;
}
