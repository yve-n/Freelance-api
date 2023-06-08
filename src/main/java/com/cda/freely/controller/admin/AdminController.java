package com.cda.freely.controller.admin;

import com.cda.freely.entity.User;
import com.cda.freely.service.EmailService;
import com.cda.freely.service.UserService;
import com.cda.freely.service.admin.AdminService;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
        private AdminService adminService;
        private UserService userService;
        private EmailService emailService;
    @Autowired
    public AdminController(AdminService adminService, UserService userService, EmailService emailService) {
        this.adminService = adminService;
        this.userService = userService;
        this.emailService = emailService;
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

    @PostMapping("/validate/user")
    @JsonView({Views.Contact.class})
    public ResponseEntity<?> validateUserAccount(@RequestBody Map<String, Long> body){
        Long id = body.get("id");
        User user = userService.findUserById(id);
        user.setUserAccountState(User.Status.APPROVED);
        User userValidated = userService.saveUser(user);

        // envoyez un e-mail de validation
        emailService.sendAccountActivatedEmail(userValidated);
        return ResponseEntity.status(HttpStatus.OK).body(userValidated);
    }
}
