package com.cda.freely.repository;

import com.cda.freely.entity.Experience;
import com.cda.freely.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("SELECT s FROM Skill s WHERE s.user.id = :id")
    List<Skill> findAllByUserId(@Param("id") Long id);
}
