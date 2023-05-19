package com.cda.freely.controller.auth;

import com.cda.freely.config.JwtTokenProvider;
import com.cda.freely.config.UserDetailsServiceImpl;
import com.cda.freely.controller.auth.externalApiData.ExternalApiResponseToJson;
import com.cda.freely.controller.auth.externalApiData.PeriodeUniteLegale;
import com.cda.freely.entity.Family;
import com.cda.freely.entity.User;
import com.cda.freely.exception.GlobalExceptionHandler;
import com.cda.freely.request.SirenRequest;
import com.cda.freely.service.EmailService;
import com.cda.freely.service.FamilyService;
import com.cda.freely.service.auth.AuthService;
import com.cda.freely.service.auth.ExternalApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private ExternalApiService externalApiService;



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
            UserDetailsServiceImpl userDetailsService,
            ExternalApiService externalApiService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.familyService = familyService;
        this.userDetailsService = userDetailsService;
        this.externalApiService = externalApiService;
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

    @PostMapping("/register/step1")
    public ResponseEntity<?> checkSiren(@RequestBody SirenRequest sirenRequest) {
        try {
            logger.info("siren-------------------> {}", sirenRequest.getSiren());
            ApiResponse response = externalApiService.fetchCompanyInfosBySiren(sirenRequest.getSiren());
            if (response.getStatusCode() == 200) {
                // Si la réponse est 200 OK
                return ResponseEntity.ok(response);
            } else {
                // Autres codes d'erreur
                switch (response.getStatusCode()) {
                    case 301:
                        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body("Unité légale fermée pour cause de doublon");
                    case 400:
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nombre incorrect de paramètres ou les paramètres sont mal formatés");
                    case 401:
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Jeton d'accès manquant ou invalide");
                    case 403:
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Droits insuffisants pour consulter les données de cette unité");
                    case 404:
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entreprise non trouvée dans la base Sirene");
                    case 406:
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Le paramètre 'Accept' de l'en-tête HTTP contient une valeur non prévue");
                    case 429:
                        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Quota d'interrogations de l'API dépassé");
                    case 500:
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur");
                    case 503:
                        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service indisponible");
                    default:
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Une erreur inconnue s'est produite.");
                }
            }
        } catch (Exception e) {
            return new GlobalExceptionHandler().handleAllExceptions(e);
        }
    }

    @PostMapping("/register/step2")
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
                        .role(User.Role.ROLE_USER)
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
