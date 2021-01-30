package com.literarnoudruzenje.repository;

import com.literarnoudruzenje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findOneById(Long id);

    List<User> findAllByType(String type);

    @Query(value = "select u from User u")
    List<User> findAllUsers();
}
