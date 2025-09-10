package com.example.eventmanagement.request;

import lombok.Data;

@Data
public class EventRegistrationRequest {
    private Long eventId;
    private String username;
    private String specialRequirements;


}
