package com.cda.freely.service;


import com.cda.freely.entity.Achievement;
import com.cda.freely.repository.AchievementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AchievementService {
    private final Logger logger = LoggerFactory.getLogger(AchievementService.class);
    private AchievementRepository achievementRepository;
    @Autowired
    public AchievementService(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    /**
     * find an achievement
     * @param id
     * @return Achievement
     */
    public Optional<Achievement> findAchieveById(Long id){
        return achievementRepository.findById(id);
    }

    /**
     * save an achievement
     * @param achievement
     * @return Achievement
     */
    public Achievement saveAchievement(Achievement achievement){
        return achievementRepository.save(achievement);
    }

    /**
     * delete an achievement
     * @param id
     */
    public void deleteAchieveById(Long id){
        achievementRepository.deleteById(id);
    }
}
