package com.anote.space.company.controller;

import com.anote.space.company.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body, HttpSession session) {
        boolean sucesso = authService.login(body.get("email"), body.get("senha"));

        if (sucesso) {
            session.setAttribute("usuario", body.get("email"));
            System.out.println("Sessão criada: " + session.getId());
            return ResponseEntity.ok("Login bem-sucedido");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody Map<String, String> body) {
        String nome = body.get("nome");
        String email = body.get("email");
        String senha = body.get("senha");

        if (nome == null || email == null || senha == null || nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            return ResponseEntity.status(400).body("Todos os campos devem ser preenchidos");
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            return ResponseEntity.status(400).body("Email inválido");
        }

        boolean sucesso = authService.registrar(nome, email, senha);
        if (sucesso) {
            return ResponseEntity.ok("Usuário registrado com sucesso");
        } else {
            return ResponseEntity.status(409).body("Usuário já existe");
        }
    }

    @GetMapping("/check")
    public ResponseEntity<String> check(HttpSession session) {
        Object usuario = session.getAttribute("usuario");
        if (usuario != null) {
            return ResponseEntity.ok("Logado como " + usuario.toString());
        } else {
            return ResponseEntity.status(401).body("Não autenticado");
        }
    }
}


