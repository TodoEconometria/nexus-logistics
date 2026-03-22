// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
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

    @GetMapping
    @Operation(summary = "Listar todos los envios con paginacion")
    public ResponseEntity<Page<Envio>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(envioService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener envio por ID")
    public ResponseEntity<Envio> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(envioService.obtenerPorId(id));
    }

    @GetMapping("/tracking/{trackingNumber}")
    @Operation(summary = "Buscar envio por numero de tracking")
    public ResponseEntity<Envio> obtenerPorTracking(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(envioService.obtenerPorTracking(trackingNumber));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar envios por destino")
    public ResponseEntity<Page<Envio>> buscarPorDestino(
            @RequestParam String destino, Pageable pageable) {
        return ResponseEntity.ok(envioService.buscarPorDestino(destino, pageable));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo envio")
    public ResponseEntity<Envio> crear(
            @RequestParam String origen,
            @RequestParam String destino,
            @RequestParam BigDecimal pesoKg,
            @RequestParam Long clienteId) {
        Envio nuevo = envioService.crearEnvio(origen, destino, pesoKg, clienteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar el estado de un envio")
    public ResponseEntity<Envio> actualizarEstado(
            @PathVariable Long id, @RequestParam EstadoEnvio estado) {
        return ResponseEntity.ok(envioService.actualizarEstado(id, estado));
    }

    @PatchMapping("/{id}/ruta")
    @Operation(summary = "Asignar envio a una ruta")
    public ResponseEntity<Envio> asignarARuta(
            @PathVariable Long id, @RequestParam Long rutaId) {
        return ResponseEntity.ok(envioService.asignarARuta(id, rutaId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un envio (solo en estado REGISTRADO)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        envioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
