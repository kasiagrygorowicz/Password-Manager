package com.example.passwordmanager.dao;

import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntryDAO extends JpaRepository<Entry, Long> {


//    @Modifying
//    @Query("select e.id,e.link,e.password from entries e where e.user_id = :user_id ")
//    public List<Entry> GetAll(int user_id);

}
