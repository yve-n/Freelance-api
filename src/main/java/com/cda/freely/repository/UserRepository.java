package com.cda.freely.repository;

import com.cda.freely.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userAccountState = 'PENDING'")
    List<User> findUsersWithPendingAccountState();

    @Query("SELECT u FROM User u WHERE u.userAccountState = 'APPROVED'")
    List<User> findUsersWithApprovedAccountState();

    @Query("SELECT u FROM User u WHERE u.userAccountState = 'DECLINED'")
    List<User> findUsersWithDeclinedAccountState();

    @Query("SELECT u FROM User u WHERE u.userAccountState = 'DELETE'")
    List<User> findUsersWithDeletedAccountState();

    @Query("SELECT u FROM User u WHERE u.userAccountState = 'PENDING' AND u.role = 'ROLE_USER'")
    List<User> findUsersWithPendingAccountStateAndUserRole();

    @Query("SELECT u FROM User u JOIN FETCH u.tags WHERE u.id = :id")
    Optional<User> findByIdWithTags(@Param("id") Long id);
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.portfolioLink = :portfolioLink")
    Optional<User> findByPortfolioLink(@Param("portfolioLink") String portfolioLink);
}
