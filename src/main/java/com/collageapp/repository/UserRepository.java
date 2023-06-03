package com.collageapp.repository;

import com.collageapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//step no 2 user repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findByEmail(String email);
    Optional<User>findByUsernameOrEmail(String username,String email);
    Optional<User>findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
