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


    @PostMapping("/register")
    public String registerNewAccount(@Valid @ModelAttribute("user") CreateUserDTO user, BindingResult bindingResult) throws SQLException {
//        userService.add(user);
//        System.out.println(user.getEmail());

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldErrors());
            System.out.println(bindingResult.getErrorCount());
//            System.out.println(bindingResult.get);
            return "registration";

        } else {
            return "user-dashboard";
        }


}


}
