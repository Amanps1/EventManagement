package com.example.eventmanagement.service.zones;

import com.example.eventmanagement.dto.ZoneDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.model.Zone;
import com.example.eventmanagement.repository.ZoneRepository;
import com.example.eventmanagement.request.ZoneRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZoneService implements IZoneService{
    private final ZoneRepository zoneRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ZoneDto> getAllZones() {
        return zoneRepository.findAll()
                .stream()
                .map(zone -> modelMapper.map(zone, ZoneDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ZoneDto getZoneById(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + id));
        return modelMapper.map(zone, ZoneDto.class);
    }

    @Override
    public ZoneDto createZone(ZoneRequest request) {
        Zone zone = modelMapper.map(request, Zone.class);
        Zone saved = zoneRepository.save(zone);
        return modelMapper.map(saved, ZoneDto.class);
    }

    @Override
    public ZoneDto updateZone(Long id, ZoneRequest request) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + id));

        modelMapper.map(request, zone); // update fields
        Zone updated = zoneRepository.save(zone);
        return modelMapper.map(updated, ZoneDto.class);
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
}
