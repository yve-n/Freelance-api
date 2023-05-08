package com.cda.freely.repository;

import com.cda.freely.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTagRepository extends JpaRepository<UserTag, UserTag.KeyUserTag> {
    Optional<UserTag> findByIdUserTag_IdUserAndIdUserTag_IdTag(Integer id_user, Integer id_tag);
}


