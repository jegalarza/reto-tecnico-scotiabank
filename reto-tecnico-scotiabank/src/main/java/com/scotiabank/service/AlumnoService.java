package com.scotiabank.service;

import com.scotiabank.model.Alumno;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoService {

	public Flux<Alumno> listar();
	
	public Mono<Alumno> guardar(Alumno alumno);
	
}
