package com.cda.freely.service.auth;

import com.cda.freely.entity.User;
import com.cda.freely.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id){ return userRepository.findById(id);}

    public Optional<User> findByMail(String email){ return Optional.ofNullable(userRepository.findByEmail(email));}

}
