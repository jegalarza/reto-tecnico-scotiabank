package com.scotiabank.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scotiabank.model.Alumno;
import com.scotiabank.repository.AlumnoRepository;
import com.scotiabank.service.AlumnoService;
import com.scotiabank.util.Constantes;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AlumnoServiceImpl implements AlumnoService {

	@Autowired
	private AlumnoRepository alumnoRepository;

	@Override
	public Flux<Alumno> listar() {
		return alumnoRepository.findByEstadoIgnoreCase(Constantes.ACTIVO);
	}

	@Override
	public Mono<Alumno> guardar(Alumno alumno) {
		
		if (alumno.getNombre() == null || alumno.getNombre().isBlank()) {
			return Mono.error(new IllegalArgumentException("El nombre es obligatorio"));
		}
		if (alumno.getApellido() == null || alumno.getApellido().isBlank()) {
			return Mono.error(new IllegalArgumentException("El apellido es obligatorio"));
		}
		if (alumno.getEstado() == null || !(alumno.getEstado().equalsIgnoreCase("activo")
				|| alumno.getEstado().equalsIgnoreCase("inactivo"))) {
			return Mono.error(new IllegalArgumentException("El estado debe ser 'activo' o 'inactivo'"));
		}
		if (alumno.getEdad() == null || alumno.getEdad() < 0) {
			return Mono.error(new IllegalArgumentException("La edad debe ser positiva"));
		}

		if (alumno.getId() == null) {
			return alumnoRepository.save(alumno);
		}

		return alumnoRepository.existsById(alumno.getId()).flatMap(existe -> {
			if (existe) {
				return Mono.error(new IllegalStateException("No se pudo grabar dicho alumno: ID " + alumno.getId() + " repetido"));
			}
			return alumnoRepository.save(alumno);
		});
	}

}
