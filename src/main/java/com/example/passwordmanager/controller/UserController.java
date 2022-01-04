package com.example.passwordmanager.controller;

import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.service.IEntryService;
import com.example.passwordmanager.service.IUserService;
import javassist.NotFoundException;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value="/")
public class UserController {

    @Autowired
    private IUserService userService;




    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute("user", new CreateUserDTO());
        return "registration";
    }

    @PostMapping("/register")
    public String registerNewAccount(@Valid @ModelAttribute("user") CreateUserDTO user, BindingResult bindingResult, Model model, RedirectAttributes attributes) throws SQLException {

        if (bindingResult.hasErrors()) {
            return "registration";

        } else {
            try {
                userService.add(user);
            }catch(RuntimeException e){
                model.addAttribute("errorMessage",e.getMessage());
                return "registration";
            }
            attributes.addFlashAttribute("success","New account registered successfully");
            return "redirect:/login";
        }
    }

    @GetMapping("login")
    public String displayLoginForm(Model model){
        model.addAttribute("user", new CreateUserDTO());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }


    @GetMapping("/dashboard")
    public String displayEntriesList(Model model) throws NotFoundException {
        List<Entry> l = userService.getUserPasswords();
        model.addAttribute("passwords", l);

        return "dashboard";

    }








}
