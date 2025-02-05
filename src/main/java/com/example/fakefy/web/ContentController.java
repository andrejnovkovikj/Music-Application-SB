package com.example.fakefy.web;

import com.example.fakefy.model.MusicUser;
import com.example.fakefy.service.MusicUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = {"https://music-application-sb-frontend.onrender.com","http://localhost:3000"})
public class ContentController {
    private final MusicUserService musicUserService;
    public ContentController(MusicUserService musicUserService) {
        this.musicUserService = musicUserService;
    }
    @GetMapping()
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/navbar")
    public String navbar(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("username", auth.getName()); // Username of logged-in user
        }
        return "navbar";
    }

    // Add this endpoint to check if the user is authenticated
    @GetMapping("/api/authenticated")
    @ResponseBody
    public boolean checkAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser");
    }
}
