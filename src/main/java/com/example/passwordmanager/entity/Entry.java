package com.example.passwordmanager.entity;

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

    @Column(nullable = false, unique = true)
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
