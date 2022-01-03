package com.example.passwordmanager.service;

import com.example.passwordmanager.entity.Entry;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEntryService {

    public List<Entry> GetAll(int id);

    public void add(String link, String password, int id);

    public void delete(Long id);

}
