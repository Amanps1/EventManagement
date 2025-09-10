package com.example.eventmanagement.service.eventregistration;

import com.example.eventmanagement.dto.EventRegistrationDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.EventRegistration;
import com.example.eventmanagement.model.User;
import com.example.eventmanagement.repository.EventManagementRepository;
import com.example.eventmanagement.repository.EventRegistrationRepository;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.request.EventRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventRegistrationService implements IEventRegistrationService{
    private final EventRegistrationRepository registrationRepo;
    private final EventManagementRepository eventRepo;
    private final UserRepository userRepo;
    @Override
    public EventRegistrationDto register(Long eventId, EventRegistrationRequest request) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        EventRegistration registration = new EventRegistration();
        registration.setEvent(event);
        registration.setUser(user);
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus(EventRegistration.Status.REGISTERED);
        registration.setSpecialRequirements(request.getSpecialRequirements());

        return toDto(registrationRepo.save(registration));
    }

    @Override
    public void unregister(Long eventId, Long userId) {
        EventRegistration registration = registrationRepo
                .findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));

        registration.setStatus(EventRegistration.Status.CANCELLED);
        registrationRepo.save(registration);
    }

    @Override
    public List<EventRegistrationDto> getRegistrations(Long eventId) {
        List<EventRegistration> registrations = registrationRepo.findByEventId(eventId);
        return registrations.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EventRegistrationDto updateStatus(Long eventId, Long registrationId, String status) {
        EventRegistration registration = registrationRepo.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));

        registration.setStatus(EventRegistration.Status.valueOf(status.toUpperCase()));
        return toDto(registrationRepo.save(registration));
    }

    @Override
    public EventRegistrationDto checkIn(Long eventId, Long userId) {
        EventRegistration registration = registrationRepo
                .findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));

        registration.setCheckInTime(LocalDateTime.now());
        registration.setStatus(EventRegistration.Status.ATTENDED);
        return toDto(registrationRepo.save(registration));
    }

    @Override
    public List<EventRegistrationDto> getAttendees(Long eventId) {
        return registrationRepo.findByEventIdAndStatus(eventId, EventRegistration.Status.ATTENDED)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EventRegistrationDto submitFeedback(Long eventId, Long userId, Integer rating, String comment) {
        EventRegistration registration = registrationRepo
                .findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));

        registration.setFeedbackRating(rating);
        registration.setFeedbackComment(comment);
        return toDto(registrationRepo.save(registration));
    }

    @Override
    public List<EventRegistrationDto> getFeedback(Long eventId) {
        return registrationRepo.findByEventId(eventId)
                .stream()
                .filter(r -> r.getFeedbackRating() != null)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EventRegistrationDto toDto(EventRegistration registration) {
        return EventRegistrationDto.builder()
                .id(registration.getId())
                .eventId(registration.getEvent().getId())
                .username(registration.getUser().getUsername())
                .status(registration.getStatus().name())
                .registrationDate(registration.getRegistrationDate())
                .checkInTime(registration.getCheckInTime())
                .feedbackRating(registration.getFeedbackRating())
                .feedbackComment(registration.getFeedbackComment())
                .specialRequirements(registration.getSpecialRequirements())
                .build();
    }
}
