package com.cda.freely.service.admin;

import com.cda.freely.entity.Contact;
import com.cda.freely.entity.User;
import com.cda.freely.repository.UserRepository;
import com.cda.freely.service.ContactService;
import com.cda.freely.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminService {

    private UserRepository userRepository;
    private ContactService contactService;

    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public AdminService(UserRepository userRepository, ContactService contactService) {
        this.userRepository = userRepository;
        this.contactService = contactService;
    }

    public List<User> getUsersWithPendingAccountState(){
        return userRepository.findUsersWithPendingAccountState();
    }
    public List<Contact> getContacts(){return contactService.findAllContacts();}

    public List<User> getUsers(){return userRepository.findAll();
    }

}
