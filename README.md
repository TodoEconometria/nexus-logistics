# Nexus Logistics

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg)](https://docs.docker.com/compose/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Sistema de gestion logistica construido con **Spring Boot 3**, **JPA/Hibernate** y **PostgreSQL**.

Proyecto companion del libro **"Arquitectura de Sistemas Enterprise: El Arquitecto en la Era de la IA"** — Certificacion IFCD0014.

---

## Este repositorio es un esqueleto

Esta rama (`main`) contiene la **estructura completa** del proyecto pero con la logica de negocio pendiente de implementar. Los archivos marcados con `TODO` indican donde debes escribir tu codigo.

**Para obtener la implementacion completa**, consigue el libro:

- **Amazon** (paperback + Kindle): [enlace pendiente]
- **Gumroad** (PDF + codigo completo): [enlace pendiente]

El libro explica paso a paso cada linea de codigo, con la teoria detras de cada decision arquitectonica.

---

## Que esta incluido

| Capa | Estado | Archivos |
|------|--------|----------|
| Entidades JPA | Completo | 5 entidades + 2 enums |
| Repositorios Spring Data | Completo | 5 repositorios con JPQL |
| DTOs | Completo | 5 objetos de transferencia |
| Excepciones | Completo | 3 clases de manejo de errores |
| Seguridad JWT | Completo | Filter + Provider + Config |
| OpenAPI/Swagger | Completo | Configuracion lista |
| Docker + CI/CD | Completo | Dockerfile, Compose, GitHub Actions |
| Python Analytics | Completo | 4 scripts de analisis |
| **Servicios** | **TODO** | **4 clases por implementar** |
| **Controladores** | **TODO** | **5 clases por implementar** |
| **Tests** | **Incluidos** | **Pasan cuando implementes los servicios** |

## Que debes implementar

### Servicios (logica de negocio)
- `EnvioService.java` — Creacion de envios, maquina de estados, asignacion a rutas
- `ClienteService.java` — CRUD con validacion de email unico
- `RutaService.java` — Gestion de rutas, completar ruta, asignacion de vehiculos
- `AuthService.java` — Registro y login con JWT + BCrypt

### Controladores (endpoints REST)
- `EnvioController.java` — 8 endpoints: CRUD + PATCH estado/ruta
- `ClienteController.java` — 5 endpoints: CRUD completo
- `RutaController.java` — 8 endpoints: CRUD + completar + asignar vehiculo
- `AlmacenController.java` — 7 endpoints: CRUD + filtros
- `AuthController.java` — 2 endpoints: login + register

### Tests de validacion
Los tests en `EnvioServiceTest.java` estan completos y **deben pasar** cuando termines la implementacion. Usalos como guia.

---

## Tecnologias

- **Java 21** + **Spring Boot 3.2**
- **JPA/Hibernate** con PostgreSQL 15
- **Spring Security** + JWT (HMAC-SHA256)
- **OpenAPI/Swagger** (springdoc 2.5.0)
- **Docker Compose** para orquestacion
- **GitHub Actions** para CI/CD
- **Python** (pandas, matplotlib, Streamlit, SQLAlchemy) para analitica

## Inicio rapido

```bash
git clone https://github.com/TodoEconometria/nexus-logistics.git
cd nexus-logistics
docker-compose up -d --build

# Acceder a:
# API REST:   http://localhost:8080/api/envios
# Swagger UI: http://localhost:8080/swagger-ui.html
# Adminer:    http://localhost:8081
```

## Estructura del proyecto

```
nexus-logistics/
├── src/main/java/com/todoeconometria/nexus/
│   ├── model/          # Entidades JPA (Envio, Cliente, Ruta, Almacen, Vehiculo)
│   ├── repository/     # Repositorios Spring Data con JPQL
│   ├── service/        # TODO: Logica de negocio
│   ├── controller/     # TODO: Endpoints REST
│   ├── dto/            # Objetos de transferencia
│   ├── security/       # JWT (generacion, validacion, filtro)
│   ├── config/         # SecurityConfig, OpenApiConfig
│   └── exception/      # Manejo global de errores
├── analytics/          # Scripts Python de analitica
├── docker-compose.yml  # PostgreSQL + Adminer + API
├── Dockerfile          # Multi-stage build (Maven + JRE Alpine)
└── pom.xml             # Dependencias Maven
```

---

## El libro

Este repositorio contiene el codigo del **Capitulo 19** del libro. El libro cubre en **290,000+ palabras** todo el recorrido desde Java basico hasta despliegue en produccion con Kubernetes.

**5 partes, 34 capitulos:**
- Parte 0: Los Cimientos de Java
- Parte I: La Construccion (Maven)
- Parte II: La Estructura (Hibernate)
- Parte III: Las Vigas de Carga (Spring)
- Parte IV: La Fabrica Automatizada (DevOps)
- Parte V: La Catedral Completa (Nexus)

**Consiguelo en:**
- Amazon KDP (paperback + Kindle): [enlace pendiente]
- Gumroad (PDF + codigo): [enlace pendiente]

---

## Metodologia QUASAR

Este proyecto forma parte del ecosistema pedagogico **QUASAR** (Quality Unified Automated Student Assessment & Ranking), una metodologia de evaluacion y ranking automatizado para certificaciones profesionales de desarrollo de software.

---

## Autor

**Juan Marcelo Gutierrez Miranda** — Cientifico de Datos, Arquitecto de Software y profesor especializado en Big Data, Econometria Aplicada y Desarrollo con IA.

- GitHub: [@TodoEconometria](https://github.com/TodoEconometria)
- YouTube: [@TodoEconometria](https://youtube.com/@TodoEconometria)
- LinkedIn: [juanmarcelogutierrez](https://linkedin.com/in/juanmarcelogutierrez)
- Web: [todoeconometria.com](https://todoeconometria.com)

---

## Licencia

MIT License — ver [LICENSE](LICENSE) para mas detalles.

El codigo se proporciona como material educativo companion del libro.

(c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria). Todos los derechos reservados sobre el contenido del libro.
