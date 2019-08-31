package com.moroz.books.controller;

import com.moroz.books.model.Role;
import com.moroz.books.model.User;
import com.moroz.books.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("newUser", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String saveUser(@Valid
                           @ModelAttribute(value = "newUser") User user,
                           BindingResult bindingResult,
                           Model model) {
        if(bindingResult.hasErrors()){
            return "/registration";
        }
        User existUser = userRepository.findByUsername(user.getUsername());
        if (existUser != null) {
            model.addAttribute("message", "User already exist!");
            return "/registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
