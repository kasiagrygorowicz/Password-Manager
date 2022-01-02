package com.example.passwordmanager.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,unique=true,length=50)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false,length=60,name="master_password")
    private String masterPassword;

    @OneToMany(mappedBy = "user")
    private List<Entry> entries = null;

    @Column(nullable = false, name="is_active")
    private boolean isActive;

    @Column(name="lock_time")
    private Date lockTime;


    public User(String email, String password,String masterPassword){
        this.email=email;
        this.password=password;
        this.masterPassword=masterPassword;
        this.isActive=true;
        this.lockTime=null;

    }

    public boolean getIsActive() {
        return isActive;
    }
}
