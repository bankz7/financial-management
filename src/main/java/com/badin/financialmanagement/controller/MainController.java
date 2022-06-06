package com.badin.financialmanagement.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Login form with error
    @GetMapping("/login-error")
    public String loginError(Model model) {
        System.out.println("wrong");
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping(path = {"/index","/"})
    public String getMainPage() {
        System.out.println("Enter here");
        return "redirect:/accounts";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication){
        authentication.setAuthenticated(false);
        return "redirect:/accounts";
    }
}
