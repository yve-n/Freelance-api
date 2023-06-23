package com.cda.freely.controller.admin;

import com.cda.freely.controller.user.ExperienceController;
import com.cda.freely.entity.History;
import com.cda.freely.entity.User;
import com.cda.freely.service.EmailService;
import com.cda.freely.service.HistoryService;
import com.cda.freely.service.UserService;
import com.cda.freely.service.admin.AdminService;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
        private AdminService adminService;
        private UserService userService;
        private EmailService emailService;
        private HistoryService historyService;
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(AdminService adminService, UserService userService,
                           EmailService emailService,HistoryService historyService) {
        this.adminService = adminService;
        this.userService = userService;
        this.emailService = emailService;
        this.historyService = historyService;
    }

    @GetMapping("/pending_users")
    @JsonView({Views.User.class})
    public ResponseEntity<?> getPendingUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getUsersWithPendingAccountState());
    }
    @GetMapping("/users")
    @JsonView({Views.User.class})
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getUsers());
    }
    @GetMapping("/contacts")
    @JsonView({Views.Contact.class})
    public ResponseEntity<?> getContacts(){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getContacts());
    }
    @GetMapping("/histories")
    @JsonView({Views.History.class})
    public ResponseEntity<?> getHistories(){
        return ResponseEntity.status(HttpStatus.OK).body(historyService.getHistories());
    }

    @PostMapping("/validate/user")
    public ResponseEntity<?> validateUserAccount(@RequestBody Map<String, Long> body){
        Long id = body.get("id");
        User user = userService.findUserById(id);
        user.setUserAccountState(User.Status.APPROVED);
        User userValidated = userService.saveUser(user);

        // envoyez un e-mail de validation
        emailService.sendAccountActivatedEmail(userValidated);
        return ResponseEntity.status(HttpStatus.OK).body(userValidated);
    }

    @PostMapping("/refuse/user")
    @Transactional
    public ResponseEntity<?> refuseUserAccount(@RequestBody Map<String, Long> body){
        Long id = body.get("id");
        User user = userService.findUserById(id);
        user.setUserAccountState(User.Status.DECLINED);
        User userRefused = userService.saveUser(user);

        //créer un nouvel historique
        History newHistory = new History();
        newHistory.setCreatedAt(new Date());
        newHistory.setDescription("problème lié à l'entreprise de l'utilisateur.");
        newHistory.setUser(userRefused);
        historyService.saveHistory(newHistory);
        
        // envoyez un e-mail de refus
        emailService.sendAccountNotValidatedEmail(userRefused);
        return ResponseEntity.status(HttpStatus.OK).body(userRefused);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String bearerToken) {
        logger.warn("bearer token ----------- {}", bearerToken);
       User user =  userService.checkUser(bearerToken);
            user.setTokenNumber(user.getTokenNumber() + 1);
            userService.saveUser(user);
        logger.warn("bearer token ----------- {}", "user");
            return ResponseEntity.ok().build();
    }

}
