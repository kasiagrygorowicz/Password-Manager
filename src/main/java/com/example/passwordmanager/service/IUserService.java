package com.example.passwordmanager.service;

import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.dto.GetUserInfoDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import javassist.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    public GetUserInfoDTO add(CreateUserDTO user) throws SQLException;

    public Optional<User> findByEmail(String email);

    public Optional<User> getCurrent() throws NotFoundException;

   public  List<Entry> getUserPasswords() throws NotFoundException;

    public String getMasterPassword();
    void updateFailedAttempts(User user);
    void lockUser(User user);
    boolean isUserStillLocked(User user);
    void unlockUser(User user);
    void resetAttempts(User user);



}
