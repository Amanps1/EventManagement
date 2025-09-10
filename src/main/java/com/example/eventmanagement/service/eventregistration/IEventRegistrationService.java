package com.example.eventmanagement.service.eventregistration;

import com.example.eventmanagement.dto.EventRegistrationDto;
import com.example.eventmanagement.request.EventRegistrationRequest;

import java.util.List;

public interface IEventRegistrationService {
    EventRegistrationDto register(Long eventId, EventRegistrationRequest request);

    void unregister(Long eventId, Long userId);

    List<EventRegistrationDto> getRegistrations(Long eventId);

    EventRegistrationDto updateStatus(Long eventId, Long registrationId, String status);

    EventRegistrationDto checkIn(Long eventId, Long userId);

    List<EventRegistrationDto> getAttendees(Long eventId);

    EventRegistrationDto submitFeedback(Long eventId, Long userId, Integer rating, String comment);

    List<EventRegistrationDto> getFeedback(Long eventId);
}
