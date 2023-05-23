package com.cda.freely.service;

import com.cda.freely.entity.Contact;
import com.cda.freely.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {
    private final Logger logger = LoggerFactory.getLogger(ContactService.class);
    private ContactRepository contactRepository;
    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * find a contact
     * @param id
     * @return Contact
     */
    public Optional<Contact> findContactById(Long id){
        return contactRepository.findById(id);
    }

    /**
     * save a contact
     * @param contact
     * @return Contact
     */
    public Contact saveContact(Contact contact){
        return contactRepository.save(contact);
    }

    /**
     * delete a contact
     * @param id
     */
    public void deleteContactById(Long id){
        contactRepository.deleteById(id);
    }
}
