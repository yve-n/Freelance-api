package com.cda.freely.repository;

import com.cda.freely.entity.Skill;
import com.cda.freely.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {
    @Query("SELECT t FROM Training t WHERE t.user.id = :id")
    List<Training> findAllByUserId(@Param("id") Long id);
}
