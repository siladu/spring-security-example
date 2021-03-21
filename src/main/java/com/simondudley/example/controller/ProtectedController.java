package com.simondudley.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedController {

    @GetMapping
    public String getProtected() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "This is a protected endpoint; logged in as '" + authentication.getName() + "'";
    }
}
