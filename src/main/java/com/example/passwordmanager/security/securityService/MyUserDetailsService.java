package com.example.passwordmanager.security.securityService;

import com.example.passwordmanager.dao.UserDAO;
import com.example.passwordmanager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userDAO.findByUsername(email);
        if (user.isPresent()) {
            return user.get();

        }
        throw new UsernameNotFoundException(email);
    }
}
//