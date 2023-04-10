package com.cda.freely.repository;

import com.cda.freely.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, UserCategory.KeyUserCategory> {
    Optional<UserCategory> findByUserCategoryId_IdUserAndUserCategoryId_IdCategory(Integer id_user , Integer id_category);
}
