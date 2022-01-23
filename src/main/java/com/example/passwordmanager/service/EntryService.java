package com.example.passwordmanager.service;

import com.example.passwordmanager.dao.EntryDAO;
import com.example.passwordmanager.dto.DecryptPasswordDTO;
import com.example.passwordmanager.dto.EntryDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.mapper.EntryMapper;
import com.example.passwordmanager.security.aes.CBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

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
    public void add(EntryDTO entry) {
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
    public void edit(EntryDTO entry) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!passwordEncoder.matches(entry.getMasterPassword(),user.getMasterPassword())){
            throw new RuntimeException("Wrong master password.\n Cannot edit !!!");
        }
        Entry e = entryDAO.findById(entry.getId()).orElseThrow(
                () -> new EntityNotFoundException("You are trying to edit entry that does not exist"));

        e.setWebsite(entry.getWebsite());

        SecretKey key = CBC.getSecret(entry.getMasterPassword(), user.getSalt());
        e.setPassword(CBC.encrypt(entry.getPassword(),key, user.getIv()));
        entryDAO.save(e);

    }

    @Override
    public String getWebsite(Long id) {
        return entryDAO.getById(id).getWebsite();
    }

    @Override
    public EntryDTO getEntry(Long id) {
        return EntryMapper.mapEntryToEntryDTO(entryDAO.getById(id));
    }


    @Override
    public DecryptPasswordDTO getDecryptPasswordDTO(Long id) {
        Entry e = entryDAO.getById(id);
        return new DecryptPasswordDTO(id,e.getWebsite());
    }

    @Override
    public String decrypt(String password, String masterPassword,byte[] iv, byte[] salt)  {
        SecretKey key = null;
        String d =null;
        try {
            key = CBC.getSecret(masterPassword,salt);
            d=CBC.decrypt(password,key,iv);
        } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException(e.getMessage());
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage());
        }
        return d;

    }


}
