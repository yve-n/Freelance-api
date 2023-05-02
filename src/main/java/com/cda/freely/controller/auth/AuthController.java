package com.cda.freely.controller.auth;

import com.cda.freely.config.JwtTokenProvider;
import com.cda.freely.config.UserDetailsServiceImpl;
import com.cda.freely.config.UserPrincipal;
import com.cda.freely.entity.Family;
import com.cda.freely.entity.User;
import com.cda.freely.exception.GlobalExceptionHandler;
import com.cda.freely.service.EmailService;
import com.cda.freely.service.FamilyService;
import com.cda.freely.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    private PasswordEncoder passwordEncoder;
    private AuthService authService;
    private FamilyService familyService;
    private UserDetailsServiceImpl userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);



    /**
     * Injection de dépendance par Constructeur.
     * tous les params du construct sont des dépendances.
     */
    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider,
            EmailService emailService,
            PasswordEncoder passwordEncoder,
            AuthService authService,
            FamilyService familyService,
            UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.familyService = familyService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("")
    public String home() {
        return "hello world";
    }

    @GetMapping("/test")
    public String test() {
        return "hello test";
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user){
        logger.info("user", user);
        logger.info("Authenticating user: {}", user.getEmail(), user.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            try{
                Optional<User> foundUser = authService.findByMail(user.getEmail());
                JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt);
                jwtAuthenticationResponse.setUser(foundUser);
                return ResponseEntity.ok(jwtAuthenticationResponse);
            }catch(Exception e){
                return new GlobalExceptionHandler().handleAllExceptions(e);
            }

        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", user.getEmail(), e);
            logger.error("error", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),e.getCause().getCause().getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Vérifiez si l'e-mail est déjà enregistré
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            Optional<User> ExistUser = authService.findByMail(user.getEmail());
            if (ExistUser.isPresent()) {
                errorResponse.setMessage("Email already exists");
                return ResponseEntity.badRequest().body(errorResponse);
            } else {
                // Créez un nouvel utilisateur à partir de la demande d'enregistrement
                Optional<Family> family = familyService.findById(user.getFamily().getId());
                family.ifPresent(user::setFamily);
                var newUser = User.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .gender(user.getGender())
                        .role(User.Role.USER)
                        .userAccountState(User.Status.PENDING)
                        .userAvailability(User.Availability.YES)
                        .createdAt(new Date())
                        .family(user.getFamily())
                        .build();
                authService.saveUser(newUser);

                // envoyez un e-mail d'activation
                try{
                    String subject = "Bienvenue sur Freely";
                    String message = "Votre compte est actuellement en attente de validation. Vous recevrez " +
                            "un email lorsque celui ci sera validé." +
                            newUser.getFirstName() +  newUser.getEmail();
                    emailService.sendEmail(newUser.getEmail(), subject, message);
                    return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
                }catch(Exception e){
                    return new GlobalExceptionHandler().handleAllExceptions(e);
                }
            }
        } catch (Exception e) {
            errorResponse.setMessage(e.getMessage());
            errorResponse.setDetails(e.getCause().getCause().getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
