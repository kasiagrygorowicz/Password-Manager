package com.example.passwordmanager.rest;

import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping(value="/")
public class UserRestController {

    private IUserService userService;

    @GetMapping("/registrationForm")
    public String displayRegistrationForm(Model model){
        CreateUserDTO user = new CreateUserDTO();
        model.addAttribute("user",user);
        return "registration";
    }


    @PostMapping("/register")
    public String registerNewAccount(@ModelAttribute("user") CreateUserDTO user) throws SQLException {
//        userService.add(user);
        System.out.println(user.getEmail());
        return "user-dashboard";

    }


}
