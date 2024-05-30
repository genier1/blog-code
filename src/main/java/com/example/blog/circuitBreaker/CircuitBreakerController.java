package com.example.blog.circuitBreaker;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/circuit-breaker")
public class CircuitBreakerController {
    private final CircuitBreakerService circuitBreakerService;

    @GetMapping("/{id}")
    public String test(@PathVariable Long id) {
        return circuitBreakerService.getMessage(id);
    }
}

