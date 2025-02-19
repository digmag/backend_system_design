package ru.hits.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello");
    }
    @GetMapping("/api")
    public ResponseEntity<String> tst(){
        return ResponseEntity.ok("123");
    }
}
