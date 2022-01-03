package com.example.passwordmanager.security.securityService;

import com.example.passwordmanager.dao.UserDAO;
import com.example.passwordmanager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return user;
    }
}
//