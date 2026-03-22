// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.controller;

import com.todoeconometria.nexus.exception.ResourceNotFoundException;
import com.todoeconometria.nexus.model.Almacen;
import com.todoeconometria.nexus.repository.AlmacenRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
@Tag(name = "Almacenes", description = "Gestion de almacenes de distribucion")
public class AlmacenController {

    private final AlmacenRepository almacenRepository;

    public AlmacenController(AlmacenRepository almacenRepository) {
        this.almacenRepository = almacenRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todos los almacenes")
    public ResponseEntity<List<Almacen>> listarTodos() {
        return ResponseEntity.ok(almacenRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener almacen por ID")
    public ResponseEntity<Almacen> obtenerPorId(@PathVariable Long id) {
        Almacen almacen = almacenRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Almacen", "id", id));
        return ResponseEntity.ok(almacen);
    }

    @GetMapping("/ciudad/{ciudad}")
    @Operation(summary = "Filtrar almacenes por ciudad")
    public ResponseEntity<List<Almacen>> filtrarPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(almacenRepository.findByCiudad(ciudad));
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar almacenes con espacio disponible")
    public ResponseEntity<List<Almacen>> listarDisponibles() {
        return ResponseEntity.ok(almacenRepository.findAlmacenesConEspacio());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo almacen")
    public ResponseEntity<Almacen> crear(@Valid @RequestBody Almacen almacen) {
        almacen.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(almacenRepository.save(almacen));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un almacen existente")
    public ResponseEntity<Almacen> actualizar(
            @PathVariable Long id, @Valid @RequestBody Almacen datosActualizados) {
        Almacen almacen = almacenRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Almacen", "id", id));
        almacen.setNombre(datosActualizados.getNombre());
        almacen.setCiudad(datosActualizados.getCiudad());
        almacen.setDireccion(datosActualizados.getDireccion());
        almacen.setCapacidadMaxima(datosActualizados.getCapacidadMaxima());
        return ResponseEntity.ok(almacenRepository.save(almacen));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un almacen")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!almacenRepository.existsById(id)) {
            throw new ResourceNotFoundException("Almacen", "id", id);
        }
        almacenRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
