package com.example.crud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("/home")
    public String helloWorld(){
        return "Hello, This is my first API call in java";
    }
}
