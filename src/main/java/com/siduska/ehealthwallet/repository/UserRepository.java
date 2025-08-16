package com.siduska.ehealthwallet.repository;

import com.siduska.ehealthwallet.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    boolean existsByEmail(String email);
}
