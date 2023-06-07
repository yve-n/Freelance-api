package com.cda.freely.controller.user;

import com.cda.freely.entity.Skill;
import com.cda.freely.entity.Training;
import com.cda.freely.entity.User;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.service.TrainingService;
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
@RequestMapping("/user/trainings")
public class TrainingController {
    private TrainingService trainingService;
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(TrainingController.class);
    @Autowired
    public TrainingController(TrainingService trainingService, UserService userService) {
        this.trainingService = trainingService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @JsonView({Views.Training.class})
    public ResponseEntity<?> getTraining(@RequestHeader("Authorization") String bearerToken,
                                      @PathVariable Integer id){
        logger.warn("bearer token ---------- {}", bearerToken);
        userService.checkUser(bearerToken);
        Training training = trainingService.findTrainingById(id).orElseThrow(() -> new NotFoundException("Skill not found"));
        return ResponseEntity.status(HttpStatus.OK).body(training);

    }

    @GetMapping("")
    @JsonView({Views.Training.class})
    public ResponseEntity<?> getTrainings(@RequestHeader("Authorization") String bearerToken) {
        logger.warn("bearer token ----------- {}", bearerToken);
        User usernameExists = userService.checkUser(bearerToken);
        List<Training> trainings = trainingService.getTrainings(usernameExists.getId());
        return ResponseEntity.ok(trainings);
    }
    @PostMapping("")
    @JsonView({Views.Training.class})
    public ResponseEntity<?> addTraining(@RequestHeader("Authorization") String bearerToken,
                                      @RequestBody Training training){
        logger.warn("bearer token ---------- {}", bearerToken);

        User usernameExists = userService.checkUser(bearerToken);;
        logger.info("Add a new skill-----------: {}",usernameExists);
        training.setUser(usernameExists);
        Training newTraining = trainingService.saveTraining(training);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTraining);

    }

    @PutMapping("/{id}")
    @JsonView({Views.Training.class})
    public ResponseEntity<?> editSkill(@RequestHeader("Authorization") String bearerToken,
                                       @RequestBody Training updatedTraining,
                                       @PathVariable Integer id){
        logger.warn("bearer token ---------- {}", bearerToken);
        User usernameExists = userService.checkUser(bearerToken);;
        updatedTraining.setUser(usernameExists);
        Training newTraining = trainingService.updateTraining(updatedTraining, id);

        return ResponseEntity.status(HttpStatus.OK).body(newTraining);

    }
    @DeleteMapping("")
    @JsonView({Views.Training.class})
    public ResponseEntity<?> deleteSkill(@RequestHeader("Authorization") String bearerToken,
                                         @RequestBody Integer id){

        logger.warn("bearer token ---------- {}", bearerToken);
        userService.checkUser(bearerToken);
        trainingService.deleteTrainingById(id);
        return ResponseEntity.ok().build();
    }

}
