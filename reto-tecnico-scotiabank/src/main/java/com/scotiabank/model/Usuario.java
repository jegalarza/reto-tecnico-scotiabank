package com.scotiabank.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("usuarios")
public class Usuario {
	
    @Id
    private String id;
    private String username;
    private String password;
    private String rol;

}