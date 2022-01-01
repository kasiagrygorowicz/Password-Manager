package com.example.passwordmanager.service;

import com.example.passwordmanager.dao.UserDAO;
import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.dto.GetUserInfoDTO;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javassist.NotFoundException;
import java.sql.SQLException;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public GetUserInfoDTO add(CreateUserDTO user) throws SQLException {
        if(userDAO.findByEmail(user.getEmail())!=null){
            throw new RuntimeException("This user already exists");
        }

        User u = UserMapper.CreateUserDTOToUser(user);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setMasterPassword(passwordEncoder.encode(u.getMasterPassword()));
        userDAO.save(u);

        return UserMapper.UserToGetUserInfoDTO(u);
    }

    @Override
    public void deleteByEmail(String email) throws NotFoundException {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("Account with email:  " + email + " does not exist");
        }
        userDAO.deleteByEmail(email);

    }

    @Override
    public GetUserInfoDTO findByEmail(String email) throws NotFoundException {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("Account with email:  " + email + " does not exist");
        }
        return UserMapper.UserToGetUserInfoDTO(user);
    }


}
