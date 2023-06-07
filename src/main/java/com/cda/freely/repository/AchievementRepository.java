package com.cda.freely.repository;

import com.cda.freely.entity.Achievement;
import com.cda.freely.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    @Query("SELECT a FROM Achievement a WHERE a.experience.id = :id")
    List<Achievement> findAllByExperienceId(@Param("id") Long id);
}
