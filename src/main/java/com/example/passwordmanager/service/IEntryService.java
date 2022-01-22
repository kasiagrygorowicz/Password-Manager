package com.example.passwordmanager.service;

import com.example.passwordmanager.dto.EditEntryDTO;
import com.example.passwordmanager.entity.Entry;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEntryService {

    public List<Entry> GetAll(int id);

    public void add(EditEntryDTO entry);

    public void delete(Long id);
    public void edit(EditEntryDTO entry);
    public String showPassword(Long id);
    public String getWebsite(Long id);

}
