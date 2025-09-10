package com.example.eventmanagement.service.event;

import com.example.eventmanagement.dto.EventDto;
import com.example.eventmanagement.request.EventCreationRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface IEventService {
    EventDto registerEvent(EventCreationRequest request);

    List<EventDto> getAllEvents(int page, int size, String sortBy);

    EventDto getEventById(Long id);

    EventDto updateEvent(Long id, EventCreationRequest request);

    void deleteEvent(Long id);

    List<EventDto> getMyEvents(String username);

    List<EventDto> getMyRegistrations(String username);

    List<EventDto> getCalendarEvents(LocalDateTime start, LocalDateTime end);

    List<EventDto> getEventsByCategory(String category);

    List<EventDto> getEventsByZone(Long zoneId);

    List<EventDto> getUpcomingEvents();

    List<EventDto> searchEvents(String keyword, String category);
}
