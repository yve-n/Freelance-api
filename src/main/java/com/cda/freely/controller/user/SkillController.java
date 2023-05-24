package com.cda.freely.controller.user;

import com.cda.freely.entity.Skill;
import com.cda.freely.exception.GlobalExceptionHandler;
import com.cda.freely.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class SkillController {
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(SkillController.class);
    @Autowired
    public SkillController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/skill")
    public ResponseEntity<?> addSkill(@RequestHeader("Authorization") String bearerToken,
                                        @RequestBody Skill skill){
        try{
            System.out.println(bearerToken);
            logger.info("Add a new skill-----------: {}",skill);
            Skill newSkill = new Skill();
            return ResponseEntity.ok(skill);
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
