// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
//
// ESQUELETO: Implementar endpoints REST para rutas de distribucion.
// Capitulo 19B: "Controladores REST Adicionales"
package com.todoeconometria.nexus.controller;

import com.todoeconometria.nexus.model.Ruta;
import com.todoeconometria.nexus.service.RutaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rutas")
@Tag(name = "Rutas", description = "Gestion de rutas de distribucion")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    // TODO: Implementar GET /api/rutas (listar todas)

    // TODO: Implementar GET /api/rutas/{id} (obtener por ID)

    // TODO: Implementar GET /api/rutas/{id}/envios (obtener ruta con envios cargados)
    // Usa obtenerConEnvios del servicio (JOIN FETCH para evitar N+1)

    // TODO: Implementar GET /api/rutas/pendientes (listar no completadas)

    // TODO: Implementar GET /api/rutas/fecha/{fecha} (filtrar por fecha programada)
    // Pista: usar @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) en el @PathVariable

    // TODO: Implementar POST /api/rutas (crear ruta)
    // Parametros: nombre, origen, destino, fechaProgramada, distanciaKm (opcional), vehiculoId (opcional)
    // Retornar HttpStatus.CREATED

    // TODO: Implementar PATCH /api/rutas/{id}/completar (completar ruta)
    // Esta operacion entrega todos los envios y libera el vehiculo

    // TODO: Implementar PATCH /api/rutas/{id}/vehiculo (asignar vehiculo)
    // Parametro: vehiculoId
}
