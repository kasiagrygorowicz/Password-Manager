package com.example.passwordmanager.service;

import com.example.passwordmanager.entity.Entry;
import com.example.passwordmanager.entity.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService implements IValidationService{

    @Autowired
    private IUserService userService;
    @Autowired
    private IEntryService entryService;


    @Override
    public boolean resourceBelongsToUser(Long id)  {
        User u = null;
        try {
            u = userService.getCurrent().get();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Entry e = entryService.getEntry(id);
        return e.getUser().getId()==u.getId();
    }

}
