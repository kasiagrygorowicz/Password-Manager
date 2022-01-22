package com.example.passwordmanager.dto;

import com.example.passwordmanager.security.validation.ValidPassword;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditEntryDTO {
    private String website;
    private String password;
    private String masterPassword;
    private Long id;

    public EditEntryDTO(Long id){
        this.id=id;
    }
}


