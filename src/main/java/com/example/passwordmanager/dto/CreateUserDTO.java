package com.example.passwordmanager.dto;

import com.example.passwordmanager.security.validation.FieldMatch;
import com.example.passwordmanager.security.validation.ValidPassword;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

@FieldMatch.List({

        @FieldMatch( first="password",
                second = "confirmationPassword",
                message = "Passwords do not match!"),
        @FieldMatch(first="masterPassword",
                second = "confirmationMasterPassword",
                message = "Master passwords do not match!")


        })


public class CreateUserDTO {

    @NotNull
    private String email;


    @ValidPassword(message = "Password has to be 8 character long, contain minimum 3 digits, 1 uppercase letter and 1 special character")
    @NotNull
    private String password;

    @NotNull
    private String confirmationPassword;

    @ValidPassword(message = "Master password has to be 8 character long, contain minimum 3 digits, 1 uppercase letter and 1 special character")
    @NotNull
    private String masterPassword;

    @NotNull
    private String confirmationMasterPassword;


}
