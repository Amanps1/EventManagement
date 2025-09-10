package com.example.eventmanagement.controller;

import com.example.eventmanagement.request.ResourcesRequestDto;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.service.resources.IResourcesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceManagementController {
    private final IResourcesService resourceService;

    @PostMapping
    public ResponseEntity<ApiResponse> createResource(@RequestBody ResourcesRequestDto request) {
        try {
            return ResponseEntity.ok(resourceService.createResource(request));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateResource(@PathVariable Long id, @RequestBody ResourcesRequestDto request) {
        try {
            return ResponseEntity.ok(resourceService.updateResource(id, request));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteResource(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(resourceService.deleteResource(id));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getResource(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(resourceService.getResource(id));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllResources() {
        try {
            return ResponseEntity.ok(resourceService.getAllResources());
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
