package com.cda.freely.service;

import com.cda.freely.entity.Experience;
import com.cda.freely.repository.ExperienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExperienceService {
    private final Logger logger = LoggerFactory.getLogger(ExperienceService.class);
    private ExperienceRepository experienceRepository;
    @Autowired
    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    /**
     * find an experience
     * @param id
     * @return Experience
     */
    public Optional<Experience> findExpById(Long id){
        return experienceRepository.findById(id);
    }

    /**
     * save an experience
     * @param experience
     * @return Experience
     */
    public Experience saveExperience(Experience experience){
        return experienceRepository.save(experience);
    }

    /**
     * delete an experience
     * @param id
     */
    public void deleteExpById(Long id){
        experienceRepository.deleteById(id);
    }

}
