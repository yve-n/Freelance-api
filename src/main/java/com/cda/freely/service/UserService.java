package com.cda.freely.service;

import com.cda.freely.config.JwtTokenProvider;
import com.cda.freely.entity.User;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
     private UserRepository userRepository;
     private CompanyService companyService;
     private AddressService addressService;
     private SkillService skillService;
    private JwtTokenProvider tokenProvider;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    public UserService(UserRepository userRepository,
                       CompanyService companyService,
                       AddressService addressService,
                       SkillService skillService,
                       JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.companyService = companyService;
        this.addressService = addressService;
        this.skillService = skillService;
        this.tokenProvider = tokenProvider;
    }

    public Optional<User> findById(Long id){ return userRepository.findById(id);}
    public Optional<User> findUserByMail(String email){ return Optional.ofNullable(userRepository.findByEmail(email));}
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User saveUser(User user){return userRepository.save(user);}

    public User findUserById(Long id) {
        return userRepository.findByIdWithTags(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User checkUser(String token ){
        String username = tokenProvider.extractUserNameFromToken(token);
        logger.warn("username ----------- {}",username);
        User usernameExists = this.findByEmail(username);
        if (usernameExists == null) {
            throw new AccessDeniedException("You're not allowed to access this data");
        }
        return usernameExists;
    }

}
