package com.example.eventmanagement.request;

import lombok.Data;

@Data
public class ReportRequestDto {
    private String reportType; // event, community, custom
    private String filters;
}
