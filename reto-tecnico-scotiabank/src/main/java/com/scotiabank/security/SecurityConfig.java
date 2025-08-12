package com.scotiabank.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	private final JwtAuthenticationManager jwtAuthenticationManager;
    private final JwtSecurityContextRepository jwtSecurityContextRepository;

    public SecurityConfig(JwtAuthenticationManager jwtAuthenticationManager,
                          JwtSecurityContextRepository jwtSecurityContextRepository) {
        this.jwtAuthenticationManager = jwtAuthenticationManager;
        this.jwtSecurityContextRepository = jwtSecurityContextRepository;
    }
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(csrf -> csrf.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .authenticationManager(jwtAuthenticationManager)
            .securityContextRepository(jwtSecurityContextRepository)
            .authorizeExchange(exchange -> exchange
                .pathMatchers(
                    "/api/v1/auth/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/webjars/**"
                ).permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/alumnos").hasAnyRole("USER", "ADMIN")
                .pathMatchers("/api/v1/**").hasRole("ADMIN")
                .anyExchange().authenticated()
            )
            .build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /*
    public static void main(String[] args) {
    	System.out.println("Contra encriptada: " + new BCryptPasswordEncoder().encode("user"));
    }
    */

}


