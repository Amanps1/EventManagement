package com.example.eventmanagement.service.resources;

import com.example.eventmanagement.request.ResourcesRequestDto;
import com.example.eventmanagement.response.ApiResponse;

public interface IResourcesService {
    ApiResponse createResource(ResourcesRequestDto request);

    ApiResponse updateResource(Long id, ResourcesRequestDto request);

    ApiResponse deleteResource(Long id);

    ApiResponse getResource(Long id);

    ApiResponse getAllResources();
}
