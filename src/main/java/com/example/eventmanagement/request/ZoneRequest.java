package com.example.eventmanagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneRequest {
    private String zoneName;
    private String description;
    private String boundaries;
    private Long coordinatorId;
    private int population;
    private BigDecimal areaSize;
}
