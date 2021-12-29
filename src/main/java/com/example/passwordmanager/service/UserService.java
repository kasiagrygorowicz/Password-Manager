package com.example.passwordmanager.service;

import com.example.passwordmanager.dao.UserDAO;
import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.dto.GetUserInfoDTO;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.mapper.UserMapper;

import org.springframework.stereotype.Service;
import javassist.NotFoundException;
import java.sql.SQLException;

@Service
public class UserService implements IUserService {

    private UserDAO userDAO;

    @Override
    public GetUserInfoDTO add(CreateUserDTO user) throws SQLException {
        User u = userDAO.save(UserMapper.CreateUserDTOToUser(user));
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
