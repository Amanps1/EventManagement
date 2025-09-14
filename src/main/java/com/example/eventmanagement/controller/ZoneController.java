package com.example.eventmanagement.controller;

import com.example.eventmanagement.dto.ZoneDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.request.ZoneRequest;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.service.zones.IZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {
    private final IZoneService zoneService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllZones() {
        List<ZoneDto> zones = zoneService.getAllZones();
        return ResponseEntity.ok(new ApiResponse("Success", zones));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getZoneById(@PathVariable Long id) {
        try {
            ZoneDto zone = zoneService.getZoneById(id);
            return ResponseEntity.ok(new ApiResponse("Success", zone));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createZone(@RequestBody ZoneRequest zoneRequest) {
        try {
            ZoneDto zone = zoneService.createZone(zoneRequest);
            return ResponseEntity.ok(new ApiResponse("Zone created successfully", zone));
        } catch (IllegalArgumentException e) {
            // Return 400 Bad Request for duplicate zone name
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateZone(@PathVariable Long id,
                                                  @RequestBody ZoneRequest zoneRequest) {
        try {
            ZoneDto updatedZone = zoneService.updateZone(id, zoneRequest);
            return ResponseEntity.ok(new ApiResponse("Zone updated successfully", updatedZone));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteZone(@PathVariable Long id) {
        try {
            zoneService.deleteZone(id);
            return ResponseEntity.ok(new ApiResponse("Zone deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<ApiResponse> getZoneEvents(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse("Success", zoneService.getZoneEvents(id)));
    }

    @GetMapping("/{id}/residents")
    public ResponseEntity<ApiResponse> getZoneResidents(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse("Success", zoneService.getZoneResidents(id)));
    }
}
