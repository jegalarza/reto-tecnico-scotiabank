package com.scotiabank.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;

import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles("test")
public class AlumnoRepositoryTest {

	@Autowired
	private AlumnoRepository alumnoRepository;

	@Test
	void testFindAll() {
		StepVerifier.create(alumnoRepository.findAll()).expectNextCount(3)
				.verifyComplete();
	}

	@Test
	void testFindByEstadoIgnoreCase() {
		StepVerifier.create(alumnoRepository.findByEstadoIgnoreCase("activo"))
				.expectNextMatches(alumno -> alumno.getEstado().equalsIgnoreCase("activo"))
				.expectNextMatches(alumno -> alumno.getEstado().equalsIgnoreCase("activo")).verifyComplete();
	}

}
