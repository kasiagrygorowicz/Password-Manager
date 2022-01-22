package com.example.passwordmanager.service;

import com.example.passwordmanager.dao.EntryDAO;
import com.example.passwordmanager.dto.EditEntryDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.security.aes.CBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
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
    public void add(EditEntryDTO entry) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!passwordEncoder.matches(entry.getMasterPassword(),user.getMasterPassword())){
            throw new RuntimeException("Wrong master password.\n Cannot add entry !!!");
        }
        Entry newEntry = new Entry();
        byte [] salt = user.getSalt();
        String cipherPassword = null;
        try {
            SecretKey key = CBC.getSecret(entry.getMasterPassword(),salt);
            cipherPassword = CBC.encrypt(entry.getPassword(),key,user.getIv());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException | UnsupportedEncodingException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e.getMessage());
        }

        newEntry.setPassword(cipherPassword);
        newEntry.setUser(user);
        newEntry.setWebsite(entry.getWebsite());

        entryDAO.save(newEntry);


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

    @Override
    public String showPassword(Long id) {
        Entry e  = entryDAO.getById(id);
//        SecretKey = CBC.getSecret(e.get)
        return "elo";
    }

    @Override
    public String getWebsite(Long id) {
        return entryDAO.getById(id).getWebsite();
    }


}
