package com.scotiabank.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.scotiabank.model.Alumno;
import com.scotiabank.repository.AlumnoRepository;
import com.scotiabank.util.Constantes;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AlumnoServiceImplTest {
	
	@Mock
	private AlumnoRepository alumnoRepository;

	@InjectMocks
	private AlumnoServiceImpl alumnoService;

	private Alumno alumnoValido;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		alumnoValido = new Alumno(1L, "Juan", "Pérez", "activo", 20);
	}

	@Test
	void listarSoloActivos() {
		when(alumnoRepository.findByEstadoIgnoreCase(Constantes.ACTIVO)).thenReturn(Flux.just(alumnoValido));

		StepVerifier.create(alumnoService.listar())
				.expectNextMatches(a -> a.getEstado().equalsIgnoreCase(Constantes.ACTIVO)).verifyComplete();

		verify(alumnoRepository).findByEstadoIgnoreCase(Constantes.ACTIVO);
	}

	@Test
	void guardarAlumnoConDatosValidosSinId() {
		Alumno nuevoAlumno = new Alumno(null, "Pedro", "López", "activo", 25);
		when(alumnoRepository.save(any(Alumno.class))).thenReturn(Mono.just(nuevoAlumno));

		StepVerifier.create(alumnoService.guardar(nuevoAlumno)).expectNextMatches(a -> a.getNombre().equals("Pedro"))
				.verifyComplete();

		verify(alumnoRepository).save(nuevoAlumno);
	}

	@Test
	void guardarAlumnoConIdDuplicado() {
		when(alumnoRepository.existsById(anyLong())).thenReturn(Mono.just(true));

		StepVerifier.create(alumnoService.guardar(alumnoValido))
				.expectErrorMatches(e -> e instanceof IllegalStateException && e.getMessage().contains("ID 1 repetido"))
				.verify();

		verify(alumnoRepository).existsById(1L);
		verify(alumnoRepository, never()).save(any());
	}

	@Test
	void guardarAlumnoConNombreVacio() {
		Alumno alumnoInvalido = new Alumno(null, "", "Pérez", "activo", 20);

		StepVerifier.create(alumnoService.guardar(alumnoInvalido))
				.expectErrorMatches(
						e -> e instanceof IllegalArgumentException && e.getMessage().equals("El nombre es obligatorio"))
				.verify();

		verifyNoInteractions(alumnoRepository);
	}

	@Test
	void guardarAlumnoConEstadoInvalido() {
		Alumno alumnoInvalido = new Alumno(null, "Pedro", "Pérez", "pendiente", 20);

		StepVerifier.create(alumnoService.guardar(alumnoInvalido))
				.expectErrorMatches(
						e -> e instanceof IllegalArgumentException && e.getMessage().contains("El estado debe ser"))
				.verify();

		verifyNoInteractions(alumnoRepository);
	}
}
