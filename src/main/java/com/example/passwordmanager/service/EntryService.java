package com.example.passwordmanager.service;

import com.example.passwordmanager.dao.EntryDAO;
import com.example.passwordmanager.dto.EditEntryDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EntryService implements IEntryService {

    @Autowired
    private EntryDAO entryDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Entry> GetAll(int id) {
        return null;
    }

    @Override
    public void add(Entry entry) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        entry.setUser(user);
        entryDAO.save(entry);
    }


    @Override
    public void delete(Long id) {
        entryDAO.deleteById(id);
    }

    @Override
    public void edit(EditEntryDTO entry) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!passwordEncoder.matches(entry.getMasterPassword(),user.getMasterPassword())){
            throw new RuntimeException("Wrong master password.\n Cannot edit !!!");
        }
        Entry e = entryDAO.findById(entry.getId()).orElseThrow(
                () -> new EntityNotFoundException("You are trying to edit entry that does not exist"));

        e.setWebsite(entry.getWebsite());
        e.setPassword(entry.getPassword());
        entryDAO.save(e);

    }
}
