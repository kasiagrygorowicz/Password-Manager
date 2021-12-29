package com.example.passwordmanager.service;

import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.dto.GetUserInfoDTO;
import javassist.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.sql.SQLException;

public interface IUserService {

    GetUserInfoDTO add(CreateUserDTO user) throws SQLException;
    void deleteByEmail(String email) throws ChangeSetPersister.NotFoundException, NotFoundException;
    GetUserInfoDTO findByEmail(String email) throws ChangeSetPersister.NotFoundException, NotFoundException;
}
