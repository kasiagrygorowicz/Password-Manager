package com.example.passwordmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DecryptPasswordDTO {

    private String website;
    private String masterPassword;
//    entry id
    private Long id;

    public DecryptPasswordDTO(Long id,String website){
        this.id=id;
        this.website =website;
    }

    public DecryptPasswordDTO(String website,String masterPassword){
        this.masterPassword=masterPassword;
        this.website =website;
    }
}
