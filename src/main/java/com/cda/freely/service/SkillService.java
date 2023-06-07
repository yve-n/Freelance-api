package com.cda.freely.service;

import com.cda.freely.controller.user.SkillController;
import com.cda.freely.entity.Experience;
import com.cda.freely.entity.Skill;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    private SkillRepository skillRepository;
    private final Logger logger = LoggerFactory.getLogger(SkillService.class);
    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    /**
     * find a particular skill by its id
     * @param id identifier of a skill
     * @return Skill
     */
    public Optional<Skill> findSkillById(Long id){return skillRepository.findById(id);}

    /**
     * save a skill in database
     * @param skill
     * @return a new skill
     */
    public Skill saveSkill(Skill skill){
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Skill updatedSkill, Long id){
        Skill skill = this.findSkillById(id).orElseThrow(() -> new NotFoundException("Skill not found"));

        skill.setName(updatedSkill.getName());
        skill.setDescription(updatedSkill.getDescription());
        skill.setUser(updatedSkill.getUser());
        return this.saveSkill(skill);
    }

    /**
     * delete a skill by its id
     * @param id id of skill
     */
    public void deleteSkillById(Long id){
        if (!skillRepository.existsById(id)) {
            throw new NotFoundException("Skill not found");
        }
        skillRepository.deleteById(id);
    }
    public List<Skill> getSkills(Long id){
        return skillRepository.findAllByUserId(id);
    }

}
