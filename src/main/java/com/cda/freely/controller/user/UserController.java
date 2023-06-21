package com.cda.freely.controller.user;

import com.cda.freely.config.JwtTokenProvider;
import com.cda.freely.entity.User;
import com.cda.freely.service.auth.AuthService;
import com.cda.freely.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;
    private JwtTokenProvider tokenProvider;
    private AuthService authService;
    @Autowired
    public UserController(UserService userService,JwtTokenProvider tokenProvider, AuthService authService){
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authService = authService;
    }

    @PostMapping("/profile")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String bearerToken){
        User user =  userService.checkUser(bearerToken);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String bearerToken) {
        User user =  userService.checkUser(bearerToken);
        user.setTokenNumber(user.getTokenNumber() + 1);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }
}
