package com.cda.freely.service;

import com.cda.freely.entity.History;
import com.cda.freely.repository.HistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {
    private final Logger logger = LoggerFactory.getLogger(HistoryService.class);
    private HistoryRepository historyRepository;
    @Autowired
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    /**
     * find a history
     * @param id
     * @return History
     */
    public Optional<History> findHistoryById(Long id){
        return historyRepository.findById(id);
    }

    /**
     * save a history
     * @param history
     * @return History
     */
    public History saveHistory(History history){
        return historyRepository.save(history);
    }

    /**
     * delete a history
     * @param id
     */
    public void deleteHistoryById(Long id){
        historyRepository.deleteById(id);
    }

    public List<History> getHistories(){return historyRepository.findAll();}
}
