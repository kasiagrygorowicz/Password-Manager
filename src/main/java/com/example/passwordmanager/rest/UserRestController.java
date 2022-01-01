package com.example.passwordmanager.rest;

import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping(value="/")
public class UserRestController {

    @Autowired
    private IUserService userService;



    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute("user", new CreateUserDTO());
        return "registration";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model){
        model.addAttribute("user", new CreateUserDTO());
        return "login";
    }


    @PostMapping("/register")
    public String registerNewAccount(@Valid @ModelAttribute("user") CreateUserDTO user, BindingResult bindingResult,Model model) throws SQLException {

        if (bindingResult.hasErrors()) {
            return "registration";

        } else {
            try {
                userService.add(user);
            }catch(RuntimeException e){
                model.addAttribute("errorMessage",e.getMessage());
                return "registration";
            }
            return "user-dashboard";
        }

}




}
