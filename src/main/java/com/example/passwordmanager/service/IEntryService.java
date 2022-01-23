package com.example.passwordmanager.service;

import com.example.passwordmanager.dto.DecryptPasswordDTO;
import com.example.passwordmanager.dto.EntryDTO;
import com.example.passwordmanager.entity.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface IEntryService {

    public List<Entry> GetAll(int id);

    public void add(EntryDTO entry);

    public void delete(Long id);

    public void edit(EntryDTO entry) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

    public String getWebsite(Long id);

    public EntryDTO getEntryDTO(Long id);

    public DecryptPasswordDTO getDecryptPasswordDTO(Long id);
    Entry getEntry(Long id);


    String decrypt(String password, String masterPassword, byte[] iv, byte[] salt);
}
