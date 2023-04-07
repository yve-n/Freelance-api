package com.cda.freely.controller.auth;

import com.cda.freely.config.JwtTokenProvider;
import com.cda.freely.entity.User;
import com.cda.freely.service.EmailService;
import com.cda.freely.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(){
        return "hello world";
    }

    @GetMapping("test")
    public String test(){
        return "hello test";
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        // Vérifiez si l'e-mail est déjà enregistré

        String userEmail = String.valueOf(userService.findByEmail(request.getEmail()));
        if (userEmail != null) {
            RegistrationResponse response = new RegistrationResponse();
            response.setMessage("Email already exists");
            return ResponseEntity.badRequest().body(response);
        } else {

            // Créez un nouvel utilisateur à partir de la demande d'enregistrement
            User.Gender gender = null;
            if (request.getGender().equalsIgnoreCase("M")) {
                gender = gender.MALE;
            } else if (request.getGender().equalsIgnoreCase("F")) {
                gender = gender.FEMALE;
            }

            var user = User.builder()
                    .first_name(request.getFirst_name())
                    .last_name(request.getLast_name())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(User.Role.USER)
                    .gender(gender)
                    .user_account_state(User.Status.PENDING)
                    .user_availability(User.Availability.YES)
                    .build();
            userService.createUser(user);

            // envoyez un e-mail d'activation
            String subject = "Nouvel utilisateur en attente d'approbation";
            String message = String.format("L'utilisateur %s (%s) s'est inscrit et attend l'approbation.", user.getFirst_name(), user.getEmail());
            emailService.sendEmail(user.getEmail(), subject, message);

            // Créez une réponse JSON pour informer l'utilisateur que son compte a été créé avec succès et qu'il doit activer son compte.
            RegistrationResponse response = new RegistrationResponse("User registered successfully. Please check your email to activate your account.", user);
            return ResponseEntity.ok(response);
        }
    }


}
