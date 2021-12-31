package com.example.passwordmanager.dto;

import com.example.passwordmanager.security.password.FieldMatch;
import com.example.passwordmanager.security.password.FieldsValueMatch;
import com.example.passwordmanager.security.password.ValidPassword;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

@FieldsValueMatch(
        field = "password",
        fieldMatch = "confirmationPassword",
        message = "Passwords do not match!"
)


public class CreateUserDTO {

    @NotNull
    private String email;


    //    @ValidPassword
    @NotNull
    private String password;

    @NotNull
    private String confirmationPassword;

    //    @ValidPassword(message = "Master passsword has to be 8 character long, contain minimum 3 digits, 1 uppercase letter and 1 special character")
    @NotNull
    private String masterPassword;

    @NotNull
    private String confirmationMasterPassword;


}
