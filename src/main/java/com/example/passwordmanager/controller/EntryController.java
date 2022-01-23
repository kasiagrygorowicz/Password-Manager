package com.example.passwordmanager.controller;

import com.example.passwordmanager.dto.DecryptPasswordDTO;
import com.example.passwordmanager.dto.EntryDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.service.IEntryService;
import com.example.passwordmanager.service.IUserService;
import com.example.passwordmanager.service.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class EntryController {

    @Autowired
    private IValidationService validationService;

    @Autowired
    private IEntryService entryService;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("dashboard/addEntry")
    public String displayNewEntryForm(Model model) {

        model.addAttribute("entry", new EntryDTO());
        return "entry/new-entry-form";
    }

    @PostMapping("dashboard/addEntry")
    public String processNewEntryForm(@ModelAttribute("entry") EntryDTO entry, RedirectAttributes attributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "redirect:/dashboard/addEntry";
        } else {

            try {
                entryService.add(entry);

            } catch (Exception e) {
                attributes.addFlashAttribute("\"error\"", e.getMessage());
            }
            attributes.addFlashAttribute("success", "New password was added successfully");
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/dashboard/show/{id}")
    public String showPassword(@PathVariable Long id, Model model,RedirectAttributes attributes) {
        if(!validationService.resourceBelongsToUser(id)){
            attributes.addFlashAttribute("error","You have tried to access someone else's account !!!");
            return "redirect:/dashboard";
        }
        DecryptPasswordDTO e = entryService.getDecryptPasswordDTO(id);

        model.addAttribute("entry",e);
        model.addAttribute("isDecrypted", false);

        return "entry/show-password-form";
    }

    @PostMapping("/dashboard/show/{id}")
    public String showPassword(@ModelAttribute("entry") DecryptPasswordDTO entry, RedirectAttributes attributes, Model model, @PathVariable Long id) {
        if(!validationService.resourceBelongsToUser(id)){
            attributes.addFlashAttribute("error","You have tried to access someone else's account !!!");
            return "redirect:/dashboard";
        }
        if (!passwordEncoder.matches(entry.getMasterPassword(),userService.getMasterPassword())) {
            attributes.addFlashAttribute("error", "Master password not correct");
            return "redirect:/dashboard/show/"+id;
        }
            String decrypted = null;
        try{
            String password = entryService.getEntryDTO(entry.getId()).getPassword();
            Optional<User> u = userService.getCurrent();
            if(u.isPresent()){
                decrypted = entryService.decrypt(password,entry.getMasterPassword(),u.get().getIv(),u.get().getSalt());
            }else{
                throw new RuntimeException("Current user not found");
            }


        }catch(Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard/show/"+id;
        }
        DecryptPasswordDTO d = new DecryptPasswordDTO(entryService.getWebsite(id),decrypted,id);
        model.addAttribute("isDecrypted", true);
       model.addAttribute("decryptedEntry",d);
        return "entry/show-password-form";
    }


    @RequestMapping("/dashboard/delete/{id}")
    public String deleteEntry(@PathVariable Long id,RedirectAttributes attributes) {
        if(!validationService.resourceBelongsToUser(id)){
            attributes.addFlashAttribute("error","You have tried to access someone else's account !!!");
            return "redirect:/dashboard";
        }
        entryService.delete(id);
        return "redirect:/dashboard";
    }

    @GetMapping("dashboard/edit/{id}")
    public String displayEditForm(@PathVariable Long id, Model model,RedirectAttributes attributes) {
        if(!validationService.resourceBelongsToUser(id)){
            attributes.addFlashAttribute("error","You have tried to access someone else's account !!!");
            return "redirect:/dashboard";
        }
        Entry e = (Entry) model.getAttribute("psw");
        System.out.println(e.getId());
        model.addAttribute("entry", new EntryDTO(entryService.getWebsite(id),id));
        return "entry/edit-form";
    }

    @PostMapping("dashboard/edit/")
    public String processEditEntry(@Valid @ModelAttribute("entry") EntryDTO entry, Model model,RedirectAttributes attributes) {
        try {
            entryService.edit(entry);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "entry/edit-form";
        }

        return "redirect:/dashboard";
    }
}
