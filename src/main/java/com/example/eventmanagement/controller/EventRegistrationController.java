package com.example.eventmanagement.controller;

import com.example.eventmanagement.dto.EventRegistrationDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.request.EventRegistrationRequest;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.service.eventregistration.IEventRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
public class EventRegistrationController {
    private final IEventRegistrationService registrationService;

    @PostMapping("/{eventId}/register")
    public ResponseEntity<ApiResponse> register(
            @PathVariable Long eventId,
            @RequestBody EventRegistrationRequest request) {
        try {
            EventRegistrationDto dto = registrationService.register(eventId, request);
            return ResponseEntity.ok(new ApiResponse("Registration Successful", dto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{eventId}/unregister")
    public ResponseEntity<ApiResponse> unregister(
            @PathVariable Long eventId,
            @RequestParam Long userId) {
        try {
            registrationService.unregister(eventId, userId);
            return ResponseEntity.ok(new ApiResponse("Unregistered Successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{eventId}/registrations")
    public ResponseEntity<ApiResponse> getRegistrations(@PathVariable Long eventId) {
        try {
            List<EventRegistrationDto> registrations = registrationService.getRegistrations(eventId);
            return ResponseEntity.ok(new ApiResponse("Success", registrations));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{eventId}/registrations/{registrationId}")
    public ResponseEntity<ApiResponse> updateStatus(
            @PathVariable Long eventId,
            @PathVariable Long registrationId,
            @RequestParam String status) {
        try {
            EventRegistrationDto updated = registrationService.updateStatus(eventId, registrationId, status);
            return ResponseEntity.ok(new ApiResponse("Status Updated Successfully", updated));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/{eventId}/check-in/{userId}")
    public ResponseEntity<ApiResponse> checkIn(@PathVariable Long eventId, @PathVariable Long userId) {
        try {
            EventRegistrationDto dto = registrationService.checkIn(eventId, userId);
            return ResponseEntity.ok(new ApiResponse("Check-in Successful", dto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{eventId}/attendees")
    public ResponseEntity<ApiResponse> getAttendees(@PathVariable Long eventId) {
        try {
            List<EventRegistrationDto> attendees = registrationService.getAttendees(eventId);
            return ResponseEntity.ok(new ApiResponse("Success", attendees));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/{eventId}/feedback")
    public ResponseEntity<ApiResponse> submitFeedback(
            @PathVariable Long eventId,
            @RequestParam Long userId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment) {
        try {
            EventRegistrationDto dto = registrationService.submitFeedback(eventId, userId, rating, comment);
            return ResponseEntity.ok(new ApiResponse("Feedback Submitted", dto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{eventId}/feedback")
    public ResponseEntity<ApiResponse> getFeedback(@PathVariable Long eventId) {
        try {
            List<EventRegistrationDto> feedback = registrationService.getFeedback(eventId);
            return ResponseEntity.ok(new ApiResponse("Success", feedback));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
