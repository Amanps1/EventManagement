package com.example.eventmanagement.controller;

import com.example.eventmanagement.dto.EventDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.request.EventCreationRequest;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.service.event.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventManagementController {
    private final IEventService eventService;
    @PostMapping
    public ResponseEntity<ApiResponse> registerEvent(@RequestBody EventCreationRequest request){
        try{
            EventDto eventdto=eventService.registerEvent(request);
            return ResponseEntity.ok(new ApiResponse("Success",eventdto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDatetime") String sortBy) {
        try{
            List<EventDto> events = eventService.getAllEvents(page, size, sortBy);
            return ResponseEntity.ok(new ApiResponse("Success", events));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getEventById(@PathVariable Long id) {
        try {
            EventDto event = eventService.getEventById(id);
            return ResponseEntity.ok(new ApiResponse("Success", event));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateEvent(@PathVariable Long id,
                                                   @RequestBody EventCreationRequest request) {
        try {
            EventDto updated = eventService.updateEvent(id, request);
            return ResponseEntity.ok(new ApiResponse("Updated Successfully", updated));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.ok(new ApiResponse("Deleted Successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/my-events")
    public ResponseEntity<ApiResponse> getMyEvents(@RequestParam String username) {
        try{
            List<EventDto> events = eventService.getMyEvents(username);
            return ResponseEntity.ok(new ApiResponse("Success", events));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/my-registrations")
    public ResponseEntity<ApiResponse> getMyRegistrations(@RequestParam String username) {
        try{
            List<EventDto> events = eventService.getMyRegistrations(username);
            return ResponseEntity.ok(new ApiResponse("Success", events));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/calendar")
    public ResponseEntity<ApiResponse> getCalendarEvents(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        try{
            List<EventDto> events = eventService.getCalendarEvents(start, end);
            return ResponseEntity.ok(new ApiResponse("Success", events));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse> getEventsByCategory(@PathVariable String category) {
        try{
            List<EventDto> events = eventService.getEventsByCategory(category);
            return ResponseEntity.ok(new ApiResponse("Success", events));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<ApiResponse> getEventsByZone(@PathVariable Long zoneId) {
        try{
            List<EventDto> events = eventService.getEventsByZone(zoneId);
            return ResponseEntity.ok(new ApiResponse("Success", events));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/upcoming")
    public ResponseEntity<ApiResponse> getUpcomingEvents() {
        try{
            List<EventDto> events = eventService.getUpcomingEvents();
            return ResponseEntity.ok(new ApiResponse("Success", events));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchEvents(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category) {
        try{
            List<EventDto> events = eventService.searchEvents(keyword, category);
            return ResponseEntity.ok(new ApiResponse("Success", events));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

}
