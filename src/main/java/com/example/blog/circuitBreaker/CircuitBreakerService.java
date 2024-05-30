package com.example.blog.circuitBreaker;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CircuitBreakerService {

    @CircuitBreaker(name = "message-circuit-breaker", fallbackMethod = "defaultMessage")
    public String getMessage(Long id) {
        if (id < 10L) {
            return "message: " + id;
        }
        throw new RuntimeException();
    }

    public String defaultMessage(Long id, CallNotPermittedException exception) {
        return "defaultMessage";
    }
}
