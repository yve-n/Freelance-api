package com.cda.freely.controller.user;

import com.cda.freely.config.JwtTokenProvider;
import com.cda.freely.entity.Experience;
import com.cda.freely.entity.User;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.service.ExperienceService;
import com.cda.freely.service.UserService;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/experiences")
public class ExperienceController {
    private ExperienceService experienceService;
    private UserService userService;
    private JwtTokenProvider tokenProvider;
    private final Logger logger = LoggerFactory.getLogger(ExperienceController.class);
    @Autowired
    public ExperienceController(ExperienceService experienceService,
                                UserService userService,
                                JwtTokenProvider tokenProvider) {
        this.experienceService = experienceService;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/{id}")
    @JsonView({Views.Experience.class})
    public ResponseEntity<?> getExperience(@PathVariable Long id) {
        Experience experience = experienceService.findExpById(id).orElseThrow(() -> new NotFoundException("Experience not found"));
        return ResponseEntity.ok(experience);
    }
    @GetMapping("")
    @JsonView({Views.Experience.class})
    public ResponseEntity<?> getExperiences(@RequestHeader("Authorization") String bearerToken) {
        logger.warn("bearer token ----------- {}", bearerToken);
        User usernameExists = userService.checkUser(bearerToken);
        List<Experience> experiences = experienceService.getExperiences(usernameExists.getId());
        return ResponseEntity.ok(experiences);
    }

    @PostMapping("")
    @JsonView({Views.Experience.class})
    public ResponseEntity<?> createExperience(@RequestBody Experience experience,
                                              @RequestHeader("Authorization") String bearerToken
                                              ) {
            logger.warn("bearer token ----------- {}", bearerToken);
        User usernameExists = userService.checkUser(bearerToken);
        experience.setUser(usernameExists);
        Experience newExperience = experienceService.saveExperience(experience);
        usernameExists.getExperiences().add(newExperience);
        userService.saveUser(usernameExists);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExperience);
    }

    @PutMapping("/{id}")
    @JsonView({Views.Experience.class})
    public ResponseEntity<?> updateExperience(@RequestBody Experience updatedExperience,
                                              @PathVariable Long id,
                                              @RequestHeader("Authorization") String bearerToken) {
        User usernameExists = userService.checkUser(bearerToken);
        updatedExperience.setUser(usernameExists);
            Experience experience = experienceService.updateExperience(updatedExperience, id);
            experienceService.saveExperience(experience);
            return ResponseEntity.ok(experience);
    }

    @DeleteMapping("")
    @JsonView({Views.Experience.class})
    public ResponseEntity<?> deleteExperience(@RequestBody Long id) {
            experienceService.deleteExpById(id);
            return ResponseEntity.ok().build();
    }
}
