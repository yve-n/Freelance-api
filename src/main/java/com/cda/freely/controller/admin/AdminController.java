package com.cda.freely.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @GetMapping("")
    public String HelloAdmin(){
        return "hello admin";
    }
    @GetMapping("/test")
    public String testUser(){
        return "hello admin test";
    }
}
