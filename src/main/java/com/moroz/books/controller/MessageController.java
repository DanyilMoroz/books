package com.moroz.books.controller;

import com.moroz.books.model.Message;
import com.moroz.books.model.User;
import com.moroz.books.repository.MessageRepository;
import com.moroz.books.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/messages")
    public String messages(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "messages";
    }

    @GetMapping("/messages/add")
    public String addMessageForm(Model model){
        model.addAttribute("newMessage", new Message());
        return "addmessage";
    }

    @PostMapping("/messages/add")
    public String addMessage(@AuthenticationPrincipal User user, @ModelAttribute Message newMessage, Model model){
        newMessage.setAuthor(user);
        messageRepository.save(newMessage);
        model.addAttribute("messages", messageRepository.findAll());
        return "redirect:/messages";
    }

}
