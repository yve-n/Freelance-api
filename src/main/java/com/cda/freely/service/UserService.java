package com.cda.freely.service;

import com.cda.freely.entity.User;
import com.cda.freely.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
     private UserRepository userRepository;
     private CompanyService companyService;
     private AddressService addressService;
     private SkillService skillService;

     @Autowired
    public UserService(UserRepository userRepository, CompanyService companyService, AddressService addressService, SkillService skillService) {
        this.userRepository = userRepository;
        this.companyService = companyService;
        this.addressService = addressService;
        this.skillService = skillService;
    }

    public Optional<User> findById(Long id){ return userRepository.findById(id);}
    public Optional<User> findUserByMail(String email){ return Optional.ofNullable(userRepository.findByEmail(email));}
    public User saveUser(User user){return userRepository.save(user);}


}
