package com.example.passwordmanager.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CreateUserDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String confirmationPassword;
}
