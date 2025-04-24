package com.anote.space.company.service;

import com.anote.space.company.entity.Usuario;
import com.anote.space.company.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registrar(String nome, String email, String senha) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            return false;
        }

        String senhaCriptografada = passwordEncoder.encode(senha);

        Usuario usuario = new Usuario(nome, email, senhaCriptografada);

        usuarioRepository.save(usuario);

        return true;
    }

    public boolean login(String email, String senha) {
        System.out.println("Tentando fazer login com email: " + email + " e senha: " + senha);

        if (senha == null) {
            System.err.println("Erro: Senha não fornecida");
            return false;
        }

        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.map(u -> passwordEncoder.matches(senha, u.getSenha())).orElse(false);
    }

}

