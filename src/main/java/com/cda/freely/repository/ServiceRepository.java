package com.cda.freely.repository;

import com.cda.freely.entity.Service;
import com.cda.freely.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("SELECT s FROM Service s WHERE s.user.id = :id")
    List<Service> findAllByUserId(@Param("id") Long id);
}
