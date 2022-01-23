package com.example.passwordmanager.service;

import com.example.passwordmanager.dao.UserDAO;
import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.dto.GetUserInfoDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.mapper.UserMapper;

import com.example.passwordmanager.security.aes.CBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javassist.NotFoundException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    public static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_TIME = 24 * 60 * 60 * 1000;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public GetUserInfoDTO add(CreateUserDTO user) throws SQLException {
        if (userDAO.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("This user already exists");
        }
        User u = UserMapper.CreateUserDTOToUser(user);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setMasterPassword(passwordEncoder.encode(u.getMasterPassword()));
        u.setLoginAttempts(0);
        u.setIv(CBC.generateIV());
        u.setSalt(CBC.generateSalt());
        userDAO.save(u);

        return UserMapper.UserToGetUserInfoDTO(u);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userDAO.findByUsername(email);
        return user;
    }



    @Override
    public Optional<User> getCurrent() throws NotFoundException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = null;
        User user;
        if (null != securityContext.getAuthentication()) {

                user = (User) securityContext.getAuthentication().getPrincipal();
                username = user.getUsername();


        }else{
            throw new RuntimeException("Could not find user");
        }
        return findByEmail(username);
    }

    @Override
    public List<Entry> getUserPasswords() {
        Optional<User> current = null;
        try {
            current = getCurrent();
            return current.get().getEntries();

        } catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public String getMasterPassword() {

        Optional<User> current = null;
        try {
            current = getCurrent();
            return current.get().getMasterPassword();
        } catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateFailedAttempts(User user){
        int attempts = user.getLoginAttempts();
        user.setLoginAttempts(attempts+1);
        userDAO.save(user);
    }

    @Override
    public void lockUser(User user){
        user.setActive(false);
        user.setLockTime(new Date());
        userDAO.save(user);
    }

    @Override
    public boolean isUserStillLocked(User user){
        long current = System.currentTimeMillis();
        System.out.println(current);

        long time = user.getLockTime().getTime();
        System.out.println(time);
        return time + LOCK_TIME > current;

    }

    @Override
    public void unlockUser(User user){
        user.setLockTime(null);
        user.setLoginAttempts(0);
        user.setActive(true);
        userDAO.save(user);
    }

    @Override
    public void resetAttempts(User user){
        user.setLoginAttempts(0);
        user.setActive(true);
        user.setLockTime(null);
        userDAO.save(user);
    }




}
