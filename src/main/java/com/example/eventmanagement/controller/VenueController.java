package com.example.eventmanagement.controller;

import com.example.eventmanagement.dto.VenueDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.request.VenueRequest;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.service.venue.IVenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/venues")
public class VenueController {
    public final IVenueService venueService;
    @GetMapping
    public ResponseEntity<ApiResponse> getAllVenues(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        List<VenueDto> venues = venueService.getAllVenues(page, size);
        return ResponseEntity.ok(new ApiResponse("Success", venues));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getVenueById(@PathVariable Long id) {
        try {
            VenueDto venue = venueService.getVenueById(id);
            return ResponseEntity.ok(new ApiResponse("Success", venue));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createVenue(@RequestBody VenueRequest venueRequest) {
        VenueDto venue = venueService.createVenue(venueRequest);
        return ResponseEntity.ok(new ApiResponse("Venue created successfully", venue));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateVenue(@PathVariable Long id,
                                                   @RequestBody VenueRequest venueRequest) {
        try {
            VenueDto updatedVenue = venueService.updateVenue(id, venueRequest);
            return ResponseEntity.ok(new ApiResponse("Venue updated successfully", updatedVenue));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteVenue(@PathVariable Long id) {
        try {
            venueService.deleteVenue(id);
            return ResponseEntity.ok(new ApiResponse("Venue deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse> getAvailableVenues(@RequestParam String startDate,
                                                          @RequestParam String endDate) {
        return ResponseEntity.ok(new ApiResponse("Success", venueService.getAvailableVenues(startDate, endDate)));
    }

    @GetMapping("/{id}/bookings")
    public ResponseEntity<ApiResponse> getVenueBookings(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse("Success", venueService.getVenueBookings(id)));
    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<ApiResponse> getVenuesByZone(@PathVariable Long zoneId) {
        return ResponseEntity.ok(new ApiResponse("Success", venueService.getVenuesByZone(zoneId)));
    }
}
