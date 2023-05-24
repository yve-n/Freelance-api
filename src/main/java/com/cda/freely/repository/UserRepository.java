package com.cda.freely.repository;

import com.cda.freely.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.tags WHERE u.id = :id")
    Optional<User> findByIdWithTags(@Param("id") Long id);
    User findByEmail(String email);
}
