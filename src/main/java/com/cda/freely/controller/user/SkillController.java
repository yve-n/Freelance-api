package com.cda.freely.controller.user;

import com.cda.freely.entity.Skill;
import com.cda.freely.exception.GlobalExceptionHandler;
import com.cda.freely.service.SkillService;
import com.cda.freely.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/user/skills")
@RequiredArgsConstructor
public class SkillController {
    private UserService userService;
    private SkillService skillService;
    private final Logger logger = LoggerFactory.getLogger(SkillController.class);
    @Autowired
    public SkillController(UserService userService, SkillService skillService) {
        this.userService = userService;
        this.skillService = skillService;
    }
    @PostMapping("")
    public ResponseEntity<?> addSkill(@RequestHeader("Authorization") String bearerToken,
                                        @RequestBody Skill skill){
        try{
            logger.warn("bearer token ---------- {}", bearerToken);
            logger.info("Add a new skill-----------: {}",skill.toString());
            Skill newSkill = skillService.saveSkill(skill);
            return ResponseEntity.ok(newSkill);
        }catch (Exception e){
            return new GlobalExceptionHandler().handleAllExceptions(e);
        }
    }
    @GetMapping("/test")
    public String testUser(@RequestHeader("Authorization") String bearerToken){
        System.out.println(bearerToken);
        return "test user";
    }

}
