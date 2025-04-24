package com.anote.space.company.controller;

import com.anote.space.company.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registrar")
    public String registrar(@RequestBody Map<String, String> body) {
        boolean sucesso = authService.registrar(body.get("nome"), body.get("email"), body.get("senha"));
        return sucesso ? "Usuário registrado com sucesso" : "Usuário já existe";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        boolean sucesso = authService.login(body.get("email"), body.get("senha"));
        return sucesso ? "Login bem-sucedido" : "Credenciais inválidas";
    }
}
