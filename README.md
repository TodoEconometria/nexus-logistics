# Nexus Logistics

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg)](https://docs.docker.com/compose/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Sistema de gestion logistica construido con **Spring Boot 3**, **JPA/Hibernate** y **PostgreSQL**.

Proyecto companion del libro **"Arquitectura de Sistemas Enterprise: El Arquitecto en la Era de la IA"** — Certificacion IFCD0014.

---

## Rama `completo`

Esta rama contiene la **implementacion completa y funcional** del Proyecto Nexus, incluyendo toda la logica de negocio, controladores REST, seguridad JWT y scripts de analitica Python.

Si buscas la version esqueleto con TODOs para practicar, cambia a la rama `main`.

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
git clone -b completo https://github.com/TodoEconometria/nexus-logistics.git
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
│   ├── service/        # Logica de negocio (4 servicios)
│   ├── controller/     # Endpoints REST (5 controladores)
│   ├── dto/            # Objetos de transferencia
│   ├── security/       # JWT (generacion, validacion, filtro)
│   ├── config/         # SecurityConfig, OpenApiConfig
│   └── exception/      # Manejo global de errores
├── analytics/          # Scripts Python de analitica
│   ├── nexus_analytics.py          # Analisis exploratorio
│   ├── nexus_temporal.py           # Series temporales y eficiencia
│   ├── nexus_dashboard_streamlit.py # Dashboard interactivo
│   └── nexus_sqlalchemy.py         # Acceso directo a PostgreSQL
├── docker-compose.yml  # PostgreSQL + Adminer + API
├── Dockerfile          # Multi-stage build (Maven + JRE Alpine)
└── pom.xml             # Dependencias Maven
```

## Entidades del dominio

- **Envio** — Paquete con tracking, origen/destino, peso, costo, maquina de estados (REGISTRADO -> EN_ALMACEN -> EN_TRANSITO -> ENTREGADO/DEVUELTO)
- **Cliente** — Usuario con email unico, relacionado con sus envios
- **Ruta** — Ruta de distribucion con fecha programada, vehiculo asignado y lista de envios
- **Almacen** — Centro de distribucion con capacidad maxima
- **Vehiculo** — Flota con matricula, tipo (MOTO/FURGONETA/CAMION) y estado de disponibilidad

## Endpoints principales

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/envios` | Listar envios (paginado) |
| POST | `/api/envios` | Crear envio |
| PATCH | `/api/envios/{id}/estado` | Actualizar estado |
| PATCH | `/api/envios/{id}/ruta` | Asignar a ruta |
| GET | `/api/clientes` | Listar clientes |
| POST | `/api/rutas` | Crear ruta |
| PATCH | `/api/rutas/{id}/completar` | Completar ruta |
| POST | `/api/auth/login` | Obtener JWT |
| POST | `/api/auth/register` | Registrar usuario |

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
