package com.cda.freely.controller.admin;

import com.cda.freely.entity.User;
import com.cda.freely.service.admin.AdminService;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
        private AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("")
    public String HelloAdmin(){
        return "hello admin";
    }
    @GetMapping("/test")
    public String testUser(){
        return "hello admin test";
    }

    @GetMapping("/users")
    @JsonView({Views.User.class})
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getUsersWithPendingAccountState());
    }
}
