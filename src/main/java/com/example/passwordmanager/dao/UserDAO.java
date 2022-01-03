package com.example.passwordmanager.dao;

import com.example.passwordmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByid(Long id);

//    @Transactional
//    @Modifying
//    void deleteByEmail(String email);
}
