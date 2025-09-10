package com.example.eventmanagement.service.resources;

import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.model.Resources;
import com.example.eventmanagement.repository.ResourceManagementRepository;
import com.example.eventmanagement.request.ResourcesRequestDto;
import com.example.eventmanagement.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourcesService implements IResourcesService {

    private final ResourceManagementRepository resourceRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse createResource(ResourcesRequestDto request) {
        Resources resource = modelMapper.map(request, Resources.class);
        resourceRepository.save(resource);
        return new ApiResponse("Resource created successfully", modelMapper.map(resource, ResourcesRequestDto.class));
    }

    @Override
    public ApiResponse updateResource(Long id, ResourcesRequestDto request) {
        Resources existing = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id " + id));

        // map request -> entity but keep the same ID
        modelMapper.map(request, existing);
        existing.setId(id);

        resourceRepository.save(existing);
        return new ApiResponse("Resource updated successfully", modelMapper.map(existing, ResourcesRequestDto.class));
    }

    @Override
    public ApiResponse deleteResource(Long id) {
        Resources resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id " + id));

        resourceRepository.delete(resource);
        return new ApiResponse("Resource deleted successfully", null);
    }

    @Override
    public ApiResponse getResource(Long id) {
        Resources resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id " + id));

        return new ApiResponse("Success", modelMapper.map(resource, ResourcesRequestDto.class));
    }

    @Override
    public ApiResponse getAllResources() {
        List<Resources> resources = resourceRepository.findAll();
        List<ResourcesRequestDto> dtos = resources.stream()
                .map(resource -> modelMapper.map(resource, ResourcesRequestDto.class))
                .collect(Collectors.toList());

        return new ApiResponse("Success", dtos);
    }
}
