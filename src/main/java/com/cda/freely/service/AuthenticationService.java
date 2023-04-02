package com.cda.freely.service;

import com.cda.freely.config.JwtService;
import com.cda.freely.controller.auth.AuthenticateRequest;
import com.cda.freely.controller.auth.AuthenticateResponse;
import com.cda.freely.controller.auth.RegisterRequest;
import com.cda.freely.entity.Gender;
import com.cda.freely.entity.User;
import com.cda.freely.entity.UserRole;
import com.cda.freely.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse register(RegisterRequest request) {
        Gender gender = null;
        if(request.getGender().equalsIgnoreCase("M")){
             gender = Gender.MALE;
        }else if(request.getGender().equalsIgnoreCase("F")){
            gender = Gender.FEMALE;
        }

        var user = User.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .gender(gender)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticateResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user  = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticateResponse.builder()
                .token(jwtToken)
                .build();
    }
}
