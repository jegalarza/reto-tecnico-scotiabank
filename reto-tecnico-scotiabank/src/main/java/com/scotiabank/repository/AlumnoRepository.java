package com.scotiabank.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.scotiabank.model.Alumno;

import reactor.core.publisher.Flux;

@Repository
public interface AlumnoRepository extends ReactiveCrudRepository<Alumno, Long> {
	
	Flux<Alumno> findByEstadoIgnoreCase(String estado);
	
}