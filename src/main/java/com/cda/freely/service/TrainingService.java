package com.cda.freely.service;

import com.cda.freely.entity.Skill;
import com.cda.freely.entity.Training;
import com.cda.freely.repository.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingService {
    private TrainingRepository trainingRepository;
    private final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    @Autowired
    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    /**
     * find a particular training
     * @param id identifier of a training
     * @return Training
     */
    public Optional<Training> findTrainingById(Integer id){
        return trainingRepository.findById(id);
    }

    /**
     * save a training in database
     * @param training
     * @return save training
     */
    public Training saveTraining(Training training){
        return trainingRepository.save(training);
    }

    /**
     * delete a particular training
     * @param id training's id
     */
    public void deleteTrainingbyId(Integer id){
        trainingRepository.deleteById(id);
    }

}
