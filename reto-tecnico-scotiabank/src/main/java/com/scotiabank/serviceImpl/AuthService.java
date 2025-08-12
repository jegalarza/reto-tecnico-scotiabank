package com.scotiabank.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scotiabank.model.Usuario;
import com.scotiabank.model.dto.request.AuthRequest;
import com.scotiabank.model.dto.response.AuthResponse;
import com.scotiabank.repository.UsuarioRepository;
import com.scotiabank.util.JwtUtil;

import reactor.core.publisher.Mono;

@Service
public class AuthService {
    
    private final UsuarioRepository repository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    public Mono<AuthResponse> login(AuthRequest request) {
        return repository.findByUsername(request.getUsername())
                .filter(usuario -> encoder.matches(request.getPassword(), usuario.getPassword()))
                .map(usuario -> new AuthResponse(
                        jwtUtil.generarToken(usuario.getUsername(), usuario.getRol())
                ));
    }

    public Mono<Usuario> registrar(Usuario usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        return repository.save(usuario);
    }
}
