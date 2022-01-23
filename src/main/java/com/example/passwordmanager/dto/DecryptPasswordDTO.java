package com.example.passwordmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DecryptPasswordDTO {

    private String website;
    private String masterPassword;
//    entry id
    private Long id;

    public DecryptPasswordDTO(Long id,String website){
        this.id=id;
        this.website =website;
    }
}
