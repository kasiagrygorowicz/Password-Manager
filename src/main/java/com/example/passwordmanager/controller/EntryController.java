package com.example.passwordmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntryController {

    @GetMapping("/addEntry")
    public String displayNewEntryForm(){
        return "entry/new-entry-form";
    }


}
