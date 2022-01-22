package com.example.passwordmanager.entity;

import com.example.passwordmanager.security.validation.ValidPassword;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String website;

//    @ValidPassword(message = "Password has to be 8 character long, contain minimum 3 digits, 1 uppercase letter and 1 special character")
    @Column(nullable = false, unique = true)
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
