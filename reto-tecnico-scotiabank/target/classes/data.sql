CREATE TABLE alumnos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    edad INT NOT NULL
);

CREATE TABLE usuarios (
    id VARCHAR(50) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL
);


INSERT INTO alumnos (nombre, apellido, estado, edad) VALUES
('Juan', 'Pérez', 'activo', 20),
('Ana', 'Gómez', 'inactivo', 22),
('Luis', 'Martínez', 'activo', 19);

INSERT INTO usuarios (id, username, password, rol) VALUES
('1', 'admin', '$2a$10$2WPAx1huk.jgzQM2KHwDYeqv3Bo3K06YdOqcQskyZlG8UgWhf4BvS', 'ROLE_ADMIN'),
('2', 'user', '$2a$10$kmqXaj0AzpyQyOzPwulaeeG8RLMr/eNLM3tLBDgAbykb7JaKrsBFW', 'ROLE_USER');
