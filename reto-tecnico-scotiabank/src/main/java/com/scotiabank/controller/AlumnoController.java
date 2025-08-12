package com.scotiabank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scotiabank.model.Alumno;
import com.scotiabank.service.AlumnoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoService;

	@GetMapping
	public Mono<ResponseEntity<Flux<Alumno>>> listar() {
		Flux<Alumno> alumnos = alumnoService.listar();

		return alumnos.hasElements().flatMap(tieneElementos -> {
			if (tieneElementos) {
				return Mono.just(ResponseEntity.ok(alumnos));
			} else {
				return Mono.just(ResponseEntity.noContent().build());
			}
		});
	}

	@PostMapping
	public Mono<ResponseEntity<Object>> crear(@RequestBody Alumno alumno) {
	    return alumnoService.guardar(alumno)
	            .then(Mono.just(ResponseEntity.status(201).build()))
	            .onErrorResume(e -> {
	                if (e instanceof IllegalArgumentException) {
	                    return Mono.just(ResponseEntity.badRequest().body(e.getMessage()));
	                } else if (e instanceof IllegalStateException) {
	                    return Mono.just(ResponseEntity.status(409).body(e.getMessage()));
	                }
	                return Mono.just(ResponseEntity.internalServerError().body("Error interno del servidor"));
	            });
	}



}
