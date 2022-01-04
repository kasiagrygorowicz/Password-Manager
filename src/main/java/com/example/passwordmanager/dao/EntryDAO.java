package com.example.passwordmanager.dao;

import com.example.passwordmanager.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryDAO extends JpaRepository<Entry, Long> {


    void deleteById(Long id);
}
