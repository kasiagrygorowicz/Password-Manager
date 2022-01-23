package com.example.passwordmanager.service;

import com.example.passwordmanager.dto.DecryptPasswordDTO;
import com.example.passwordmanager.dto.EntryDTO;
import com.example.passwordmanager.entity.Entry;

import java.util.List;

public interface IEntryService {

    public List<Entry> GetAll(int id);

    public void add(EntryDTO entry);

    public void delete(Long id);
    public void edit(EntryDTO entry);
    public String showPassword(Long id);
    public String getWebsite(Long id);
    public EntryDTO getEntry(Long id);
    public EntryDTO decrypt(EntryDTO entry);
    public DecryptPasswordDTO getDecryptPasswordDTO(Long id);


    String decrypt(String password, String masterPassword,byte[] iv, byte[] salt);
}
