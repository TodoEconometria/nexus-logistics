// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
package com.todoeconometria.nexus.controller;

import com.todoeconometria.nexus.model.Cliente;
import com.todoeconometria.nexus.exception.ResourceNotFoundException;
import com.todoeconometria.nexus.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gestion de clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente")
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        cliente.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(cliente));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente existente")
    public ResponseEntity<Cliente> actualizar(
            @PathVariable Long id, @Valid @RequestBody Cliente datosActualizados) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        cliente.setNombre(datosActualizados.getNombre());
        cliente.setEmail(datosActualizados.getEmail());
        cliente.setTelefono(datosActualizados.getTelefono());
        cliente.setDireccion(datosActualizados.getDireccion());
        return ResponseEntity.ok(clienteRepository.save(cliente));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente", "id", id);
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
