package com.cda.freely.repository;

import com.cda.freely.entity.Experience;
import com.cda.freely.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    @Query("SELECT e FROM Experience e WHERE e.user.id = :id")
    List<Experience> findAllByUserId(@Param("id") Long id);

}
