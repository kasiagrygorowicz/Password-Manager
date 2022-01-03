package com.example.passwordmanager.rest;

import com.example.passwordmanager.dto.CreateUserDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.service.IEntryService;
import com.example.passwordmanager.service.IUserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value="/")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IEntryService entryService;


    @GetMapping("/list")
    public String displayEntriesList(Model model) throws NotFoundException {
        List<Entry> l = userService.getUserPasswords();
        for (Entry e : l)
            System.out.println(e.getId()+e.getWebsite()+e.getPassword());
        model.addAttribute("passwords", l);

        return "user-dashboard";

    }

@GetMapping("/dashboard")
public String displayUserDashboard(){

    return "user-dashboard";
}
    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute("user", new CreateUserDTO());
        return "registration";
    }

    @GetMapping("login")
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
