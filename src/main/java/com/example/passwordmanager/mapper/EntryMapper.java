package com.example.passwordmanager.mapper;

import com.example.passwordmanager.dto.EntryDTO;
import com.example.passwordmanager.entity.Entry;

public class EntryMapper {

    public static EntryDTO mapEntryToEntryDTO(Entry entry){
        return new EntryDTO(entry.getWebsite(),entry.getId(), entry.getPassword());
    }
}
