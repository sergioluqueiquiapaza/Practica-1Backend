package com.universidad.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController

public class Saludo_RestController {
    @GetMapping("/saludos/{name}")
    public String Saludar(@PathVariable String name) {
        return "Saludos " + name;
    }
}