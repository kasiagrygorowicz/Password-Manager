package com.example.passwordmanager.service;

import com.example.passwordmanager.dao.UserDAO;
import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.dto.GetUserInfoDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javassist.NotFoundException;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public GetUserInfoDTO add(CreateUserDTO user) throws SQLException {
        if(userDAO.findByUsername(user.getUsername())!=null){
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
        User user = userDAO.findByUsername(email);
        if (user == null) {
            throw new NotFoundException("Account with email:  " + email + " does not exist");
        }
//        userDAO.deleteByEmail(email);

    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        User user = userDAO.findByUsername(email);
        if (user == null) {
            throw new NotFoundException("Account with email:  " + email + " does not exist");
        }
         return user;
    }

    @Override
    public User findById(int id) {
        return userDAO.findByid((long) id);
    }


    @Override
    public User getCurrent() throws NotFoundException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = "";
        User principal;
        if (null != securityContext.getAuthentication()) {
            if (securityContext.getAuthentication().getPrincipal().getClass() == String.class) {
                username = (String) securityContext.getAuthentication().getPrincipal();

            } else {
                principal = (User) securityContext.getAuthentication().getPrincipal();
                username = principal.getUsername();

            }
        }
        return findByEmail(username);
    }

//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Entry> getUserPasswords() throws NotFoundException {
        User current = getCurrent();
        return (List<Entry>) current.getEntries();
    }
}
