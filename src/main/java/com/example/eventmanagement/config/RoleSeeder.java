package com.example.eventmanagement.config;

import com.example.eventmanagement.model.Role;
import com.example.eventmanagement.model.User;
import com.example.eventmanagement.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        for (User.RoleEnum roleEnum : User.RoleEnum.values()) {
            roleRepository.findByName(roleEnum).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleEnum);
                role.setDescription(roleEnum.name() + " role");
                return roleRepository.save(role);
            });
        }
        System.out.println("✅ Roles seeded successfully");
    }
}
