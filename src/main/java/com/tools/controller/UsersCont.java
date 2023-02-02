package com.tools.controller;

import com.tools.controller.main.Attributes;
import com.tools.model.User;
import com.tools.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersCont extends Attributes {
    @GetMapping
    public String Users(Model model) {
        addAttributesUsers(model);
        return "users";
    }

    @PostMapping("/update/{id}")
    public String UserUpdate(@PathVariable Long id, @RequestParam Role role) {
        User user = userRepo.getReferenceById(id);
        user.setRole(role);
        userRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String UserDelete(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }


}
