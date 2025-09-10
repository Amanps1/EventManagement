package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Resources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceManagementRepository extends JpaRepository<Resources,Long> {
}
