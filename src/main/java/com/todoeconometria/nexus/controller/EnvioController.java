// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
//
// ESQUELETO: Implementar los endpoints REST para envios.
// Capitulo 19A: "Capa REST - Controladores"
package com.todoeconometria.nexus.controller;

import com.todoeconometria.nexus.model.Envio;
import com.todoeconometria.nexus.model.EstadoEnvio;
import com.todoeconometria.nexus.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/envios")
@Tag(name = "Envios", description = "Gestion de envios logisticos")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    // TODO: Implementar GET /api/envios (listar con paginacion)
    // Pista: recibir Pageable como parametro, Spring lo resuelve automaticamente
    // Anotar con @GetMapping y @Operation(summary = "...")

    // TODO: Implementar GET /api/envios/{id} (obtener por ID)

    // TODO: Implementar GET /api/envios/tracking/{trackingNumber} (buscar por tracking)

    // TODO: Implementar GET /api/envios/buscar?destino=X (buscar por destino con paginacion)

    // TODO: Implementar POST /api/envios (crear envio)
    // Parametros: origen, destino, pesoKg (BigDecimal), clienteId (Long)
    // Retornar ResponseEntity con HttpStatus.CREATED

    // TODO: Implementar PATCH /api/envios/{id}/estado (actualizar estado)
    // Parametro: estado (EstadoEnvio)
    // Nota: usar @PatchMapping, no @PutMapping (actualizacion parcial)

    // TODO: Implementar PATCH /api/envios/{id}/ruta (asignar a ruta)
    // Parametro: rutaId (Long)

    // TODO: Implementar DELETE /api/envios/{id} (eliminar envio)
    // Retornar ResponseEntity.noContent().build() (HTTP 204)
}
