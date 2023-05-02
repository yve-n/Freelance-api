package com.cda.freely.service.user;

import com.cda.freely.entity.User;
import com.cda.freely.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
     private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long id){ return userRepository.findById(id);}
}
