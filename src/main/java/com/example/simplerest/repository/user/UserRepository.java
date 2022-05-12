package com.example.simplerest.repository.user;

import com.example.simplerest.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}