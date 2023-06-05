package com.cda.freely.controller.user;

import com.cda.freely.config.JwtTokenProvider;
import com.cda.freely.entity.Experience;
import com.cda.freely.entity.Skill;
import com.cda.freely.entity.User;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.service.SkillService;
import com.cda.freely.service.UserService;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/user/skills")
@RequiredArgsConstructor
public class SkillController {
    private UserService userService;
    private SkillService skillService;
    private JwtTokenProvider tokenProvider;
    private final Logger logger = LoggerFactory.getLogger(SkillController.class);
    @Autowired
    public SkillController(UserService userService,
                           SkillService skillService,
                           JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.skillService = skillService;
        this.tokenProvider = tokenProvider;
    }
    @GetMapping("/{id}")
    @JsonView({Views.Skill.class})
    public ResponseEntity<?> getSkill(@RequestHeader("Authorization") String bearerToken,
                                       @PathVariable Long id){
        logger.warn("bearer token ---------- {}", bearerToken);
        userService.checkUser(bearerToken);
        Skill skill = skillService.findSkillById(id).orElseThrow(() -> new NotFoundException("Skill not found"));
        return ResponseEntity.status(HttpStatus.OK).body(skill);

    }
    @GetMapping("")
    @JsonView({Views.Skill.class})
    public ResponseEntity<?> getExperiences(@RequestHeader("Authorization") String bearerToken) {
        logger.warn("bearer token ----------- {}", bearerToken);
        User usernameExists = userService.checkUser(bearerToken);
        List<Skill> skills = skillService.getSkills(usernameExists.getId());
        return ResponseEntity.ok(skills);
    }
    @PostMapping("")
    @JsonView({Views.Skill.class})
    public ResponseEntity<?> addSkill(@RequestHeader("Authorization") String bearerToken,
                                        @RequestBody Skill skill){
            logger.warn("bearer token ---------- {}", bearerToken);
             User usernameExists = userService.checkUser(bearerToken);
            logger.info("Add a new skill-----------: {}",usernameExists);
            skill.setUser(usernameExists);
            Skill newSkill = skillService.saveSkill(skill);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSkill);

    }

    @PutMapping("/{id}")
    @JsonView({Views.Skill.class})
    public ResponseEntity<?> editSkill(@RequestHeader("Authorization") String bearerToken,
                                      @RequestBody Skill updatedSkill,
                                      @PathVariable Long id){
        logger.warn("bearer token ---------- {}", bearerToken);
        User usernameExists = userService.checkUser(bearerToken);;
        updatedSkill.setUser(usernameExists);
        Skill skill = skillService.updateSkill(updatedSkill, id);

        return ResponseEntity.status(HttpStatus.OK).body(skill);

    }
    @DeleteMapping("")
    @JsonView({Views.Skill.class})
    public ResponseEntity<?> deleteSkill(@RequestHeader("Authorization") String bearerToken,
                                         @RequestBody Long id){

        logger.warn("bearer token ---------- {}", bearerToken);
        userService.checkUser(bearerToken);
        skillService.deleteSkillById(id);
        return ResponseEntity.ok().build();
    }

}
