package com.example.passwordmanager.controller;

import com.example.passwordmanager.dto.EditEntryDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.security.aes.CBC;
import com.example.passwordmanager.service.IEntryService;
import com.example.passwordmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

@Controller
public class EntryController {

    @Autowired
    private IEntryService entryService;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("dashboard/addEntry")
    public String displayNewEntryForm(Model model) {

        model.addAttribute("entry", new EditEntryDTO());
        return "entry/new-entry-form";
    }

    @PostMapping("dashboard/addEntry")
    public String processNewEntryForm(@ModelAttribute("entry") EditEntryDTO entry, RedirectAttributes attributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "redirect:/dashboard/addEntry";
        } else {

//            todo przeniesc enkrypcje do serwisu
            IvParameterSpec iv = CBC.generateIV();
            byte[] salt = CBC.generateSalt();
            try {
                SecretKey key = CBC.getSecret(entry.getPassword(), salt);
                String encryptedPassword = CBC.encrypt(entry.getPassword(), key, iv);
                entry.setPassword(encryptedPassword);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException | UnsupportedEncodingException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            entryService.add(entry);
            attributes.addFlashAttribute("success", "New password was added successfully");
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/dashboard/show/{id}")
    public String showPassword(@PathVariable Long id, Model model) {
        String website = entryService.getWebsite(id);
        model.addAttribute("website", website);
        model.addAttribute("masterPassword", "");
        model.addAttribute("id", id);
        model.addAttribute("isDecrypted", false);
        return "entry/show-password-form";
    }

    @RequestMapping("/dashboard/show/{id}")
    public String showPassword(@ModelAttribute("masterPassword") String masterPassword,@ModelAttribute("website") String website, RedirectAttributes attributes, Model model, @PathVariable String id) {

//        if (!passwordEncoder.matches(passwordEncoder.encode(masterPassword), userService.getMasterPassword())) {
//            attributes.addFlashAttribute("error", "Master password not correct");
//            return "redirect:/dashboard/show/"+id;
//        }kto?

        System.out.println(website);
        model.addAttribute("masterPassword", "decrypted");
//        model.addAttribute("website", website);
        model.addAttribute("isDecrypted", true);
        return "entry/show-password-form";
    }



    @RequestMapping("/dashboard/delete/{id}")
    public String deleteEntry(@PathVariable Long id) {
        entryService.delete(id);
        return "redirect:/dashboard";
    }

    @RequestMapping("dashboard/edit/{id}")
    public String displayEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("entry", new EditEntryDTO(id));
        return "entry/edit-form";
    }

    @PostMapping("dashboard/edit/{id}")
    public String processEditEntry(@Valid @ModelAttribute("entry") EditEntryDTO entry, Model model) {
        try {
            entryService.edit(entry);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "entry/edit-form";
        }

        return "redirect:/dashboard";
    }
}
