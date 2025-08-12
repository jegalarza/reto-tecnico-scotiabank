package com.scotiabank.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("alumnos")
public class Alumno {

	@Id
	private Long id;
	private String nombre;
	private String apellido;
	private String estado;
	private Integer edad;
	
}
