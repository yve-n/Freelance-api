package com.cda.freely.service;

import com.cda.freely.entity.Family;
import com.cda.freely.repository.FamilyRepository;
import com.cda.freely.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FamilyService {
    @Autowired
    FamilyRepository familyRepository;

    public Optional<Family> findById(Long id) {
        return familyRepository.findById(id);
    }


}
