package com.example.retroboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.retroboard.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
