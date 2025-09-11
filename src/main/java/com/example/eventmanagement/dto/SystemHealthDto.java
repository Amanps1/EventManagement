package com.example.eventmanagement.dto;

import lombok.Data;

@Data
public class SystemHealthDto {
    private String status;
    private String databaseStatus;
    private String message;
}
