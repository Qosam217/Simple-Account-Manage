package com.demo.simple_account.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.simple_account.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByUsername(String username);

    Boolean existsByUsername(String username);
}
