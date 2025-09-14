package com.example.eventmanagement.service.zones;

import com.example.eventmanagement.dto.ZoneDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.model.User;
import com.example.eventmanagement.model.Zone;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.repository.ZoneRepository;
import com.example.eventmanagement.request.ZoneRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZoneService implements IZoneService {

    private final ZoneRepository zoneRepository;
    private final UserRepository userRepository;

    @Override
    public List<ZoneDto> getAllZones() {
        return zoneRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ZoneDto getZoneById(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + id));
        return convertToDto(zone);
    }

    @Override
    public ZoneDto createZone(ZoneRequest request) {
        if (zoneRepository.existsByZoneName(request.getZoneName())) {
            throw new IllegalArgumentException("Zone with name '" + request.getZoneName() + "' already exists!");
        }

        Zone zone = new Zone();
        zone.setZoneName(request.getZoneName());
        zone.setDescription(request.getDescription());
        zone.setBoundaries(request.getBoundaries());
        zone.setPopulation(request.getPopulation());
        zone.setAreaSize(request.getAreaSize());

        if (request.getCoordinatorId() != null) {
            User coordinator = userRepository.findById(request.getCoordinatorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Coordinator not found with id: " + request.getCoordinatorId()));
            zone.setCoordinator(coordinator);
        }

        Zone saved = zoneRepository.save(zone);
        return convertToDto(saved);
    }

    @Override
    public ZoneDto updateZone(Long id, ZoneRequest request) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + id));

        if (!zone.getZoneName().equalsIgnoreCase(request.getZoneName()) &&
                zoneRepository.existsByZoneName(request.getZoneName())) {
            throw new IllegalArgumentException("Zone with name '" + request.getZoneName() + "' already exists!");
        }

        zone.setZoneName(request.getZoneName());
        zone.setDescription(request.getDescription());
        zone.setBoundaries(request.getBoundaries());
        zone.setPopulation(request.getPopulation());
        zone.setAreaSize(request.getAreaSize());

        if (request.getCoordinatorId() != null) {
            User coordinator = userRepository.findById(request.getCoordinatorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Coordinator not found with id: " + request.getCoordinatorId()));
            zone.setCoordinator(coordinator);
        }

        Zone updated = zoneRepository.save(zone);
        return convertToDto(updated);
    }

    @Override
    public void deleteZone(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + id));
        zoneRepository.delete(zone);
    }

    @Override
    public List<Object> getZoneEvents(Long zoneId) {
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + zoneId));
        return zone.getVenues()
                .stream()
                .flatMap(venue -> venue.getEvents().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Object> getZoneResidents(Long zoneId) {
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + zoneId));
        return List.copyOf(zone.getUsers());
    }

    // ---------------- Fully manual DTO conversion ----------------
    private ZoneDto convertToDto(Zone zone) {
        ZoneDto zoneDto = new ZoneDto();
        zoneDto.setId(zone.getId());
        zoneDto.setZoneName(zone.getZoneName());
        zoneDto.setDescription(zone.getDescription());
        zoneDto.setBoundaries(zone.getBoundaries());
        zoneDto.setPopulation(zone.getPopulation());
        zoneDto.setAreaSize(zone.getAreaSize());

        if (zone.getCoordinator() != null) {
            zoneDto.setCoordinatorName(zone.getCoordinator().getFirstName() + " " + zone.getCoordinator().getLastName());
        } else {
            zoneDto.setCoordinatorName(null);
        }

        return zoneDto;
    }
}
