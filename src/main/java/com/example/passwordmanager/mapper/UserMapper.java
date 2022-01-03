package com.example.passwordmanager.mapper;

import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.dto.GetUserInfoDTO;
import com.example.passwordmanager.entity.User;

public class UserMapper {

    public static User CreateUserDTOToUser(CreateUserDTO u) {
        return new User(u.getUsername(), u.getPassword(), u.getMasterPassword());
    }

    public static GetUserInfoDTO UserToGetUserInfoDTO(User u) {
        return new GetUserInfoDTO(u.getUsername());
    }
}
