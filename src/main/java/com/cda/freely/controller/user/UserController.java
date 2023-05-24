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

    @GetMapping("")
    public String HelloUser(@RequestHeader("Authorization") String bearerToken){
        System.out.println(bearerToken);
        return "hello user";
    }
    @GetMapping("/test")
    public String testUser(@RequestHeader("Authorization") String bearerToken){
        System.out.println(bearerToken);
        return "test user";
    }

    @PostMapping("/profile")
    public ResponseEntity<?> getUser(@RequestBody User user){
        try{
            Optional<User> foundUser = userService.findById(user.getId());
            if (foundUser.isPresent()){
                return ResponseEntity.ok(foundUser);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
            }

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String jwt) {
        String token = jwt.substring(7);
        Long userId = (Long) tokenProvider.getTokenBody(token).get("id");

        Optional<User> user = authService.findById(userId);
        if (user.isPresent()) {
            user.get().setTokenNumber(user.get().getTokenNumber() + 1);
            authService.saveUser(user.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }
}
