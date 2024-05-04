package com.example.blog.servlet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servlet")
public class ServletController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
