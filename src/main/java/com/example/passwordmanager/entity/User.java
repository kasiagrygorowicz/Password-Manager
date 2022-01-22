package com.example.passwordmanager.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 60, name = "master_password")
    private String masterPassword;

    @OneToMany(mappedBy = "user")
    private List<Entry> entries = null;

    @Column(nullable = false, name = "is_active")
    private boolean isActive;

    @Column(name = "lock_time")
    private Date lockTime;

    @Column(name = "salt")
    private byte[] salt;

    @Column(name = "iv")
    private byte[] iv;

    @Column(name = "login_attempts")
    private int loginAttempts;




    public User(String username, String password, String masterPassword) {
        this.username = username;
        this.password = password;
        this.masterPassword = masterPassword;
        this.isActive = true;
        this.lockTime = null;
        this.loginAttempts =0;
        this.salt=null;
        this.iv=null;


    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return new HashSet<GrantedAuthority>();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
