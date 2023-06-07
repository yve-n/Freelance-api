package com.cda.freely.controller.user;

import com.cda.freely.entity.Contact;
import com.cda.freely.entity.Experience;
import com.cda.freely.service.ContactService;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private ContactService contactService;
    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("")
    @JsonView({Views.Contact.class})
    public ResponseEntity<?> addContact(@RequestBody Contact contact){
        logger.warn("contact ----------- {}",contact.toString());
        Contact newContact = contactService.saveContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(newContact);
    }

}
