package com.example.eventmanagement.service.analytics;

import com.example.eventmanagement.dto.AnalyticDto;
import com.example.eventmanagement.dto.AuditLogDto;
import com.example.eventmanagement.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService implements IAnalyticsService{
    private final AuditLogRepository auditLogRepo;
    private final ModelMapper modelMapper;

    public List<AuditLogDto> getAuditLogs() {
        return auditLogRepo.findAll().stream()
                .map(log -> {
                    AuditLogDto dto = modelMapper.map(log, AuditLogDto.class);
                    dto.setUserId(log.getUser().getId());
                    dto.setUsername(log.getUser().getUsername());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public AnalyticDto getSystemHealth() {
        AnalyticDto dto = new AnalyticDto();
        dto.setMetricName("System Health");
        dto.setValue("Healthy"); // later connect with actuator/monitoring
        return dto;
    }
}
