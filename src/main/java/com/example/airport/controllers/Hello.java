package com.example.airport.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Hello {
    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "Привет";
    }
}

