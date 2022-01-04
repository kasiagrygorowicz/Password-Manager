package com.example.passwordmanager.controller;

import com.example.passwordmanager.dto.EditEntryDTO;
import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.service.IEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class EntryController {

    @Autowired
    private IEntryService entryService;

    @GetMapping("dashboard/addEntry")
    public String displayNewEntryForm(Model model) {

        model.addAttribute("entry", new Entry());
        return "entry/new-entry-form";
    }

    @PostMapping("dashboard/addEntry")
    public String processNewEntryForm(@Valid @ModelAttribute("entry") Entry entry, RedirectAttributes attributes,BindingResult bindingResult ) {
        if(bindingResult.hasErrors()){
            System.out.println("elo");
            attributes.addFlashAttribute("error",bindingResult.getFieldError().getDefaultMessage());
            return "redirect:/dashboard/addEntry";
        }else {

            entryService.add(entry);
            attributes.addFlashAttribute("success","New password was added successfully");
            return "redirect:/dashboard";
        }
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
