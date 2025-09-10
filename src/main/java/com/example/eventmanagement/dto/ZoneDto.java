package com.example.eventmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ZoneDto {
    private Long id;
    private String zoneName;
    private String description;
    private String boundaries;
    private String coordinatorName;
    private int population;
    private BigDecimal areaSize;
}
