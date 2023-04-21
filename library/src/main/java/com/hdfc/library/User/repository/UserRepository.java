package com.hdfc.library.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.library.User.entity.User;

@RestController
public interface UserRepository extends JpaRepository<User, Long> {
    
}
