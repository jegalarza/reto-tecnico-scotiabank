package com.scotiabank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.scotiabank.model.Alumno;
import com.scotiabank.service.AlumnoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(controllers = AlumnoController.class)
public class AlumnoControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private AlumnoService alumnoService;

	private Alumno alumno1;
	private Alumno alumno2;

	@BeforeEach
	void setUp() {
		alumno1 = new Alumno(1L, "Juan", "Pérez", "activo", 20);
		alumno2 = new Alumno(2L, "Ana", "Gómez", "activo", 22);
	}

	@Test
	void listar_conResultados_retornaOk() {
		Mockito.when(alumnoService.listar()).thenReturn(Flux.just(alumno1, alumno2));

		webTestClient.get().uri("/api/v1/alumnos").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
				.expectBodyList(Alumno.class).hasSize(2).contains(alumno1, alumno2);
	}

	@Test
	void listar_sinResultados_retornaNoContent() {
		Mockito.when(alumnoService.listar()).thenReturn(Flux.empty());

		webTestClient.get().uri("/api/v1/alumnos").accept(MediaType.APPLICATION_JSON).exchange().expectStatus()
				.isNoContent();
	}

	@Test
	void crear_exitoso_retorna201() {
		Mockito.when(alumnoService.guardar(any(Alumno.class))).thenReturn(Mono.just(alumno1));

		webTestClient.post().uri("/api/v1/alumnos").contentType(MediaType.APPLICATION_JSON).bodyValue(alumno1)
				.exchange().expectStatus().isCreated();
	}

	@Test
	void crear_conDatosInvalidos_retorna400() {
		Mockito.when(alumnoService.guardar(any(Alumno.class)))
				.thenReturn(Mono.error(new IllegalArgumentException("El nombre es obligatorio")));

		webTestClient.post().uri("/api/v1/alumnos").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new Alumno(null, "", "Pérez", "activo", 20)).exchange().expectStatus().isBadRequest()
				.expectBody(String.class).isEqualTo("El nombre es obligatorio");
	}

	@Test
	void crear_idRepetido_retorna409() {
		Mockito.when(alumnoService.guardar(any(Alumno.class)))
				.thenReturn(Mono.error(new IllegalStateException("No se pudo grabar dicho alumno: ID 1 repetido")));

		webTestClient.post().uri("/api/v1/alumnos").contentType(MediaType.APPLICATION_JSON).bodyValue(alumno1)
				.exchange().expectStatus().isEqualTo(409).expectBody(String.class)
				.isEqualTo("No se pudo grabar dicho alumno: ID 1 repetido");
	}

}
