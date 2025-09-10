package com.example.eventmanagement.service.auth;

import com.example.eventmanagement.dto.LoginDto;
import com.example.eventmanagement.dto.RegistrationDto;
import com.example.eventmanagement.response.ApiResponse;

public interface IAuthService {

    ApiResponse register(RegistrationDto registrationDto);

    ApiResponse login(LoginDto loginDto);

   

    ApiResponse refreshToken(String refreshToken);

    ApiResponse sendPasswordReset(String email);

    ApiResponse resetPassword(String token, String newPassword);

    ApiResponse verifyEmail(String token);

    ApiResponse logout(String token);
}
