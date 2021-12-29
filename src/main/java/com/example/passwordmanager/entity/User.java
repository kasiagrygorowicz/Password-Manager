package com.example.passwordmanager.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "user")
    private List<Entry> entries = null;

    public User(String email, String password){
        this.email=email;
        this.password=password;
    }

}
