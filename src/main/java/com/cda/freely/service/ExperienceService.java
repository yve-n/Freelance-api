package com.cda.freely.service;

import com.cda.freely.dto.experience.ExperienceDTO;
import com.cda.freely.entity.Experience;
import com.cda.freely.entity.User;
import com.cda.freely.exception.GlobalExceptionHandler;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.repository.ExperienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    /**
     * create an experience
     * @param experienceDTO
     * @return Experience
     */
    public Experience addExperience(ExperienceDTO experienceDTO){
            User user = userService.findById(experienceDTO.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found"));
            Experience experience = new Experience();
            experience.setTitle(experienceDTO.getTitle());
            experience.setDescription(experienceDTO.getDescription());
            experience.setStartedAt(experienceDTO.getStartedAt());
            experience.setEndedAt(experienceDTO.getEndedAt());
            experience.setUser(user);
        return experienceRepository.save(experience);
    }

    public Experience updateExperience(ExperienceDTO updatedExperienceDTO, Long id){
        Experience experience = this.findExpById(id).orElseThrow(() -> new NotFoundException("Experience not found"));
        User user = userService.findById(updatedExperienceDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        experience.setTitle(updatedExperienceDTO.getTitle());
        experience.setDescription(updatedExperienceDTO.getDescription());
        experience.setStartedAt(updatedExperienceDTO.getStartedAt());
        experience.setEndedAt(updatedExperienceDTO.getEndedAt());
        experience.setUser(user);
        return experienceRepository.save(experience);
    }

    public Experience saveExperience(Experience experience){
        return experienceRepository.save(experience);
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
