package com.example.passwordmanager.service;

import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.dto.GetUserInfoDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import javassist.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {

    GetUserInfoDTO add(CreateUserDTO user) throws SQLException;

    User findByEmail(String email) throws ChangeSetPersister.NotFoundException, NotFoundException;

    User getCurrent() throws NotFoundException;

    List<Entry> getUserPasswords() throws NotFoundException;

    String getMasterPassword();
}
