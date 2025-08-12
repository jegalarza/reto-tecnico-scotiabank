CREATE TABLE alumnos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    edad INT NOT NULL
);

INSERT INTO alumnos (nombre, apellido, estado, edad) VALUES
('Juan', 'Pérez', 'activo', 20),
('Ana', 'Gómez', 'inactivo', 22),
('Luis', 'Martínez', 'activo', 19);
