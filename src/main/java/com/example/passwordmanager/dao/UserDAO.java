package com.example.passwordmanager.dao;

import com.example.passwordmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface UserDAO extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Transactional
    @Modifying
    void deleteByEmail(String email);
}
