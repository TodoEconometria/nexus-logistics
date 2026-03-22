// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
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

    @GetMapping
    @Operation(summary = "Listar todas las rutas")
    public ResponseEntity<List<Ruta>> listarTodas() {
        return ResponseEntity.ok(rutaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener ruta por ID")
    public ResponseEntity<Ruta> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.obtenerPorId(id));
    }

    @GetMapping("/{id}/envios")
    @Operation(summary = "Obtener ruta con todos sus envios cargados")
    public ResponseEntity<Ruta> obtenerConEnvios(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.obtenerConEnvios(id));
    }

    @GetMapping("/pendientes")
    @Operation(summary = "Listar rutas pendientes ordenadas por fecha")
    public ResponseEntity<List<Ruta>> listarPendientes() {
        return ResponseEntity.ok(rutaService.listarPendientes());
    }

    @GetMapping("/fecha/{fecha}")
    @Operation(summary = "Listar rutas programadas para una fecha especifica")
    public ResponseEntity<List<Ruta>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(rutaService.listarPorFecha(fecha));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva ruta de distribucion")
    public ResponseEntity<Ruta> crear(
            @RequestParam String nombre,
            @RequestParam String origen,
            @RequestParam String destino,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaProgramada,
            @RequestParam(required = false) Double distanciaKm,
            @RequestParam(required = false) Long vehiculoId) {
        Ruta nueva = rutaService.crear(nombre, origen, destino, fechaProgramada, distanciaKm, vehiculoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PatchMapping("/{id}/completar")
    @Operation(summary = "Marcar ruta como completada (entrega todos los envios)")
    public ResponseEntity<Ruta> completar(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.completar(id));
    }

    @PatchMapping("/{id}/vehiculo")
    @Operation(summary = "Asignar un vehiculo a una ruta")
    public ResponseEntity<Ruta> asignarVehiculo(
            @PathVariable Long id, @RequestParam Long vehiculoId) {
        return ResponseEntity.ok(rutaService.asignarVehiculoARuta(id, vehiculoId));
    }
}
