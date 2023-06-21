package com.cda.freely.service;

import com.cda.freely.entity.Experience;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.repository.ExperienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceService {
    private final Logger logger = LoggerFactory.getLogger(ExperienceService.class);
    private ExperienceRepository experienceRepository;
    private UserService userService;
    @Autowired
    public ExperienceService(ExperienceRepository experienceRepository,
                             UserService userService) {
        this.experienceRepository = experienceRepository;
        this.userService = userService;
    }

    /**
     * find an experience
     * @param id
     * @return Experience
     */
    public Optional<Experience> findExpById(Long id){
        return experienceRepository.findById(id);
    }

    public Experience updateExperience(Experience updatedExperience, Long id){
        Experience experience = this.findExpById(id).orElseThrow(() -> new NotFoundException("Experience not found"));

        experience.setTitle(updatedExperience.getTitle());
        experience.setDescription(updatedExperience.getDescription());
        experience.setStartedAt(updatedExperience.getStartedAt());
        experience.setEndedAt(updatedExperience.getEndedAt());
        experience.setUser(updatedExperience.getUser());
        return experienceRepository.save(experience);
    }

    /**
     * create an experience
     * @param experience
     * @return Experience
     */
    public Experience saveExperience(Experience experience){
        return experienceRepository.save(experience);
    }

    public List<Experience> getExperiences(Long id){
        return experienceRepository.findAllByUserId(id);
    }

    /**
     * delete an experience
     * @param id
     */
    public void deleteExpById(Long id){
        if (!experienceRepository.existsById(id)) {
            throw new NotFoundException("Experience not found");
        }
        experienceRepository.deleteById(id);
    }

}
