package ru.hits.loan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/integration/loan")
public class LoanIntegrationController {

    @PostMapping("/crate")
    public void create(){

    }

    @DeleteMapping
    public void delete(){

    }

    @GetMapping
    public void allLoans(){

    }

    @GetMapping("/{id}")
    public void getOne(@PathVariable UUID id){

    }

}
