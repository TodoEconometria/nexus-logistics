// (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
// Proyecto Nexus Logistics - Material companion del libro
// Curso IFCD0014: Spring Boot + Hibernate
//
// ESQUELETO: Implementar CRUD REST para clientes.
// Capitulo 19B: "Controladores REST Adicionales"
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

    // TODO: Implementar GET /api/clientes (listar todos)
    // Este controller usa el repositorio directamente (sin service)

    // TODO: Implementar GET /api/clientes/{id} (obtener por ID)
    // Lanzar ResourceNotFoundException si no existe

    // TODO: Implementar POST /api/clientes (crear cliente)
    // Usar @Valid @RequestBody Cliente
    // Importante: asignar id = null para evitar inyeccion de ID
    // Retornar HttpStatus.CREATED

    // TODO: Implementar PUT /api/clientes/{id} (actualizar cliente)
    // Buscar existente, actualizar campos: nombre, email, telefono, direccion

    // TODO: Implementar DELETE /api/clientes/{id} (eliminar cliente)
    // Verificar existencia antes de eliminar
    // Retornar HttpStatus 204 No Content
}
