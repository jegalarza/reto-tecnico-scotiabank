package com.scotiabank.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.scotiabank.model.Usuario;

import reactor.core.publisher.Mono;

public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, String>{

	Mono<Usuario> findByUsername(String username);
	
}
