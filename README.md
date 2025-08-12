# Reto Técnico Scotiabank - API de Gestión de Alumnos (WebFlux + R2DBC + H2)

Este proyecto es una API reactiva construida con **Spring Boot WebFlux** y **Spring Data R2DBC** para gestionar información de alumnos.  
Incluye persistencia en **H2 en memoria**, pruebas unitarias y configuración para desplegar con **Docker**.

---

## 📜 Características
- API 100% reactiva con **WebFlux**
- Persistencia usando **R2DBC** (H2 en memoria)
- Endpoints para CRUD de alumnos
- Búsqueda por estado con `findByEstadoIgnoreCase`
- Manejo de errores y validaciones
- Tests para repositorio, servicio y controlador
- Listo para ejecutar en **Docker**

---

## 🛠 Tecnologías
- **Java 17**
- **Spring Boot 3.5.4**
- **Spring WebFlux**
- **Spring Data R2DBC**
- **H2 Database** (modo memoria)
- **Lombok**
- **JUnit 5** + **Reactor Test**
- **Docker**

---

## 📂 Estructura del Proyecto
src/
├─ main/java/com/scotiabank
│ ├─ controller/ # Controladores REST
│ ├─ service/ # Lógica de negocio
│ ├─ repository/ # Interfaces R2DBC
│ └─ model/ # Entidades
└─ test/java/com/scotiabank
├─ repository/ # Tests de repositorio
├─ service/ # Tests de servicio
└─ controller/ # Tests de controlador


---

## ⚙ Configuración
El proyecto usa **H2 en memoria**, por lo que no requiere instalar ninguna base de datos externa.  
El archivo `application.properties` contiene la configuración básica:

```properties
spring.r2dbc.url=r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1;MODE=MYSQL
spring.r2dbc.username=sa
spring.r2dbc.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

logging.level.org.springframework.r2dbc.core=DEBUG


