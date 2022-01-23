package com.example.passwordmanager.dto;

import com.example.passwordmanager.security.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor()
public class EntryDTO {
    private String website;
    private String password;
    private String masterPassword;
    private Long id;


    public EntryDTO( String website,Long id){
        this.id = id;
        this.website=website;

    }

    public EntryDTO( String website,Long id,String password){
        this.id = id;
        this.website=website;
        this.password=password;

    }
}


