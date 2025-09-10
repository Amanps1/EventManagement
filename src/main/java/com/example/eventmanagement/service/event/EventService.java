package com.example.eventmanagement.service.event;

import com.example.eventmanagement.dto.EventDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.User;
import com.example.eventmanagement.model.Venue;
import com.example.eventmanagement.repository.EventManagementRepository;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.repository.VenueRepository;
import com.example.eventmanagement.request.EventCreationRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.ResourceSet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService {
    private final EventManagementRepository eventRepo;
    private final VenueRepository venueRepo;
    private final UserRepository userRepo;
    @Override
    public EventDto registerEvent(EventCreationRequest request) {
        Venue venueName=venueRepo.findByVenueName(request.getVenueName())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

        User organizer=userRepo.findByUsername(request.getOrganizerName())
                .orElseThrow(()->new ResourceNotFoundException("Organizer not found"));

        Event event=new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setCategory(Event.Category.valueOf(request.getCategory().toUpperCase()));
        event.setStartDatetime(request.getStartDatetime());
        event.setEndDatetime(request.getEndDatetime());
        event.setLocation(request.getLocation());
        event.setVenue(venueName);
        event.setOrganizer(organizer);
        event.setMaxCapacity(request.getMaxCapacity());
        event.setRegistrationDeadline(request.getRegistrationDeadline());
        event.setRecurring(request.isRecurring());
        event.setRecurrencePattern(request.getRecurrencePattern());
        event.setStatus(Event.Status.PENDING_APPROVAL);

        return toDto(eventRepo.save(event));

    }

    @Override
    public List<EventDto> getAllEvents(int page, int size, String sortBy) {
        return eventRepo.findAll(PageRequest.of(page, size, Sort.by(sortBy)))
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDto getEventById(Long id) {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return toDto(event);
    }

    @Override
    public EventDto updateEvent(Long id, EventCreationRequest request) {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setCategory(Event.Category.valueOf(request.getCategory().toUpperCase()));
        event.setStartDatetime(request.getStartDatetime());
        event.setEndDatetime(request.getEndDatetime());
        event.setLocation(request.getLocation());
        event.setMaxCapacity(request.getMaxCapacity());
        event.setRegistrationDeadline(request.getRegistrationDeadline());
        event.setRecurring(request.isRecurring());
        event.setRecurrencePattern(request.getRecurrencePattern());

        return toDto(eventRepo.save(event));
    }

    @Override
    public void deleteEvent(Long id) {
        if (!eventRepo.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }
        eventRepo.deleteById(id);
    }

    @Override
    public List<EventDto> getMyEvents(String username) {
        return eventRepo.findByOrganizerUsername(username)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getMyRegistrations(String username) {
        return eventRepo.findMyRegistrations(username)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getCalendarEvents(LocalDateTime start, LocalDateTime end) {
        return eventRepo.findEventsInRange(start, end)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getEventsByCategory(String category) {
        return eventRepo.findByCategory(Event.Category.valueOf(category.toUpperCase()))
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getEventsByZone(Long zoneId) {
        return eventRepo.findByZone(zoneId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getUpcomingEvents() {
        return eventRepo.findByStartDatetimeAfter(LocalDateTime.now())
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> searchEvents(String keyword, String category) {
        Event.Category cat = (category != null) ? Event.Category.valueOf(category.toUpperCase()) : null;
        return eventRepo.searchEvents(keyword, cat)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    private EventDto toDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getCategory().name(),
                event.getStatus().name(),
                event.getStartDatetime(),
                event.getEndDatetime(),
                event.getLocation(),
                event.getVenue().getVenueName(),
                event.getOrganizer().getUsername(),
                event.getMaxCapacity(),
                event.getCurrentRegistrations(),
                event.getRegistrationDeadline(),
                event.isRecurring(),
                event.getRecurrencePattern()
        );
    }
}
