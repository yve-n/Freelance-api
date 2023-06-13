package com.cda.freely.controller.auth;

import com.cda.freely.config.JwtTokenProvider;
import com.cda.freely.config.UserDetailsServiceImpl;
import com.cda.freely.dto.user.UserDTO;
import com.cda.freely.entity.Family;
import com.cda.freely.entity.Tag;
import com.cda.freely.entity.User;
import com.cda.freely.exception.BadRequestException;
import com.cda.freely.exception.GlobalExceptionHandler;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.request.SirenRequest;
import com.cda.freely.service.EmailService;
import com.cda.freely.service.FamilyService;
import com.cda.freely.service.TagService;
import com.cda.freely.service.auth.AuthService;
import com.cda.freely.service.auth.ExternalApiService;
import com.cda.freely.service.auth.RegisterService;
import com.cda.freely.service.UserService;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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
    private UserService userService;
    private RegisterService registerService;
    private FamilyService familyService;
    private UserDetailsServiceImpl userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private TagService tagService;
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
            ExternalApiService externalApiService,
            RegisterService registerService,
            UserService userService,
            TagService tagService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.familyService = familyService;
        this.userDetailsService = userDetailsService;
        this.externalApiService = externalApiService;
        this.registerService = registerService;
        this.userService = userService;
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    @JsonView({Views.User.class})
    public ResponseEntity<?> home(@PathVariable Long id) {
            return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "hello test";
    }


    @PostMapping("/login")
    @JsonView({Views.AthenticationResponse.class})
    public ResponseEntity<?> authenticateUser(@RequestBody User user){
        logger.error("Authenticating user: {}", user.getEmail(), user.getPassword());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
                logger.info("token================ {}", jwt);
                   Optional<User> foundUser = authService.findByMail(user.getEmail());
                   if(foundUser.isPresent() && foundUser.get().getUserAccountState().equals(User.Status.APPROVED)){
                    logger.error("found user -----------> {}");
                        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt);
                        jwtAuthenticationResponse.setUser(foundUser);
                        return ResponseEntity.ok(jwtAuthenticationResponse);
                   }else{
                       throw new NotFoundException("User Not Found");
                   }
    }

    @PostMapping("/register/step1")
    public ResponseEntity<?> checkSiren(@RequestBody SirenRequest sirenRequest) {
        try {
            logger.info("siren-------------------> {}", sirenRequest.getSiren());
            ApiResponse response = externalApiService.fetchCompanyInfosBySiren(sirenRequest.getSiren());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new GlobalExceptionHandler().handleAllExceptions(e);
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
        logger.error("creation-------------------> {}", userDTO.toString());
        User newUser = registerService.CreateUser(userDTO);

        // envoyez un e-mail d'activation
            emailService.sendEmail(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);

    }
}
