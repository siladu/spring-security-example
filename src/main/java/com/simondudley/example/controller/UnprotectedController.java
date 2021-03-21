package com.simondudley.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UnprotectedController {

    @GetMapping({"/unprotected"})
    public String getUnprotected() {
        return "This is an unprotected endpoint, <a href='/api'>here's a link to the protected API</a>";
    }
}
