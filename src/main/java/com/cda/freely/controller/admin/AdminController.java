package com.cda.freely.controller.admin;

import com.cda.freely.service.admin.AdminService;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
        private AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
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
}
