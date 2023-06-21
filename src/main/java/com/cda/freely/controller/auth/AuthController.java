package com.cda.freely.controller.auth;

import com.cda.freely.config.JwtTokenProvider;
import com.cda.freely.dto.user.UserDTO;
import com.cda.freely.entity.User;
import com.cda.freely.exception.BadRequestException;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.service.EmailService;
import com.cda.freely.service.UserService;
import com.cda.freely.service.auth.AuthService;
import com.cda.freely.service.auth.RegisterService;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * Autowired permet de réaliser une injection de dépendance
     */
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;
    private EmailService emailService;
    private AuthService authService;
    private UserService userService;
    private RegisterService registerService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Injection de dépendance par Constructeur.
     * tous les params du construct sont des dépendances.
     */
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
                          EmailService emailService, AuthService authService,
                          RegisterService registerService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.emailService = emailService;
        this.authService = authService;
        this.registerService = registerService;
        this.userService = userService;
    }
    @PostMapping("/login")
    @JsonView({Views.AthenticationResponse.class})
    public ResponseEntity<?> authenticateUser(@RequestBody User user){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
                   Optional<User> foundUser = authService.findByMail(user.getEmail());
                   if(foundUser.isPresent() && foundUser.get().getUserAccountState().equals(User.Status.APPROVED)){
                        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt);
                        jwtAuthenticationResponse.setUser(foundUser);
                        return ResponseEntity.ok(jwtAuthenticationResponse);
                   }else{
                       throw new NotFoundException("User Not Found");
                   }
    }
    @JsonView(Views.User.class)
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        // Vérifiez si l'e-mail est déjà enregistré
        boolean emailExists = userService.findUserByMail(userDTO.getEmail()).isPresent();
        if (emailExists) {
            throw new BadRequestException("Email already exists");
        }
        // Créez un nouvel utilisateur à partir de la demande d'enregistrement
        User newUser = registerService.CreateUser(userDTO);

        // envoyez un e-mail d'activation
            emailService.sendEmail(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
