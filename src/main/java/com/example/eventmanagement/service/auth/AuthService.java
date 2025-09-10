package com.example.eventmanagement.service.auth;

import com.example.eventmanagement.dto.LoginDto;
import com.example.eventmanagement.dto.LoginResponse;
import com.example.eventmanagement.dto.RegistrationDto;
import com.example.eventmanagement.dto.UserDto;
import com.example.eventmanagement.model.User;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.repository.ZoneRepository;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final ZoneRepository zoneRepo;

    @Override
    public ApiResponse register(RegistrationDto registrationDto) {
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setAddress(registrationDto.getAddress());
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setEmergencyContact(registrationDto.getEmergencyContact());
        user.setRole(registrationDto.getRole());
        user.setActive(true);

        userRepo.save(user);

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return new ApiResponse("Registration successful", userDto);
    }

    @Override
    public ApiResponse login(LoginDto loginDto) {
        User user = userRepo.findByUsernameOrEmail(loginDto.getUsername(),loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
            return new ApiResponse("Invalid credentials", "Invalid username or password");
        }

        UserDto userDto = modelMapper.map(user, UserDto.class);


        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());

        return new ApiResponse("Login successful", new LoginResponse(userDto, token));
    }

    @Override
    public ApiResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return new ApiResponse("Invalid refresh token", null);
        }

        String email = jwtTokenProvider.getUsernameFromToken(refreshToken);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newToken = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());
        return new ApiResponse("Token refreshed", newToken);
    }

    @Override
    public ApiResponse sendPasswordReset(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resetLink = "demo-reset-link"; // Replace with actual email logic
        return new ApiResponse("Password reset link sent", resetLink);
    }

    @Override
    public ApiResponse resetPassword(String token, String newPassword) {
        if (!jwtTokenProvider.validateToken(token)) {
            return new ApiResponse("Invalid reset token", null);
        }

        String email = jwtTokenProvider.getUsernameFromToken(token);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        return new ApiResponse("Password reset successful", true);
    }

    @Override
    public ApiResponse verifyEmail(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            return new ApiResponse("Invalid verification token", null);
        }

        String email = jwtTokenProvider.getUsernameFromToken(token);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmailVerified(true);
        userRepo.save(user);

        return new ApiResponse("Email verified", true);
    }

    @Override
    public ApiResponse logout(String token) {
        // Implement token blacklist logic if needed
        return new ApiResponse("Logout successful", true);
    }
}
