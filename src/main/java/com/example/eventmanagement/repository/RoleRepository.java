package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Role;
import com.example.eventmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role,Long> {
    Optional<Role> findByName(User.RoleEnum name);
}
