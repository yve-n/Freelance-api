package com.cda.freely.service;

import com.cda.freely.entity.Family;
import com.cda.freely.repository.FamilyRepository;
import com.cda.freely.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FamilyService {

    private FamilyRepository familyRepository;
    @Autowired
    public FamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    /**
     * find a Family
     * @param id id of the family to get
     * @return Family
     */
    public Optional<Family> findById(Long id) {
        return familyRepository.findById(id);
    }


}
