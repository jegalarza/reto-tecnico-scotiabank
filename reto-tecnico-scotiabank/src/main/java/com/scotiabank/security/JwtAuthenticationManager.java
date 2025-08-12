package com.scotiabank.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.scotiabank.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationManager.class);

    private final JwtUtil jwtUtil;

    public JwtAuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = (authentication == null || authentication.getCredentials() == null)
                ? null : authentication.getCredentials().toString();

        if (authToken == null) {
            return Mono.empty();
        }

        String username;
        try {
            username = jwtUtil.obtenerUsernameDesdeToken(authToken);
        } catch (Exception e) {
            log.debug("Error obteniendo username desde token: {}", e.getMessage());
            return Mono.empty();
        }

        if (username == null || !jwtUtil.validarToken(authToken)) {
            log.debug("Token inválido o username nulo");
            return Mono.empty();
        }

        String role = jwtUtil.getRoleFromToken(authToken);
        if (role == null || role.isBlank()) {
            log.debug("Token válido pero no contiene claim 'role' -> sin authorities");
            return Mono.just(new UsernamePasswordAuthenticationToken(username, null, List.of()));
        }

        String grantedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;

        var authorities = List.of(new SimpleGrantedAuthority(grantedRole));

        log.debug("Autenticado usuario={} con authorities={}", username, authorities);

        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
        return Mono.just(auth);
    }
}