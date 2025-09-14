package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    List<User> findByZoneId(Long zoneId);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String username1);

    Optional<User> findByUsernameIgnoreCaseOrEmailIgnoreCase(String principal, String principal1);

    Optional<User> findByUsernameIgnoreCase(String username);
}
