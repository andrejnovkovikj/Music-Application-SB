package com.example.fakefy.web;

import com.example.fakefy.model.MusicUser;
import com.example.fakefy.model.UserRole;
import com.example.fakefy.service.MusicUserService;
import org.hibernate.annotations.CollectionId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@Controller
@RequestMapping()
public class UserController {
    private MusicUserService musicUserService;

    public UserController(MusicUserService musicUserService) {
        this.musicUserService = musicUserService;
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("bodyContent", "register");
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam UserRole role) {

        musicUserService.create(username, email, password, role, false);
        return "redirect:/albums";
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable Long id, Model model) {
        MusicUser user = musicUserService.findById(id);
        model.addAttribute("user", user);
        return "userDetails";
    }
}
