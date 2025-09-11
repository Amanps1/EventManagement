package com.example.eventmanagement.controller;

import com.example.eventmanagement.dto.AnalyticDto;
import com.example.eventmanagement.dto.AuditLogDto;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.service.analytics.IAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnalysisController {
    private final IAnalyticsService analyticsService;


    @GetMapping("/analytics/events/summary")
    public ResponseEntity<ApiResponse> getEventAnalytics() {
        return ResponseEntity.ok(new ApiResponse("Event analytics summary", null));
    }

    @GetMapping("/reports/custom")
    public ResponseEntity<ApiResponse> generateCustomReport(@RequestParam String type) {
        return ResponseEntity.ok(new ApiResponse("Custom report generated", type));
    }

    // 8.11 Administrative
    @GetMapping("/admin/system-health")
    public ResponseEntity<ApiResponse> getSystemHealth() {
        AnalyticDto dto = analyticsService.getSystemHealth();
        return ResponseEntity.ok(new ApiResponse("System health retrieved", dto));
    }

    @GetMapping("/admin/audit-logs")
    public ResponseEntity<ApiResponse> getAuditLogs() {
        List<AuditLogDto> logs = analyticsService.getAuditLogs();
        return ResponseEntity.ok(new ApiResponse("Audit logs retrieved", logs));
    }
}
