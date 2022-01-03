package com.example.passwordmanager.service;

import com.example.passwordmanager.dao.EntryDAO;
import com.example.passwordmanager.entity.Entry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryService implements IEntryService{

    private EntryDAO entryDAO;

    @Override
    public List<Entry> GetAll(int id) {
        return null;
    }

    @Override
    public void add(String link, String password, int id) {

    }



    @Override
    public void delete(Long id) {

    }
}
